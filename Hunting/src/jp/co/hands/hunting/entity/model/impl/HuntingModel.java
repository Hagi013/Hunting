package jp.co.hands.hunting.entity.model.impl;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jp.co.hands.hunting.entity.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="HuntingModel")
@Table(name="hunting_model")
public class HuntingModel extends BaseEntity{
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="user_name")
	private String userName;

	@Column(name="user_first_name")
	private String userFirstName;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="profile_picture")
	private byte[] profilePicture;

	@Column(name="user_last_names")
	private String userLastName;
	
	@Column(name="display_name")
	private String displayName;
	
	@OneToMany(targetEntity=HuntingTimeLine.class, mappedBy="huntingModel", cascade=CascadeType.ALL)
	@JoinColumn(name="timeline_id")
	private List<HuntingTimeLine> huntingTimeLines;

		
}
