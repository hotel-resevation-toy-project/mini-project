package mini.project.HotelReservation.User.Controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.User.Data.Dto.UserDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    /**
     * 회원가입 API
     */
    @PostMapping("/new")
    public void join(){

    }
    /**
     *  회원 정보 조회 API
     */
    @GetMapping("/new")
    public UserSignUpDto getMyInfo(){

        return null;
    }
    /**
     * 로그인 API
     * 서비스에서 컨트롤러로 토큰을 넘긴다.
     * 컨트롤러에서 토큰을 세션에 얹는다.
     * 해당 페이지 return
     */
    @PostMapping
    public UserSignUpDto SignIn(HttpServletRequest request){
        request.getSession();
        return null;
    }
    /**
     * 로그아웃 API
     */
    @GetMapping
    public void SignOut(){

    }
    /**
     * 회원탈퇴 API
     */
    @PatchMapping
    public void deactive(){

    }
    /**
     * 회원 정보 업데이트 API
     */
    public UserDto updateUserInfo(){
        return null;
    }
}

