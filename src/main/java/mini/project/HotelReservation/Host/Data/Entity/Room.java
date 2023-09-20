package mini.project.HotelReservation.Host.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "ROOMS")
public class Room extends AuditTime {

    @Id @GeneratedValue
    private Long roomId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @NotNull
    private Integer roomPrice;

    @NotNull
    private Integer roomStock;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Room(RoomType roomType, Integer roomPrice, Integer roomStock) {
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomStock = roomStock;
    }

    //연관 관계 메서드
    public void foreignHotel(Hotel foreignHotel) {
        hotel = foreignHotel;
        hotel.getRooms().add(this);
    }
    //비즈니스 로직
    public void modifyPrice(Integer newPrice){
        roomPrice = newPrice;
    }

    public void modifyStock(Integer newStock){
        roomStock = newStock;
    }
}
