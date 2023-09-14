package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Data.Enum.RoomType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    @Id @GeneratedValue
    private Long reserveId;

    @NotNull
    private String reserveNumber;

    @NotNull
    private String reservePrice;

    @NotNull
    private RoomType roomType;

    @NotNull
    private String hotelName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String userName;

    @NotNull
    private LocalDateTime checkInDate;

    @NotNull
    private LocalDateTime checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    public Reservation(String reserveNumber, String reservePrice, RoomType roomType, String hotelName, String phoneNumber, String userName, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
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
    Reservation createReserve(User user, Room room, ReserveDto reserveDto) {

        return null;
    }

    // 연관관계 메서드
    void foreignUser(User foreignUser){
        user = foreignUser;
    }
    void foreignRoom(Room foreignRoom){
        room = foreignRoom;
    }
    

}
