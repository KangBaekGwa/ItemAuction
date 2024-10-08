package baekgwa.itemauction.domain.userdetail.service;

import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.userdetail.dto.CustomUserDetails;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userRepository.findByLoginId(username).orElseThrow(
                () -> new CustomException(CustomErrorCode.FIND_USER_ERROR_NOT_FIND)
        );

        return new CustomUserDetails(userData);
    }
}