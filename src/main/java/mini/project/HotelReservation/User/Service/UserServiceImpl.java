package mini.project.HotelReservation.User.Service;


import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Dto.*;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenDecoder td;

    //회원가입
    @Override
    @Transactional
    public void join(UserSignUpDto sud) {
        Optional<User> optionalUser = userRepository.findByEmail(sud.getEmail());
        //처음 가입하는 경우
        if(optionalUser.isEmpty()) {
            User newUser = new User(sud.getName(), sud.getEmail(), sud.getPassword(), sud.getPhoneNumber(), UserStatus.USER_STATUS_ACTIVE, sud.getRole());

            //HOST가 가입하는 경우
            if(sud.getRole() == UserRole.ROLE_HOST) {
                Hotel hotel = hotelRepository.findByHotelName(sud.getName());
                newUser.foreignHotel(hotel);
            }
            userRepository.save(newUser);
        } else {
            User findUser = optionalUser.get();
            //재가입 방지
            if(!checkStatus(findUser)){
                throw new DuplicateRequestException("이미 가입한 사용자입니다.");
            }
            //탈퇴한 회원이 재가입하는 경우
            else {
                findUser.changeStatus();
                userRepository.save(findUser);
            }
        }
    }

    //유저 상태 확인
    @Override
    public Boolean checkStatus(User user) {
        //재가입하는 경우 -> true
        return user.getStatus() == UserStatus.USER_STATUS_DEACTIVE;
    }

    //로그인
    @Override
    public void logIn(UserSignInDto sid) {
        User user = userRepository.findByEmail(sid.getEmail()).orElseThrow(
                () -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        //탈퇴한 회원이 로그인하는 경우
        if(user.getStatus() == UserStatus.USER_STATUS_DEACTIVE){
            throw new NoSuchElementException("탈퇴한 회원 입니다.");
        }

        if(passwordEncoder.matches(sid.getPassword(), user.getPassword())){
            //user 로그인
            td.createToken(String.valueOf(user.getRole()),
                    String.valueOf(user.getUserId()));
        } else { //비밀번호 not matches인 경우
            throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");
        }
    }
    @Override
    @Transactional
    public UserInfoDto getUserInfo(){
        User user = userRepository.findByTokenId(td.currentUserId());
        return new UserInfoDto(user.getName(), user.getEmail(),user.getPhoneNumber());
    }
    //유저 정보 업데이트
    @Override
    @Transactional
    public void updateInfo(UserInfoDto userInfoDto) {
        User user = userRepository.findByTokenId(td.currentUserId());
        user.updateInfo(userInfoDto);
    }

    //비밀번호 입력 받은 후 회원 탈퇴
    @Override
    @Transactional
    public void deactive(String password) {
        User user = userRepository.findByTokenId(td.currentUserId());
        if(!(passwordEncoder.matches(password, user.getPassword()))){
            throw new NoSuchElementException("탈퇴시 비밀번호를 정확히 입력해주세요.");
        }
        user.deactive();
    }

    //user 측, 예약 리스트
    @Override
    public List<UserReservationDto> reservationList(){
        Long userId = td.currentUserId();
        return reservationRepository
                .findAllByUser_UserId(userId)
                .stream().map(UserReservationDto::new).toList();
    }
}
