package mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class DaysRateDiscountPolicy implements DaysDiscountPolicy{

    double discountRate = 0.95;
    @Override
    public int discount(int noDiscountPrice, int days) {
        int price = noDiscountPrice * days;
        double discountPrice = price * (1 - Math.pow(discountRate, (days / 3)));
        return (int)discountPrice;
        // 리턴 = 공제 금액
    }
}
