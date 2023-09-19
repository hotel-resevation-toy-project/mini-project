package mini.project.HotelReservation.User.Service;

import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Test
    @DisplayName("회원가입_테스트")
    void join() {
        //given
        User user1 = new User("Serah","sexy123@play.data", "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER);
        //when, then
        Assertions.assertThat(user1.getName().equals("Serah"));
        Assertions.assertThat(user1.getEmail().equals("sexy123@play.data"));
    }

    @Test
    @DisplayName("회원 상태 확인 테스트")
    void checkStatus() {
    }

    @Test
    void logIn() {
    }

    @Test
    void updateInfo() {

    }

    @Test
    void deactive() {

    }

    @Test
    void reservationList() {
    }

    @Test
    void loadUserByUserId() {
    }
}