package mini.project.HotelReservation.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Data.Dto.UserDto;
import mini.project.HotelReservation.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.Data.Entity.User;
import mini.project.HotelReservation.Data.Enum.UserStatus;
import mini.project.HotelReservation.Repository.UserRepository;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Override
    public void join(UserSignUpDto sud) {
        //탈퇴한 회원이 재가입하는 경우
        if(!checkStatus(sud.getEmail())){
            Optional<User> user = userRepository.findStatusByEmail(sud.getEmail());
            user.get().changeStatus();
        }

        //처음 가입하는 경우
        else {
            User user = User.builder()
                    .name(sud.getName())
                    .email(sud.getEmail())
                    .password(sud.getPassword())
                    .phoneNumber(sud.getPhoneNumber())
                    .status(UserStatus.USER_STATUS_ACTIVE)
                    .role(sud.getRole())
                    .build();

            User saveUser = userRepository.save(user);
        }
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
    public UserDto logIn(UserSignInDto sid) {
        User user = userRepository.findStatusByEmail(sid.getEmail()).orElseThrow(
                () -> new NoSuchElementException());
        //계정 정보 확인
        if(user.getStatus() == UserStatus.USER_STATUS_DEACTIVE){
            throw new NoSuchElementException();
        }
        //todo : jwt 생성 후 비밀번호 확인 로직 구현
//        if(passwordEncoder.matches(sid.getPassword(), user.getPassword())){
//
//        }
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

}
