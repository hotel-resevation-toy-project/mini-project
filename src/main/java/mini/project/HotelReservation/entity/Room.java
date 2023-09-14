package mini.project.HotelReservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Room {

    @Id @GeneratedValue
    private Long roomId;

    @NotNull
    private RoomType roomType;

    @NotNull
    private Integer roomPrice;

    @NotNull
    private Integer roomStock;

 /*   private LocalDateTime createAt;
    private LocalDateTime updateAt;*/

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Room(RoomType roomType, Integer roomPrice, Integer roomStock, Hotel hotel) {
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomStock = roomStock;
        this.hotel = hotel;
    }

    //비즈니스 로직
    private void foreignHotel(Hotel hotel);

    {
        hotel = foreignHotel;
    }

    private void reserveSequence();
    private void modifyPrice(RoomType roomType, Integer price);
    private void plusStock();
    private void minusStock();
    private void cancel();

}
