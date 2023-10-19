package com.fullstack2.ph1.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 *  @Entity : JPA 에서 실제 DB의 테이블과 매칭될 class 임을 명시하는 어노테이션(= DB TABLE 이다 라고생각하면 편함)
 *  @Table : Entity 테이블에 매핑할 테이블 정보를 알려주는 어노테이션
 *  속성으로 name을 갖는데, 이 값을 주면, 값으로 테이블 생성됨.
 *  Default 는 Entity클래스 이름과 같은 이름으로 매핑됨
 *  @ID : JPA 에서는 기본적으로 모든 테이블에 PK 를 주도록 정의함
 *  따라서 이 어노테이션을 주게되면 해당 컬럼이 자동으로 PK 지정됨
 *  일반적으로 자바 NUMBER 타입을 사용하는데, 필요하다면 문자열도 PK 지정됨
 *  참고로 부트에서는 나중에 배울 Stream(IO 아님) 을 많이 사용하기 때문에
 *  ptype 의 넘버 보다는 Wrapper 타입으로 사용하는게 일반적임.
 *   
 */

@Entity
@Table(name = "tb_memo")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder //--> 롬복의 기능중 하나로 builder() 를 호출후 setter 에 값 설정후 bulld() 를 호출하며
//인스턴스를 생성해 주는 어노테이션 입니다.

public class Memo {
	
	//컬럼의 PK를 지정시에, PK의 번호를 자동으로 지정하는 어노테이션이 @GeneratedValue 입니다.
	//여러가지가 있는데, 주체가 누가 되는지를 결정하는 값입니다.
	//테이블에서, 하이버네이트에서, Auto_increment 로 등의 다양한 값을 설정할 수 있습니다.
	/*
	 * 키 생성전략 설명
	 * auto(Default) : 하이버네이트가 생성해서 부여합니다.
	 * identity : DB에서 직업 부여 합니다. maria 인 경우엔 auto_increment가 지정됨
	 * sequence : 오라클등에서 사용하는 시쿼스 객체가 지정합니다..이때는  @sequenceGeneratir와 같이
	 * 			  사용해야 합니다. 
	 * 
	 * @Column : 당연히 컬럼을 나타내며, PK가 아닌 다른 컬럼을 지정(속성을 정하는것)할 때 사용함.
	 * 만약 선언하지 않으면, 모든 필드는 자동으로 타입에 맞게 컬럼으로 생성됩니다.
	 * 그럼왜쓸까요?? 이유는 컬럼의 속성을 지정할 때 사용합니다.
	 * 예를 들자면, NN, 문자열 길이등을 따로 지정할때 사용합니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mno;
	
	@Column(length = 100, nullable = false)
	private String memoText;
}
