package jp.co.hands.hunting.entity.model.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class HuntingTimeLineId implements Serializable{
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="timeline_id")
	private String timeLineId;
	
	

}
