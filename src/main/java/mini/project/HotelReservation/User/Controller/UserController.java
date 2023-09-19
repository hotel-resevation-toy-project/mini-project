package mini.project.HotelReservation.User.Controller;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.User.Service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping(value = "/login")
//    public String joinForm(@AuthenticationPrincipal SecurityUser securityUser){
//        if(securityUser != null && securityUser.getRoleTypes().contains(RoleType.ROLE_VIEW)) {
//            return "redirect:/v";
//        }
//        return "login/join";
//    }
}

