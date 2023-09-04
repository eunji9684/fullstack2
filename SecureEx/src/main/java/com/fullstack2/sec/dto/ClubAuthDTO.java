package com.fullstack2.sec.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * DB에서 가져온 값을 사용자가 입력한 내용과 비교해서 인증 및 인가 처리를 하려면
 * Spring Security 에서는 반드시 아래의 세단계를 단계적으로 pass 해야 합니다.
 * 1.PK 로 지정된 사용자 ID 비교
 * 2.1이 완료되면 암호화된 암호값 비교
 * 3.2가 완료되면, 인증은 끝났으니, 인가 처리를 해야 하므로 role 비교 및 설정입니다.
 * 
 * 이를 모두 직접 구현하기 보다는 DTO 를 생성하고 이에 필요한 필수 필드를 선언 및
 * 요청받은 내용을 DTO 에 세팅하고 super 로 넘기는 방식입니다.(위 세필드만)
 * 그리기 위해서는 이 DTO 가 User 라는 SpringSecrity 클래스의 자식으로 정의
 * 되면 되겠네요.
 */

@Getter
@Setter
@ToString
//super사용시엔 allargu나 noargu넣으면 안된다.
public class ClubAuthDTO extends User{
	
	//엔티티 내용과 똑같이 작성한다.
	private String email;
	private String password;
	private boolean fromSocial;
	private String name;
	//core.userdetails.User 객체는 내부 필드명으로 username 을 사용하는데,
	//얘가 의미하는게 PK를 의미합니다. 따라서 우리가 상속을 받아서 사용시 같은 이름을
	//필드로 사용하게 되면 필드 은닉 현상이 발생되므로, 다른 이름을 사용할 것을 강추해요
	//해서 아래에 보면, 우리가 일반적으로 사용하는 username 이 아니라 그 뜻으로 email 을 사용
	//한겁니다.
	//username 은 pk의 key다  끝에 boolean fromSocial추가해준다.
	public ClubAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean fromSocial) {
		super(username, password, authorities);
		
	
		this.email = username;
		this.fromSocial = fromSocial;
	}
		
		
	
}
