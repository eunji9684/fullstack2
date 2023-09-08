package com.fullstack2.sec;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.query.Param;

import com.fullstack2.sec.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
		
	//@EntityGraph : 이놈은 내부적으로 lazy 조인이 걸린 엔티티간의 Left Join 을 알아서
	//수행해 주는 놈입니다.s
	//따로 조인 쿼리를 JPQL 에 하지 않더라도, 이넘만 있으면, 연관테이블의 FK를 찾아서 LEFT JOIN 합니다.
	//속성으로는 속성패스와, ..Graph 로더를 넘기는게 있는데, 일반적으로 아래처럼 사용합니다.
	
	@EntityGraph(attributePaths = {"roleSet"}, type =EntityGraph.EntityGraphType.LOAD)
	@Query("Select m From ClubMember m where m.fromSocial = :social and m.email = :email")
	//권한이 없어도 회원정보는 다땡겨와야되기때문에 left 조인
	Optional<ClubMember> findbyEmail(@Param("email") String email,@Param("social")  boolean social);
}
