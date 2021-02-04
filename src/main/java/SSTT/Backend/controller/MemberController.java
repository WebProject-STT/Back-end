package SSTT.Backend.controller;

import SSTT.Backend.dto.MemberDto;
import SSTT.Backend.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("member", new MemberDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {
        memberService.idCheck(memberDto.getSignId()); // ID 중복 확인
        memberService.signUp(memberDto);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
