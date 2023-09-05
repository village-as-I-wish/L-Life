package kosa.com.suntofu.L_LIFE.member.interceptor;

import kosa.com.suntofu.L_LIFE.member.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            // 로그인 되지 않음
            System.out.println("[미인증 사용자 요청]");

            //로그인으로 redirect
            response.sendRedirect("/l-life/member/login?redirectURL=" + requestURI);
            return false;
        }
        // 로그인 되어있을 때
        return true;
    }
}
