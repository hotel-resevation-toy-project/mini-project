package mini.project.HotelReservation.User.Controller;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final ReservationRepository reservationRepository;


    // 로그인 화면(시작)
    @GetMapping("/in")
    public String getLogIn(Model model){
        model.addAttribute("userSignInDto",new UserSignInDto());
        return "user/login";
    }

    //로그인
    @PostMapping("/in")
    public String postLogIn(HttpSession session, @Valid @ModelAttribute("userSignInDto") UserSignInDto userSignInDto, BindingResult result,Model model){
        session.setAttribute("error", "");
        if(result.hasErrors()) {
            model.addAttribute("error","아이디와 비밀번호를 제대로 입력해주세요.");
            return "redirect:/user/in";
        }
        userService.logIn(userSignInDto);


        return "reservation/main";
    }

    //회원가입 화면
    @GetMapping("/new")
    public String  getJoin(Model model){
        model.addAttribute("userSignUpDto",new UserSignUpDto());
        return "user/join";
    }

    //회원가입
    @PostMapping("/new")
    public String postJoin(@Valid @ModelAttribute("userSignUpDto") UserSignUpDto userSignUpDto,
                           BindingResult result,Model model,HttpSession session){
        session.setAttribute("error","");
        if(result.hasErrors()) {
            session.setAttribute("error","다시 입력해주세요.");
            return "redirect:/user/new";
        }
        userService.join(userSignUpDto);
        return "redirect:/user/login";
    }

    // 로그아웃
    @GetMapping(value = "/out")
    public String getLogOut(){
        return "redirect:/logout";
    }

    // 유저측 예약리스트 조회
    @GetMapping(value = "/reservations")
    public String getUserReservationList(Model model){
        model.addAttribute("userReservationDtoList", userService.reservationList());
        return "user/userReservationList";
    }

    @GetMapping(value = "/{reserveNumber}")
    public String getUserReservation(@PathVariable("reserveNumber") String reserveNumber, Model model) {
        model.addAttribute("reservationDto",
                reservationRepository.findByReserveNumber(reserveNumber));
        return "user/reservationInfo";
    }

    @GetMapping()
    public String getUserInfo(Model model){
        model.addAttribute("userInfoDto", userService.getUserInfo());
        return "user/userInfo";
    }

    @PostMapping()
    public String putUserInfo(HttpSession session, Model model,/* @RequestParam("userInfoDto") UserInfoDto user*/
                              @Valid @ModelAttribute("userInfoDto") UserInfoDto userInfoDto, BindingResult result){

        session.setAttribute("error", "");

        if(result.hasErrors()) {
            model.addAttribute("Error","변경할 값을 다시 입력해주세요.");
            System.out.println("%%%%%%%%%%%%%%%%");
            System.out.println("업데이트 오류");
            return "redirect:/user";
        }
        userService.updateInfo(userInfoDto);
//        model.addAttribute("userInfoDto",userInfoDto);
        return "redirect:/user";
    }


    @GetMapping("/withdraw")
    public String withDraw(){
        return "/user/withdraw";
    }

    @PostMapping("/quit")
    public String quit(@RequestParam("password") String password){
        userService.deactive(password);
        return "redirect:/logout";
    }

    @ExceptionHandler({NoSuchElementException.class, DuplicateRequestException.class})
    public String handler(Exception e, HttpServletRequest req) {
        if(e.getClass().equals(NoSuchElementException.class)) {
            req.getSession().setAttribute("error", e.getMessage());
            return "redirect:/user/in";
        }
        if(e.getClass().equals(DuplicateRequestException.class)) {
            req.getSession().setAttribute("error", e.getMessage());
            return "redirect:/user/new";
        }
        return null;
    }
}
