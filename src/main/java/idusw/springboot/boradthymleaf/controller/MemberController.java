package idusw.springboot.boradthymleaf.controller;

import idusw.springboot.boradthymleaf.domain.Member;
import idusw.springboot.boradthymleaf.service.MemberService;
import idusw.springboot.boradthymleaf.service.MemoService;
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

    @GetMapping("/login")
    public String getLoginForm() {
        return "/members/login"; // view : template engine - thymeleaf.html
    }
    @PostMapping("/login")
    public String loginMember() { // 로그인 처리 -> service -> repository -> service -> controller
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
    public String getMember(@PathVariable("seq") Long seq, ) {
        
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
