package jp.co.hands.hunting.entity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	@Column(name="delete_flag")
	private boolean deleteFlag;
	
	@Column(name="registered_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDateTime;
	
	@PrePersist
	public void prepersist() {

		setRegisteredDateTime(new Date(new Date().getTime()));
	}
}
