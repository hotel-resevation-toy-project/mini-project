package mini.project.HotelReservation.User.Service;

import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired UserServiceImpl userService;
    @Autowired UserRepository userRepository;

    @Test
    void 회원_가입() {
        //given
        User user1 = new User("Serah","sexy123@play.data", "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER);
        //when, then
        Assertions.assertThat(user1.getName().equals("Serah"));
        Assertions.assertThat(user1.getEmail().equals("sexy123@play.data"));
    }
    @Test
    void 중복_회원_가입() {
        //given
        User user = new User("Serah","sexy123@play.data", "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER);
        //when, then

        IllegalStateException e = assertThrows(IllegalStateException.class, ()->userService.join(new UserSignUpDto("Serah","sexy123@play.data", "123", "123-4567-9101", UserRole.ROLE_USER)));
        Assertions.assertThat(e.getMessage().equals("이미 가입한 사용자입니다."));
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