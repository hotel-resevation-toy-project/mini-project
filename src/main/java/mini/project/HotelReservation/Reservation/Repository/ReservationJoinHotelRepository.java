package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;

public interface ReservationJoinHotelRepository {

    Hotel findByHotelName(String hotelName);
}
