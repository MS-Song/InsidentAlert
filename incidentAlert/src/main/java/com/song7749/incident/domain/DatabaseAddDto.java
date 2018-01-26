package com.song7749.incident.domain;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.jdbc.DatabaseDriver;

import com.song7749.base.AbstractDto;
import com.song7749.incident.type.Charset;

public class DatabaseAddDto extends AbstractDto {

	private static final long serialVersionUID = 2469669840827588753L;

	@Length(max=120)
	@NotBlank
	private String host;

	@Length(max=120)
	@NotBlank
	private String hostAlias;

	@Length(max=120)
	@NotBlank
	private String schemaName;

	@Length(max=60)
	@NotBlank
	private String account;

	@Length(min=4,max=20)
	@NotBlank
	private String password;

	@Enumerated(EnumType.STRING)
	@NotNull
	private DatabaseDriver driver;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Charset charset;

	@Length(max=5)
	@NotBlank
	private String port;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public DatabaseAddDto() {}


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
	public DatabaseAddDto(
			 String host,
			 String hostAlias,
			 String schemaName,
			 String account,
			 String password,
			 DatabaseDriver driver,
			 Charset charset,
			 String port,
			 Date createDate) {
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
		this.createDate=createDate;
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
}