package mini.project.HotelReservation.Repository;

import mini.project.HotelReservation.Data.Entity.Hotel;

public interface ReservationJoinHotelRepository {

    Hotel findByHotelName(String hotelName);
}
