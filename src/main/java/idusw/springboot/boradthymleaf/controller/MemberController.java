package idusw.springboot.boradthymleaf.controller;

import idusw.springboot.boradthymleaf.domain.Member;
import idusw.springboot.boradthymleaf.domain.Memo;
import idusw.springboot.boradthymleaf.service.MemberService;
import idusw.springboot.boradthymleaf.service.MemoService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {
    // Constructor DI(Dependency Injection)
    MemberService memberService;
    public MemberController(MemberService memberService) { // Spring Framework에게 주입(하도록 요청함)
        this.memberService = memberService;
    }
    HttpSession session = null;
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("member", Member.builder().build()); // email / pw 전달을 위한 객체
        return "/members/login"; // view : template engine - thymeleaf.html
    }
    @PostMapping("/login")
    public String loginMember(@ModelAttribute("member") Member member, HttpServletRequest request) { // 로그인 처리 -> service -> repository -> service -> controller
       Member result = null;
       if((result = memberService.login(member)) != null ) { // 정상적으로 레코드의 변화가 발생하는 경우 영향받는 레코드 수를 반환
           session = request.getSession();
           session.setAttribute("mb", result);
           return "redirect:/";
       }
       else
            return "/main/error";
    }
    @PostMapping("/logout")
    public String logoutMember() {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/register")
    public String getRegisterForm(Model model) { // form 요청 -> view (template engine)
        model.addAttribute("member", Member.builder().build());
        return "/members/register";
    }
    @PostMapping("/register")
    public String createMember(@ModelAttribute("member") Member member, Model model) { // 등록 처리 -> service -> repository -> service -> controller
        if(memberService.create(member) > 0 ) // 정상적으로 레코드의 변화가 발생하는 경우 영향받는 레코드 수를 반환
            return "redirect:/";
        else
            return "/main/error";
    }
    @GetMapping("/{seq}")
    public String getMember(@PathVariable("seq") Long seq, Model model) {
        Member result = new Member(); // 반환
        Member m = new Member(); // 매개변수로 전달
        m.setSeq(seq);
        result = memberService.read(m);
        // MemberService가 MemberRepository에게 전달
        // MemberRepository는 JpaRepository 인터페이스의 구현체를 활용할 수 있음
        model.addAttribute("member", result);
        return "/members/detail";

    }
    @GetMapping("/update")
    public String getUpdateForm() { // form 요청 -> view (template engine)
        return "/members/update";
    }
    @PutMapping("/update")
    public String updateMember() { // 등록 처리 -> service -> repository -> service -> controller
        return "redirect:/";
    }
    @DeleteMapping("/delete")
    public String deleteMember() { // 등록 처리 -> service -> repository -> service -> controller
        return "redirect:/";
    }
    @GetMapping("/forgot")
    public String getForgotForm() { // 분실 비밀번호 처리 요청 -> view
        return "/members/forgot-password"; // view : template engine - thymeleaf.html
    }
    @PostMapping("/forgot") // create vs update -> @PutMapping, delete -> @DeleteMapping
    public String forgotMemberPassword() { // 비밀번호(갱신) -> service -> repository -> service -> controller
        return "redirect:/"; // 루트로 이동
    }
}
