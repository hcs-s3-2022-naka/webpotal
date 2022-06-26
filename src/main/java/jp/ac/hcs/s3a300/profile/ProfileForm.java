package jp.ac.hcs.s3a300.profile;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ProfileForm {
	
	private String user_id;
	
	private String user_name;
	
	private String qualification;
	
	@Length(max = 10, message = "{length_check}")
	private String nickname;
	
	@Length(max = 100, message = "{length_check}")
	private String comment;
}
