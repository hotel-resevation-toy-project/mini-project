package mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy;

import mini.project.HotelReservation.Configure.CustomAnnotation.MainDiscountPolicy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class PeakRateDiscountPolicy implements PeakDiscountPolicy{
    double discountRate = 0.3;

    @Override
    public int discount(int noDiscountPrice, int days) {
        return (int)((noDiscountPrice * days) * discountRate);
        // 리턴 = 공제 금액 = 원금의 30%
    }
}
