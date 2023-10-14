package mini.project.HotelReservation.Reservation.Service;


import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.CustomAnnotation.MainDiscountPolicy;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy.DaysDiscountPolicy;
import mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy.PeakDiscountPolicy;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Reservation.Data.Dto.*;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;
    private final TokenDecoder td;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  HotelRepository hotelRepository,
                                  RoomRepository roomRepository,
                                  @MainDiscountPolicy PeakDiscountPolicy peakDiscountPolicy,
                                  @MainDiscountPolicy DaysDiscountPolicy daysDiscountPolicy,
                                  TokenDecoder td) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.peakDiscountPolicy = peakDiscountPolicy;
        this.daysDiscountPolicy = daysDiscountPolicy;
        this.td = td;
    }

    @Override
    public List<HotelDto> findByHotelList() {
        return hotelRepository.findDtos().stream().map(HotelDto::new).collect(Collectors.toList());
        // 리스트<호텔> -> 스트림<호텔> -> 맵(호텔 -> 호텔 디티오)-> 스트림<호텔 디티오> -> 리스트<호텔 디티오>
    }

    @Override
    public List<RoomDto> findByRoomList(String hotelName) {
//        List<Room> rooms = ;
//        List<RoomDto> roomDtos = new ArrayList<>();
//        for (Room room : rooms) {
//            roomDtos.add(new RoomDto((room.getRoomType(),room.getRoomPrice(),room.getRoomStock()));
//        }
        return roomRepository.findAllByHotelName(hotelName);
    }

    @Override
    public DiscountPriceDto discountPrice(ReservationRequestDto requestDto) {

        //호텔 객체 생성
        Hotel hotel = hotelRepository.findByHotelName(requestDto.getHotelName());
        //숙박일
        int days = (int)ChronoUnit.DAYS.between(requestDto.getCheckInDate(), requestDto.getCheckOutDate());

        Room reservedRoom = roomRepository.findByHotelNameAndRoomType(requestDto.getHotelName(), requestDto.getRoomType());

        //원가
        int oneDayPrice = reservedRoom.getRoomPrice();
        int reservePrice = oneDayPrice*days;

        //할인될 값
        int discountPrice;

        int noPeakDays = CheckPeakDays((int) ChronoUnit.DAYS.between(hotel.getStartPeakDate(),hotel.getEndPeakDate()),
                hotel.getStartPeakDate(),
                requestDto.getCheckInDate(),
                requestDto.getCheckOutDate());

        switch (hotel.getDiscountPolicy()) {
            case POLICY_PEAK -> {
                // 성수기 할인이 적용 되야 할 일 수
                discountPrice =  peakDiscountPolicy.discount(oneDayPrice,noPeakDays);
                return new DiscountPriceDto(reservePrice, // 예약 금액
                                            discountPrice, // 할인 금액
                                            reservePrice - discountPrice, // 예약 금액 - 할인 금액 = 지불 금액
                                            hotel.getDiscountPolicy().toString()); // 적용된 할인 타입

            }
            case POLICY_DAYS -> {
                discountPrice = daysDiscountPolicy.discount(oneDayPrice, days);
                return new DiscountPriceDto(reservePrice,
                                            discountPrice,
                                        reservePrice - discountPrice,
                                            hotel.getDiscountPolicy().toString());
            }
            default -> {
                discountPrice = Math.max(peakDiscountPolicy.discount(oneDayPrice,noPeakDays),
                        daysDiscountPolicy.discount(oneDayPrice, days));
                return new DiscountPriceDto(reservePrice,
                                            discountPrice,
                                        reservePrice - discountPrice,
                                            "성수기, 연박 두 할인 중 더 큰 할인이 적용 되었습니다.");
            }
        }
    }

    //성수기를 판별하여 성수기에 해당하는 숙박일과 해당하지 않는 숙박일을 리턴해주는 메서드
    public int CheckPeakDays(int peakDays,LocalDate startPeakdate, LocalDate checkInDate, LocalDate checkOutDate){
        // 이 번 년도 성수기 시작일
        LocalDate hotelStartPeakDate = LocalDate.of(checkInDate.getYear(),startPeakdate.getMonth(),startPeakdate.getDayOfMonth());

        // 이 번 년도 성수기 시작일 + 총 성수기 일 수 = 성수기가 끝나는 날짜
        LocalDate hotelEndPeakDate = hotelStartPeakDate.plusDays(peakDays);

        // 성수기 할인을 적용 해야하는 일 수
        int discountEndDays;
        int discountStartDays;

        // outPeak -> front, behind

        if(hotelEndPeakDate.isAfter(checkInDate.minusDays(1))) {
            discountEndDays = Math.max((int) ChronoUnit.DAYS.between(hotelEndPeakDate, checkOutDate)-1,0);
        } else {
            return (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        if (hotelStartPeakDate.isBefore(checkOutDate.plusDays(1))){
            discountStartDays = Math.max((int) ChronoUnit.DAYS.between(checkInDate, hotelStartPeakDate),0);
        } else {
            return (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }

        return discountEndDays + discountStartDays;
    }

    @Override
    @Transactional
    public ReservationResponseDto reserve(ReservationRequestDto reservationReqDto, DiscountPriceDto discountPriceDto) {
        User user = userRepository.findByTokenId(td.currentUserId());

        Hotel hotel = hotelRepository.findByHotelName(reservationReqDto.getHotelName());

        Reservation reservation = Reservation.createReserve(user, hotel,
                                    reservationReqDto,
                                    discountPriceDto.getPay(),/* 결제 금액 */
                                    createReserveNumber(hotel, reservationReqDto)/* 예약 번호 */);
        Reservation save = reservationRepository.save(reservation);

        return new ReservationResponseDto(
                save.getUserName(),
                save.getPhoneNumber(),
                save.getHotelName(),
                save.getRoomType(),
                save.getCheckInDate(),
                save.getCheckOutDate(),
                save.getReserveNumber(),
                save.getReservePrice());
    }

    @Override
    public String createReserveNumber(Hotel hotel, ReservationRequestDto reservationReqDto){
        int reservationCount = reservationRepository.findCountByHotelNameAndRoom(hotel.getHotelName(),
                reservationReqDto.getRoomType()).intValue();

        String hotelName = hotel.getHotelName().split("_")[1];
        String roomType = reservationReqDto.getRoomType().toString().split("_")[2];
        String reserveSequence = String.valueOf(reservationCount + 1);
        String format = reservationReqDto.getCheckInDate().format(DateTimeFormatter.ofPattern("yyMMdd"));

        return hotelName + roomType + reserveSequence +"-"+ format;
    }

    //예약 상세 정보
    @Override
    public ReservationResponseDto reserveInfo(String reserveNumber) {

        Reservation reservation = reservationRepository.findByReserveNumber(reserveNumber);

        return new ReservationResponseDto(reservation.getUserName(),
                reservation.getPhoneNumber(),
                reservation.getHotelName(),
                reservation.getRoomType(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getReserveNumber(),
                reservation.getReservePrice());
    }

    //예약 취소
    @Override
    @Transactional
    public void reserveDelete(String reserveNumber) {
        reservationRepository.findByReserveNumber(reserveNumber).deleteReservation();
    }
}
