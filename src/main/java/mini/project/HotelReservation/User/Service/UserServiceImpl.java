package mini.project.HotelReservation.User.Service;


import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            User newUser = User.builder()
                    .name(sud.getName())
                    .email(sud.getEmail())
                    .password(passwordEncoder.encode(sud.getPassword()))
                    .phoneNumber(sud.getPhoneNumber())
                    .status(UserStatus.USER_STATUS_ACTIVE)
                    .role(sud.getRole())
                    .build();
            //HOST가 가입하는 경우
            if(sud.getRole() == UserRole.ROLE_HOST) {
                Hotel hotel = hotelRepository.findByHotelName(sud.getName());
                newUser.foreignHotel(hotel);
            }
            User saveUser = userRepository.save(newUser);
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
        if (user.getStatus() == UserStatus.USER_STATUS_DEACTIVE) {
            //재가입하는 경우
            return true;
        } else {
            //가입된 경우
            return false;
        }
    }

    //로그인
    @Override
    public void logIn(UserSignInDto sid) {
        User user = userRepository.findByEmail(sid.getEmail()).orElseThrow(
                () -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        //계정 정보 확인
        if(user.getStatus() == UserStatus.USER_STATUS_DEACTIVE){
            throw new NoSuchElementException("회원을 찾을 수 없습니다.");
        }
        if(passwordEncoder.matches(sid.getPassword(), user.getPassword())){

            if (user.getRole() == UserRole.ROLE_USER) {
                td.createToken(String.valueOf(user.getRole()),
                        String.valueOf(user.getUserId()));
            } else {
                td.createToken(String.valueOf(user.getRole()),
                                String.valueOf(user.getUserId()),
                                String.valueOf(user.getHotel().getHotelId()));
            }
            new UserInfoDto(user);
        }else {
            throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");
        }
    }

    //유저 정보 업데이트
    @Override
    @Transactional
    public void updateInfo(UserInfoDto userInfoDto) {
        User user = userRepository.findById(td.currentUser().getUserId()).orElseThrow(
                () -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        user.updateInfo(userInfoDto);
    }

    //비밀번호 입력 받은 후 회원 탈퇴
    @Override
    @Transactional
    public void deactive(String password) {
        User user = td.currentUser();
        if(!(passwordEncoder.matches(password, user.getPassword()))){
            throw new NoSuchElementException("비밀번호를 정확히 입력해주세요.");
        }
        user.deactive();
    }
    @Override
    public List<UserReservationDto> reservationList(){
        List<Reservation> reservationsByUserId = reservationRepository.findAllByUser_UserId(td.currentUser().getUserId());
        List<UserReservationDto> reservations = new ArrayList<>();
        for (Reservation reservation : reservationsByUserId) {
            reservations.add(new UserReservationDto(reservation.getReserveNumber(), reservation.getHotelName(),
                                                            reservation.getCheckInDate(),
                                                            reservation.getCheckOutDate()));
        }
        return reservations;
    }

    @Override
    // 유저 정보 수정시 토큰에 mapping되어 있는 유저 객체도 refresh 해줘야함.
    public User loadUserByUserId(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("해당 유저를 찾을 수 없습니다.");
        }
    }
}
