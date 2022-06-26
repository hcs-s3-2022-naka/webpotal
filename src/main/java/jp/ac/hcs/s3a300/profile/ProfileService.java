package jp.ac.hcs.s3a300.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
	
	@Autowired
	ProfileRepository profileRepository;
	
	/**
	 * 指定したユーザIDのユーザ情報を取得する.
	 * @param user_id ユーザID
	 * @return UserData
	 */
	public ProfileData selectOne(String user_id) {
		return profileRepository.selectOne(user_id);
	}
	
	public boolean updateProfile(ProfileData data) {
		int rowNumber = profileRepository.updateProfile(data);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}
}
