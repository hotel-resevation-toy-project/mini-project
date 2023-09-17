package mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy;

import org.springframework.stereotype.Component;

@Component
public class PeakFixDiscountPolicy implements PeakDiscountPolicy{
    int discountPrice = 100000;
    int minPrice = 300000;

    @Override
    public int discount(int noDiscountPrice, int days) {
        return noDiscountPrice > minPrice ? noDiscountPrice - discountPrice : noDiscountPrice;
    }
}
