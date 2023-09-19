package mini.project.HotelReservation.Reservation.Service;


import lombok.RequiredArgsConstructor;
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
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;

    @Override
    public List<HotelDto> findByHotelList() {
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDto> hotelDtos = new ArrayList<>();
        for (Hotel hotel : hotels) {
            hotelDtos.add(new HotelDto(hotel.getHotelName(),
                    roomRepository.findAllByHotelName(hotel.getHotelName()).stream().map(Room::getRoomType).toList(),
                    hotel.getCheckInTime(),
                    hotel.getCheckOutTime()));
        }
        return hotelDtos;
    }

    @Override
    public List<RoomDto> findByRoomList(String hotelName) {
        List<Room> rooms = roomRepository.findAllByHotelName(hotelName);
        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room room : rooms) {
            roomDtos.add(new RoomDto(room.getRoomType(),room.getRoomPrice(),room.getRoomStock()));
        }
        return roomDtos;
    }

    @Override
    public DiscountPriceDto priceCalculator(ReservationRequestDto requestDto) {

        //호텔 객체 생성
        Hotel hotel = hotelRepository.findByHotelName(requestDto.getHotelName());
        //숙박일
        int days = requestDto.getCheckOutDate().compareTo(requestDto.getCheckInDate());
        //원가
        int reservePrice = requestDto.getOneDayPrice()*days;
        //할인될 값
        int totalDiscount = discountPrice(hotel.getDiscountPolicy(),requestDto.getOneDayPrice(),days);
        //결제 값
        int pay = reservePrice - totalDiscount;

        return new DiscountPriceDto(totalDiscount,totalDiscount,pay,hotel.getDiscountPolicy().toString());
    }
    @Override
    public ReservationResponseDto reserve(ReservationRequestDto reservationReqDto, DiscountPriceDto discountPriceDto) {

    }

    @Override
    public Integer discountPrice(DiscountPolicy discountPolicy,
                                 Integer reservePrice,
                                 Integer days){

        return switch (discountPolicy.toString()) {
            case "POLICY_PEAK" -> peakDiscountPolicy.discount(reservePrice, days);
            case "POLICY_DAYS" -> daysDiscountPolicy.discount(reservePrice, days);
            default ->
                    Math.max(peakDiscountPolicy.discount(reservePrice, days), daysDiscountPolicy.discount(reservePrice, days));
        };
    }
    //예약 상세 정보
    @Override
    public ReservationResponseDto reserveInfo(String reserveNumber) {
        return null;
    }

    //예약 취소
    @Override
    public void reserveDelete(String reserveNumber) {
        reservationRepository.deleteByReserveNumber(reserveNumber);
    }
}
