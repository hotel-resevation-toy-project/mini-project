package mini.project.HotelReservation.User.Service;


import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
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
    private final PasswordEncoder passwordEncoder;
    private TokenDecoder td;

    @Override
    public void join(UserSignUpDto sud) {
        //탈퇴한 회원이 재가입하는 경우
        if(!checkStatus(sud.getEmail())){
            Optional<User> user = userRepository.findStatusByEmail(sud.getEmail());
            user.get().changeStatus();
        }

        //USER가 가입하는 경우
        User user = User.builder()
                .name(sud.getName())
                .email(sud.getEmail())
                .password(sud.getPassword())
                .phoneNumber(sud.getPhoneNumber())
                .status(UserStatus.USER_STATUS_ACTIVE)
                .role(sud.getRole())
                .build();

        //HOST가 가입하는 경우
        if(sud.getRole() == UserRole.ROLE_HOST){
            Hotel hotel = hotelRepository.findByHotelName(sud.getName());
            user.foreignHotel(hotel);
        }

        User saveUser = userRepository.save(user);
    }

    @Override
    public Boolean checkStatus(String email) {
        Optional<User> optionalUser = userRepository.findStatusByEmail(email);

        if(optionalUser.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public void logIn(UserSignInDto sid) {
        User user = userRepository.findStatusByEmail(sid.getEmail()).orElseThrow(
                () -> new NoSuchElementException("회원을 찾을 수 없습니다."));

        //계정 정보 확인
        if(user.getStatus() == UserStatus.USER_STATUS_DEACTIVE){
            throw new NoSuchElementException();
        }

        if(passwordEncoder.matches(sid.getPassword(), user.getPassword())){

            if (user.getRole() == UserRole.ROLE_USER) {
                td.createToken(String.valueOf(user.getRole()),String.valueOf(user.getUserId()));
            } else {
                td.createToken(String.valueOf(user.getRole()),
                                String.valueOf(user.getUserId()),
                                String.valueOf(user.getHotel().getHotelId()));
            }
            new UserInfoDto(user);
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void updateInfo(UserInfoDto userInfoDto) {
        User user = userRepository.findById(td.currentUser().getUserId()).orElseThrow(
                () -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        user.updateInfo(userInfoDto);
    }

    @Override
    public void deactive(String email, UserSignInDto usd) {
        User user = userRepository.findStatusByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 유저를 찾을 수 없습니다.")
        );
        if(passwordEncoder.matches(usd.getPassword(), user.getPassword())){
            throw new NoResultException("비밀번호를 정확히 입력해주세요.");
        }
        user.deactive();
    }
    @Override
    public List<UserReservationDto> reservationList(Long userId){
        List<Reservation> reservationsByUserId = userRepository.findReservationsByUserId(userId);
        List<UserReservationDto> reservations = new ArrayList<>();
        for (Reservation reservation : reservationsByUserId) {
            reservations.add(new UserReservationDto(reservation.getHotelName(),
                                                            reservation.getCheckInDate(),
                                                            reservation.getCheckOutDate()));
        }
        return reservations;
    }

    @Override
    public User loadUserByUserId(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchElementException("해당 유저를 찾을 수 없습니다.");
        }
    }
}
