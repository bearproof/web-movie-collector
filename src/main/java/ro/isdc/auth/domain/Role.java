package ro.isdc.auth.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * Role entity class
 * </p>
 * 
 * @author Adrian.Ursu
 */
@Document
public class Role {

	@Id
	private String id;

	@NotNull
	@Size(min = 1, max = 100)
	private String roleName;

	public Role() {
	}

	public Role(final String roleName) {
		this.roleName = roleName;
	}

	/** GETTERS/SETTERS **/

	public String getRoleName() {
		return roleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
