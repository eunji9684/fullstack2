package com.fullstack2.sec.entity;

import java.util.HashSet;
import java.util.Set;



import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//이 엔티티는 회원엔티티 입니다.
//키와 속성 및 권한을 줄 예정인데, 한 사용자는 하나 이상의 권한을 부여받게 될 예정입니다.
//권한을 정의한 클래스는 ENUM 으로 따로 정의할 예정이고, ENUM 은 상수를 정의한 클래스라고 보시면 됩니다.

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity{
		
	@Id
	private String email;
	private String password;
	private String name;
	private boolean fromSocial;
	
	//여기서는 각 member 들이 생성될때 권한을 갖게 할 예정입니다.
	//먼저 이 권한을 부여할 메서드를 정의 할 예정인데, 권한값을 가진 컬렉션을 먼저 생성할게요.
	//아래의 @ElementCollection 은 Entity 생성시에 manyToOne 을 적용하지 않아도
	//자동으로 참조를 걸게하는 어노테이션입니다.
	
	@Builder.Default //ElementCollection사용할때 반드시 들어가야한다.이넘 위에다가 걸어야된다.
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<ClubMemberRole> roleSet = new HashSet<>();
	
	//각 멤버가 DB 에 인서트될때 참조되는 Enum을 추가하는 메서드 정의
	public void addMemberRole(ClubMemberRole clubMemberRole) {
		roleSet.add(clubMemberRole);
	}
}
