package mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DaysFixDiscountPolicy implements DaysDiscountPolicy{
    int discountPrice = 3000;
    @Override
    public int discount(int noDiscountPrice, int days) {
        return noDiscountPrice - (days / 3) * discountPrice;
    }
}
