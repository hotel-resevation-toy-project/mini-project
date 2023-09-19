package mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy;

import org.springframework.stereotype.Component;

@Component
public class DaysRateDiscountPolicy implements DaysDiscountPolicy{

    double discountRate = 0.95;
    @Override
    public int discount(int noDiscountPrice, int days) {
        double discountPrice = noDiscountPrice * (1 - Math.pow(discountRate, (days / 3)));
        return noDiscountPrice - (int)discountPrice;
        // 리턴 = 공제 금액
    }
}
