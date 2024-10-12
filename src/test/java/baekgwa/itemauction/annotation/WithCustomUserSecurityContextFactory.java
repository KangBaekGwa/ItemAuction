package baekgwa.itemauction.annotation;

import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.userdetail.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // User 객체 생성
        User newUser = User.createNewUser(customUser.username(), customUser.password());
        newUser.updateRole(customUser.role());
        CustomUserDetails customUserDetails = new CustomUserDetails(newUser);

        // Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
                null,
                customUserDetails.getAuthorities());

        context.setAuthentication(authentication);
        return context;
    }
}
