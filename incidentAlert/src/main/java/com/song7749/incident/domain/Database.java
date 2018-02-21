package com.song7749.incident.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.Entities;
import com.song7749.incident.type.Charset;

/**
 * <pre>
 * Class Name : DatabaseServerInfo.java
 * Description : DatbaseServer 접속 정보 저장
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 16.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 16.
*/

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
public class Database extends Entities{

	private static final long serialVersionUID = 8561337661359215895L;

	@Id
	@Column(nullable=false,updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	@Length(max=120)
	@NotBlank
	private String host;

	@Column(nullable=false)
	@Length(max=120)
	@NotBlank
	private String hostAlias;

	@Column(nullable=false)
	@Length(max=120)
	@NotBlank
	private String schemaName;

	@Column(nullable=false)
	@Length(max=60)
	@NotBlank
	private String account;

	@Column(nullable=false)
	@Length(min=4,max=20)
	@NotBlank
	private String password;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private DatabaseDriver driver;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Charset charset;

	@Column(nullable=false)
	@Length(max=5)
	@NotBlank
	private String port;

	@Column(nullable=false,updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern="yyyy-MM-dd h:i:s")
	private Date createDate;

	@Column(nullable=true)
	@UpdateTimestamp
	@DateTimeFormat(pattern="yyyy-MM-dd h:i:s")
	private Date modifyDate;

	public Database() {}


	/**
	 * @param id
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 */
	public Database(
			 Long id,
			 String host,
			 String hostAlias,
			 String schemaName,
			 String account,
			 String password,
			 DatabaseDriver driver,
			 Charset charset,
			 String port) {
		super();
		this.id = id;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
	}


	/**
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 */
	public Database(
			String host,
			String hostAlias,
			String schemaName,
			String account,
			String password,
			DatabaseDriver driver,
			Charset charset,
			String port) {
		super();
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getHostAlias() {
		return hostAlias;
	}


	public void setHostAlias(String hostAlias) {
		this.hostAlias = hostAlias;
	}


	public String getSchemaName() {
		return schemaName;
	}


	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public DatabaseDriver getDriver() {
		return driver;
	}


	public void setDriver(DatabaseDriver driver) {
		this.driver = driver;
	}


	public Charset getCharset() {
		return charset;
	}


	public void setCharset(Charset charset) {
		this.charset = charset;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}

	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}