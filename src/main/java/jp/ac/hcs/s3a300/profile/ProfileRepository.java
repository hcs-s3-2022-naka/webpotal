package jp.ac.hcs.s3a300.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM profile_m WHERE  user_id = :userId";
	
	/** SQL 1件更新 全ユーザ プロフィール変更 */
	private static final String SQL_UPDATE_PROFILE_ONE = "UPDATE profile_m SET qualification = :qualification, nickname = :nickname, self_comment = :comment WHERE user_id = :userId";
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private ProfileEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		ProfileEntity entity = new ProfileEntity();

		for (Map<String, Object> map : resultList) {
			ProfileData data = new ProfileData();
			data.setUser_id((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setQualification((String) map.get("qualification"));
			data.setNickname((String) map.get("nickname"));
			data.setComment((String) map.get("self_comment"));

			entity.getProfileList().add(data);
		}
		return entity;
	}
	
	/**
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public ProfileData selectOne(String user_id) throws DataAccessException {
		Map<String,Object> params = new HashMap<>();
		params.put("userId", user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, params);
		ProfileEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		ProfileData data = entity.getProfileList().get(0);
		return data;
	}
	
	public int  updateProfile(ProfileData data) throws DataAccessException {
		int rowNumber = -1;
		Map<String,Object> params = new HashMap<>();
		params.put("qualification", data.getQualification());
		params.put("nuckname", data.getNickname());
		params.put("comment", data.getComment());
		params.put("userId", data.getComment());
		rowNumber = jdbc.update(SQL_UPDATE_PROFILE_ONE, params);
		return rowNumber;
	}

}
