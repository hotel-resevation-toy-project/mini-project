package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.Data.AuditTime;
import mini.project.HotelReservation.Data.Enum.RoomType;
import org.springframework.web.bind.annotation.Mapping;

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

    @OneToMany(mappedBy = "room")
    List<Reservation> reservations;

    // 연관관계 매핑은 생성자에서 빼야함 진석 -> 다은
    // 생성자에 Hotel을 빼고 아래의 foreignHotel이용해야함
    public Room(RoomType roomType, Integer roomPrice, Integer roomStock) {
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomStock = roomStock;
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
