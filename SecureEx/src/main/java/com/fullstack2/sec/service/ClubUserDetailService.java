package com.fullstack2.sec.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullstack2.sec.ClubMemberRepository;
import com.fullstack2.sec.dto.ClubAuthDTO;
import com.fullstack2.sec.entity.ClubMember;

import lombok.RequiredArgsConstructor;

//서비스 등록해야 bean에서 가져다 쓸수있따.
@Service
@RequiredArgsConstructor
public class ClubUserDetailService implements UserDetailsService {

	//사용자 정보 가져오기
	private final ClubMemberRepository clubmemberRepository;
	
	//userDetails 객체사용
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//사용자 정보를 repository 에서 가져온후, 그 정보를 DTO 에 세팅을 합니다.
		//그렇게 되면 해당 DTO 는 User 클래스를 상속을 받았기 때문에, 더이상의 작업이 필요없이
		//그대로 인증과 인가 작업을 유지 할 수 있습니다.
		Optional<ClubMember> res = clubmemberRepository.findbyEmail(username, false);
		
		//만약 데이터가 존재하지 않는다면, 사용자가 없는것입니다... 왜냐면
		//위 메서드의 username 은 Clubmember entity의 key 이기 때문에, 이 키는 email 과
		//매핑이 되어서 repository 로 조회를 한것이기 때문입니다.
		//만약 조회된게 없을시엔 예외 발생시킴
		if(res.isEmpty()) {
			throw new UsernameNotFoundException("사용자 정보가 없거나, 소셜 로그인 정보 확인");
		}
		
		ClubMember member = res.get();
		//존재한다면 아래에서 처럼 UserDTO 객체를 생성합니다.
		ClubAuthDTO dto = new ClubAuthDTO(
											member.getEmail(),
											member.getPassword(),
											member.getRoleSet().stream().map(role
													-> new SimpleGrantedAuthority("ROLE_" +role.name())).collect(Collectors.toSet()),
											false);
											//member.isFromSocial());
		dto.setFromSocial(member.isFromSocial());
		dto.setName(member.getName());
		
		return dto;
	}

}
