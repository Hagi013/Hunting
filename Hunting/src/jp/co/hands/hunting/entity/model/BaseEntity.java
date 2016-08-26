package jp.co.hands.hunting.entity.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable{

	private boolean deleteFlag;
		
}
