package mini.project.HotelReservation.User.Controller;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.rmi.server.UID;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    //회원가입 화면
    @GetMapping(value = "/new")
    public ModelAndView getJoin(Model model,@RequestParam("dto") UserSignUpDto dto){
        ModelAndView modelAndJoinView = new ModelAndView("user/join");
        model.addAttribute(dto);

        return modelAndJoinView;
    }

    //회원가입
    @PostMapping(value = "/new")
    public ModelAndView postJoin(@RequestParam("dto") UserSignUpDto dto, RedirectAttributes redirectAttributes){
        ModelAndView modelAndLoginView = new ModelAndView("user/login");

        try{
            userServiceImpl.join(dto);
            return modelAndLoginView;
        }catch (DuplicateRequestException e){
            redirectAttributes.addFlashAttribute("error", "이미 가입한 회원입니다.");
            return new ModelAndView("redirect:/user/join");
        }
    }

    //로그인 화면(시작)
    @GetMapping(value = "/in")
    public ModelAndView getLogIn(UserSignInDto dto, Model model){
        ModelAndView modelAndLoginView = new ModelAndView("user/login");
        model.addAttribute(dto);
        return modelAndLoginView;
    }

    //로그인
    @PostMapping(value = "/in")
    public ModelAndView postLogIn(UserSignInDto sid, RedirectAttributes redirectAttributes){
        ModelAndView modelAndMainView = new ModelAndView("reservation/main");

        try{
            userServiceImpl.logIn(sid);
            return modelAndMainView;
        } catch (NoSuchElementException e){
            redirectAttributes.addFlashAttribute("error","로그인에 실패하였습니다.");
            return new ModelAndView("redirect:/user/login.html");
        }
    }

    @GetMapping(value = "/out")
    public String getLogOut(){
        return "/logout";
    }

    @GetMapping(value = "/reservations")
    public ModelAndView getUserReservationList(Model model){
        ModelAndView modelAndView = new ModelAndView("user/userReservationList");
        List<UserReservationDto> dtoList = userServiceImpl.reservationList();
        model.addAttribute("dtoList",dtoList);

        return modelAndView;
    }

    //todo:{rN}? & html에 값 잘 들어가는지?
    @GetMapping(value = "/reservation/{reserveNumber}")
    public ModelAndView getUserReservation(Model model, @RequestParam("reservationResponseDto") ReservationResponseDto reservationResponseDto) {

        model.addAttribute("reservationResponseDto", reservationResponseDto);
        return new ModelAndView("user/reservationInfo");
    }

    @GetMapping
    public ModelAndView getUserInfo(Model model, @RequestParam("user") UserInfoDto user){
        model.addAttribute("user", user);
        return new ModelAndView("user/userInfo");
    }

    @PutMapping
    public ModelAndView putUserInfo(@RequestParam("user") UserInfoDto user){
        userServiceImpl.updateInfo(user);
        return new ModelAndView("redirect:/user/userInfo");
    }

    @PatchMapping
    public ModelAndView quit(Model model, RedirectAttributes redirectAttributes){
        try {
            userServiceImpl.deactive((String) model.getAttribute("password"));
            return new ModelAndView("user/login");
        }catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", "비밀번호를 다시 입력해주세요");
            return new ModelAndView("redirect:/user/login");
        }
    }
}

