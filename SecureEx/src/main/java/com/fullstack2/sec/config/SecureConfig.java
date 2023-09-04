package com.fullstack2.sec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * 스프링부트 로그인 처리 방식
 * 
 * URL 요청 -> 분석 -> 스프링 시큐어 필터에서 사용자 인증을 하도록 log 페이지 활성화 ->
 * 각 필터에는 파라미터로 전달된 정보를 순차적으로 내려서 필터마다 적용된 일을 수행함.. ->
 * 필터까지 모두 처리되어 나온 결과는 인증매니저(인터페이스이름은 나중에) 가 로그인 방식을 분석함
 * 이 방식이라는거는(DB 로그인, 인메모리 로그인, 암호화 로그인, DB & OS 인증로그인 등..)
 * 로그인을 할때 처리되는 다양한 인증 방식을 정의한 객체를 이용해서 처리하도록 수행 요청함 -->
 * 이때 사용되는 대표적인 API 인증제공자(Au...Provider)라는 객체임...이 객체를 호출해서
 * 인증에 사용되는 Token(문자열 값) 등을 발생후 같이 넘겨줍니다.
 * 인증Provider 는 토큰의 타입(인증방식) 을 확인후에 내부의 authenication()을 호출해서 인증을 수행함
 * 
 * 여기서 다른 객체가 하나 더 나오는데, UserDetailService 라는 인터페이스 임.
 * 얘는 위의 xxxProvider 가 인증을 처리시 사용되는 객체인데, 실제 인증을 처리하는 데이터를 가져오는
 * 역할을 UserDeatilService 가 담당함.
 * 
 * 하나의 예를 들면....... DB 접근 하는 Repository --> UserDetailService 에서 데이터 get, --?
 * 가져온 데이터를 xxxProvider 가 검증 후 로그인 진행함...이런식으로 구조가 정의되어있음.
 * 
 * 스프링부트 2.0 이후부터는 사용자 정보중 암호는 무조건 암호화 되어야하는 규칙이 있음...
 * 즉 저장될때 암호화 되고, 가져올때 복호화 되어야함.
 * 
 * 이때 여러 암복호화 API를 같이 제공하는데, 대표적인 API BcryptPasswordEncoder 객체임
 * 이 객체는 특정 알고리즘으로 암호를 암호화 하는데, 문제는 복호화를 하는 기능이 없음..
 * 하지만 암호화 이전의 값과 암호화된 값이 서로 같은지는 비교하는 메서드를 보유 하고 있어서
 * 많이 사용됨.
 */
@Configuration
public class SecureConfig {
	/*
	 * 암호화를 적용하는 BcryptPasswordEncoder를 Bean 으로 선언해서
	 * 부트가 시작시에 메모리에 적재후 다른 API 에서 필요시 가져다 쓰게 합니다.
	 * 지금 작성하는 Config 클래스는 설정 클래스라고 해서, 어노테이션만 넣어주면
	 * 부트가 시작되자 마자 제일 먼저 자동으로 호출하는 설정 객체 입니다.
	 * PasswordEncoder 는 최상위 암호화 객체로 하위에는 이를 구현한 다양한
	 * 클래스가 있는데, 이중 BCryptPasswordEncoder를 기본 암호화 구현 객체로 사용합니다.
	 * 
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//인증될 사용자 정보를 관리하는 객체인 DetailManager 객체를 이용해서
	//서버메모리에 임시적으로 여러분의 정보를 설정하고 테스트 해볼겁니다.
	//이 객체의 하위 타입으로는 어떠한 인증 방식을 결정할지의 하위 인터페이스가 존재하는데
	//메모리 인증 방식은 InMemoryUserDetailManager 라는 애가 담당합니다.
	
	//시작하자마자 여러분의 정보가 메모리에 올라옵니다bean사용
	/*
	@Bean
	//메모리의 인증값과 비교해준다. 
	//DetailsManager애타입을 커스텀해서 특정소스로 만들어서 사용하는게 User을 dto화 시켜서
	//필수적인건 super로 해서 처리하는게 가장 쉽다.
	//UserDetailsManager User객체를 이용해서 내부적으로 사용자의정보를 User객체가 갖도록 하겠다.
	public InMemoryUserDetailsManager userDetailService() {
		UserDetails user = User.builder()
									.username("tint")
									.password(passwordEncoder().encode("1234"))
									.roles("USER")
									.build();
		
		System.out.println("생성된 User 객체의 값" + user);
		return new InMemoryUserDetailsManager(user);
			
	}
	*/
	
	//아래는 권한을 설정하는 Fillter 를 이용해서 URL path 마다 권한을 주도록 할게요.
	//메서드의 리턴은 SecurityFillterChain타입이어야 하고, 권한 설정을 하도록 하는 객체는
	//위 chain 에 파라미터로 매핑되는 HttpSecurity 객체입니다.
	//이 객체의 메서드를 이용, 로그인, 아웃, 권한 설정등을 할 수 있습니다.
	@Bean
	public SecurityFilterChain fillterChain(HttpSecurity http) throws Exception{
		
		//인가(authorization 작업(특정 path 에 대한 권한 작업 설정함), 메서드를 이용해서 함)
		http.authorizeHttpRequests(auth -> {
			
			//sample/all 모든애들한테 이페이지를 오픈하겠다
				auth.requestMatchers("/sample/all").permitAll();
				//sample/member는 오직 USER 권한(인가)를 획득한 유저만 들어오게.
				auth.requestMatchers("/sample/member").hasRole("USER");
		});
		
		//http 객체에는 권한이 없는 경우 로그인 폼으로 되돌리도록 하는 메서드도 있음.
		http.formLogin();
		http.csrf().disable();
		http.logout();
		return http.build(); 
	
	}

}
