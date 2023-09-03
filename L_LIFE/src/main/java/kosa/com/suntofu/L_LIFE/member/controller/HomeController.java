package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.SessionConst;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomeController {
    @GetMapping
    public String home(HttpServletRequest request, Model model) {
        //세션 반환
        HttpSession session = request.getSession(false);

        // 로그인한 사용자가 아니라면 home으로 보낸다.
        if ( session == null) {
            return "pages/main/main";
        }

        MemberVo existingMember = (MemberVo) session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 사용자가 없으면 null 처리 필요
        // 세션은 있지만 existingMember객체가 null이라면
        if(existingMember == null) {
            return "pages/member/login";
        }

        // loginHome : 로그인에 성공한 사람만이 볼 수 있는 화면 - 만들어야겠다
        model.addAttribute("member", existingMember);
        return "pages/main/main";
    }

    @GetMapping
    public String homev4(@SessionAttribute(name=SessionConst.LOGIN_MEMBER,required=false)MemberVo existingMember, Model model) {

        // 사용자가 없으면 null 처리 필요
        if(existingMember == null) {
            return "pages/member/login";
        }

        // loginHome : 로그인에 성공한 사람만이 볼 수 있는 화면
        model.addAttribute("member", existingMember);
        return "pages/main/main";
    }
}
