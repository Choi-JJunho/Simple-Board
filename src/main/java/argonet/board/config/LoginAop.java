package argonet.board.config;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@RequiredArgsConstructor
public class LoginAop {

    private final JwtGenerator jwtGenerator;

    @Before("@annotation(Login)")
    public void checkLogin(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =requestAttributes.getRequest();
        String token = request.getHeader("Authorization");
        Boolean isBearer = token.startsWith("Bearer ");
        if(token == null || !isBearer) {
            throw new Exception("Not Valid Token");
        }
        if(!jwtGenerator.checkJwt(token.substring("Bearer ".length()))) {
            throw new Exception("Not Valid User...");
        }
    }
}
