package mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class PeakFixDiscountPolicy implements PeakDiscountPolicy{
    int discountPrice = 100000;
    int minPrice = 300000;

    @Override
    public int discount(int noDiscountPrice, int days) {
        return noDiscountPrice * days > minPrice ? discountPrice : 0;
        // 원금의 30만이 넘으면 리턴 10만, 아니면 리턴 0
    }
}
