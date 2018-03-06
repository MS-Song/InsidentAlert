package com.song7749.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <pre>
 * Class Name : AbstractDto.java
 * Description : 모든 조회성 DTO 는 Abstract DTO를 상속 받아야 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 15.		song7749@gmail.com		New
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 1. 15.
 */
public abstract class AbstractFindDto extends BaseObject implements Dto, Cacheable {

	private static final long serialVersionUID = 8863605294397638654L;

	private Long limit = 100L;

	private Long offset = 0L;

	private boolean useLimit = true;

	private boolean useCache = false;

	/**
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Long limit) {
		this.limit = limit;
	}

	/**
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	/**
	 * @return the useLimit
	 */
	public boolean isUseLimit() {
		return useLimit;
	}

	/**
	 * @param useLimit
	 *            the useLimit to set
	 */
	public void setUseLimit(boolean useLimit) {
		this.useLimit = useLimit;
	}

	@Override
	public boolean isUseCache() {
		return useCache;
	}

	@Override
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}