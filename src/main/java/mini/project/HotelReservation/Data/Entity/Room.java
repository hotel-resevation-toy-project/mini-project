package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.Data.Enum.RoomType;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

    @Id @GeneratedValue
    private Long roomId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Integer roomPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Integer roomStock;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @Mapping(mappedBy = "room")
    List<Reservation> reservations;

    public Room(RoomType roomType, Integer roomPrice, Integer roomStock, Hotel hotel) {
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomStock = roomStock;
        this.hotel = hotel;
    }

    //비즈니스 로직
    public void foreignHotel(Hotel foreignHotel) {
        hotel = foreignHotel;
    }
    public void reserveSequence(){

    }
    public void modifyPrice(RoomType roomType, Integer price){

    }
    public void plusStock(){

    }

    public void minusStock(){

    }
    public void cancel(){

    }

}
