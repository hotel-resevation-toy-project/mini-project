package mini.project.HotelReservation.Reservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "RESERVATIONS")
public class Reservation extends AuditTime {
    @Id @GeneratedValue
    private Long reserveId;

    @Column(nullable = false)
    private String reserveNumber;

    @Column(nullable = false)
    private Integer reservePrice;

    @Column(nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Reservation(String reserveNumber, Integer reservePrice, RoomType roomType, String hotelName, String phoneNumber, String userName, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.reserveNumber = reserveNumber;
        this.reservePrice = reservePrice;
        this.roomType = roomType;
        this.hotelName = hotelName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //비즈니스 로직
    public static Reservation createReserve(User user, Hotel hotel, ReservationRequestDto reservationRequestDto, Integer reservePrice, String reserveNumber) {

        Reservation reservation = new Reservation(reserveNumber,
                reservePrice,
                reservationRequestDto.getRoomType(),
                hotel.getHotelName(),
                user.getPhoneNumber(),
                user.getName(),
                LocalDateTime.of(reservationRequestDto.getCheckInDate(),hotel.getCheckInTime()),
                LocalDateTime.of(reservationRequestDto.getCheckOutDate(),hotel.getCheckOutTime())
                );

        reservation.foreignHotel(hotel);
        reservation.foreignUser(user);
        return reservation;
    }

    // 연관관계 메서드
    public void foreignUser(User foreignUser){
        this.user = foreignUser;
        user.getReservations().add(this);
    }
    public void foreignHotel(Hotel foreignHotel){
        this.hotel = foreignHotel;
        hotel.getReservations().add(this);
    }
    // 예약 삭제시 연관관계 제거 메서드
    public void deleteReservation() {
        this.getHotel().getReservations().remove(this);
        this.getUser().getReservations().remove(this);
    }
}
