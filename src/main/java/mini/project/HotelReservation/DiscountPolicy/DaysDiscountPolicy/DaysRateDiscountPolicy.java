package mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class DaysRateDiscountPolicy implements DaysDiscountPolicy{

    double discountRate = 0.95;
    @Override
    public int discount(int noDiscountPrice, int days) {
        double discountPrice = noDiscountPrice;

        for (int i = 0; i < days / 3; i++) {
            discountPrice = discountPrice * discountRate;
        }
        return (int) discountPrice;
    }
}
