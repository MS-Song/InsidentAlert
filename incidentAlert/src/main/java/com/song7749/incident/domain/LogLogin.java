
package com.song7749.incident.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <pre>
 * Class Name : LogLogin.java
 * Description : Member Login Log
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	New
 *
 * </pre>
 *
 * @author song7749
 * @since 2016. 2. 22.
 */

@Entity
@DiscriminatorValue("LOGIN")
public class LogLogin extends Log {

	private static final long serialVersionUID = -5741549743951708157L;

	@NotBlank
	@Size(min = 4, max = 20)
	@Column(nullable = false, updatable = false)
	private String loginId;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String cipher;

	public LogLogin() {}

	/**
	 * @param loginId
	 * @param cipher
	 */
	public LogLogin(@NotBlank @Size(min = 4, max = 20) String loginId, @NotBlank String cipher) {
		this.loginId = loginId;
		this.cipher = cipher;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}
}