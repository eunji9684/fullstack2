package com.fullstack2.sec;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fullstack2.sec.entity.ClubMember;
import com.fullstack2.sec.entity.ClubMemberRole;

@SpringBootTest
class SecureExApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClubMemberRepository clubMemberRepository;
	
	@Test
	void contextLoads() {
		
		Optional<ClubMember> res = clubMemberRepository.findbyEmail("user10@abc.com", false);
		ClubMember member = res.get();
		System.err.println(member);
		/*
		//하나의 회원이 하나 이상의 권한을 갖도록 밀어넣을게요.
		IntStream.rangeClosed(1, 100).forEach(i -> {
			ClubMember member = ClubMember.builder()
													.email("user" + i + "@abc.com")
													.name("회원" + i)
													.fromSocial(false)
													.password(passwordEncoder.encode("1111"))
													.build();
			
			//기본 ROLE 설정을 대입
										member.addMemberRole(ClubMemberRole.USER);
										
										if(i > 80 && i <=90) {
											//role 추가
											member.addMemberRole(ClubMemberRole.MANAGER);
										}if(i >90) {
											member.addMemberRole(ClubMemberRole.ADMIN);
										}
			
		clubMemberRepository.save(member);
			
		});
		
		*/
		
		
		
		
		String passwod="1234";
		String encPw = passwordEncoder.encode(passwod);
		
		System.out.println("암호화된 값 : " + encPw);
		
		boolean isMatch = passwordEncoder.matches(passwod, encPw);
		
		System.out.println("같은 값인지의 결과 : " + isMatch);
	}

}
