package mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PeakRateDiscountPolicy implements PeakDiscountPolicy{
    double discountRate = 0.7;
    @Override
    public int discount(int noDiscountPrice, int days) {
        return (int)(noDiscountPrice * discountRate);
    }
}
