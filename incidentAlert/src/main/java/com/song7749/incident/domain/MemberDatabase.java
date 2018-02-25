package com.song7749.incident.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.song7749.base.Entities;

/**
 * <pre>
 * Class Name : MemberDatabase.java
 * Description : 회원과 데이터베이스 간의 연결 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 5. 2.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 5. 2.
*/

@Entity
public class MemberDatabase extends Entities{

	private static final long serialVersionUID = 7810908005225133156L;

	@Id
	@Column(name="member_databse_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(targetEntity=Database.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "database_id", nullable = false, insertable = true, updatable = false)
	private Database database;

	@NotNull
	@ManyToOne(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, insertable = true, updatable = false)
	private Member member;


	public MemberDatabase() {}

	/**
	 * @param database
	 */
	public MemberDatabase(@NotNull Database database) {
		this.database = database;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Database getDatabase() {
		return database;
	}


	public void setDatabase(Database database) {
		this.database = database;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}
}