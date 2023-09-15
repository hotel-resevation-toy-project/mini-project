package mini.project.HotelReservation.userTestController;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class UserTest {

    private final UserServiceImpl userServiceImpl;

}
