package jp.co.hands.hunting.entity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	@Column(name="delete_flag")
	private boolean deleteFlag;
		
}
