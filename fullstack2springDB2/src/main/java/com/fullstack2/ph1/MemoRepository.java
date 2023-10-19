package com.fullstack2.ph1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack2.ph1.entity.Memo;

/*
 * 2023-08-25
 * Repository : 스프링에서 제공하는 실제 CRUD 를 수행하는 인터페이스 입니다.
 * 이 객체를 이용해서 Entity 객체를 대상으로 CRUD 를 진행하고, Entity 를 반영 결과를 
 * 실제 Table 에 반영합니다.
 * 
 * 때문에 모든 DB 작업은 이 객체를 이용하게 됩니다.
 * 
 * 이 인터페이스는 내부적으로 상속 관계를 갖게되는데, Super 로는 Repository 라는 애가 있고,
 * 하위로는 단계별로, DB 처리시 필요한 기능을 추가로 정의한 애들로 구성되어 있습니다.
 * 
 * 이중, JpaRespository 라는 최하위 인터페이스가 있는데, 얘는 위의 부모들이 정의한 CRUD 의 기본 기능외에
 * 페이징 처리를 할수 있는 기능까지 정의한 애입니다.
 * 
 * 때문에 기본적인 CRUD 를 진행하는 경우, super 인터페이스를 사용하거나, 페이지등의 처리까지 하는 경우엔
 * JpaRepository 를 사용하는게 일반적입니다.
 * 
 * 사용방법은 매우 간단합니다. Repository 를 수행하는 클래스를 선언하고 위 인터페이스중 하나를 상속받으면
 * 끝납니다.
 * 
 * 이렇게 하면, 스프링이 수행되는 시점에 해당 인스턴스를 자동으로 생성해서 메모리에 올려주고
 * 이렇게 올라간 Repository 의 메서드를 이용해서 CRUD 를 진행합니다.
 * 
 * Find ID 
 * update건 insert건 데이터에 올릴떄는 save만 날리면된다.
 * 
 * 
 * JpaRepository 의 메서드
 * 
 * insert, update : save(Entity)
 * 
 * delete : deleteById(key), delete(entity)
 * 
 * select : findById(key), getOne(key)
 *  
 */

//jpa Repository 제네릭 타입으로는 대상 Entity 객체명, 엔티티의 PK Type 을 줘야함
//레포지토리 생성완료!
public interface MemoRepository extends JpaRepository<Memo, Long>{
	//레포지토리에 쿼리메서드를 이용해서 쿼리를 날리는걸 생성함
	List<Memo> findByMnoBetween(Long start, Long end);
	
	List<Memo> findByMemoTextLike(String key);//lie 키워드로 memoText 내의 해당 하는 문자열이 포함된 리스트 찾기
	
	Page<Memo> findByMnoBetween(Long start, Long end, Pageable pageable);
	
	//update EntityName alias alias.컬럼명 = :parameter where m.mno = :mno
	
	//여기가 수행하는 메서드 쪽임
	@Transactional
	@Modifying//Select 쿼리가 아닌 쿼리 메서드에 선언하세요.
	@Query("update Memo m SET m.memoText = :memotext where m.mno = :mno")
	int updateMemoText(@Param("mno") Long mno, @Param("memotext") String memotext);
}

