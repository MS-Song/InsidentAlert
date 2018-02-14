package com.song7749.incident.value;

import java.util.Date;

import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.BaseObject;
import com.song7749.base.Vo;
import com.song7749.incident.type.Charset;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("데이터베이스 연결 정보")
public class DatabaseVo extends BaseObject implements Vo {

	private static final long serialVersionUID = 2469669840827588753L;

	@ApiModelProperty(value = "Database Info ID")
	private Long id;

	@ApiModelProperty("Database Host Name (IP Adress OR Domain)")
	private String host;

	@ApiModelProperty("Database Host Alias")
	private String hostAlias;

	@ApiModelProperty("Database Schema(Mysql), SID(Oracle)... ")
	private String schemaName;

	@ApiModelProperty("Database Account")
	private String account;

	@ApiModelProperty("Database Driver Selection")
	private DatabaseDriver driver;

	@ApiModelProperty("Database Connnect Charicter Set")
	private Charset charset;

	@ApiModelProperty("Database Connect Port")
	private String port;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	@ApiModelProperty(value = "Create Date-Time")
	private Date createDate;

	/**
	 * @param id
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param driver
	 * @param charset
	 * @param port
	 * @param createDate
	 */
	public DatabaseVo(Long id, String host, String hostAlias, String schemaName, String account, DatabaseDriver driver,
			Charset charset, String port, Date createDate) {
		this.id = id;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
		this.createDate = createDate;
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
