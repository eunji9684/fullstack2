package com.fullstack2.question.controller;



import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fullstack2.question.form.UserCreateForm;
import com.fullstack2.question.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm,
				BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
					"2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		//중복회원 가입 방지
		try {
			userService.create(userCreateForm.getUsername(), 
					userCreateForm.getEmail(),
					userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
			return "signup_form";
		}catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/user/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	

    @GetMapping("/find_username")
    public String showFindUsernameForm(Model model) {
        // 아이디 찾기 화면을 템플릿으로 전달할 수 있는 로직 추가
        return "find_username";
    }
    
    @GetMapping("/find_password")
    public String showFindpasswordForm(Model model) {
        //비밀번호 찾기 화면을 템플릿으로 전달할 수 있는 로직 추가
        return "find_password";
    }
    
    
	

}
