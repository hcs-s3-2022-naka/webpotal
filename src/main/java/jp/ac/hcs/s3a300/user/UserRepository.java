package jp.ac.hcs.s3a300.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * ユーザ情報のデータを管理する.
 * - Userテーブル
 */
@Repository
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = :userId";

	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, user_name, enabled, role) VALUES(:userId, :password, :username, :enabled, :role)";

	/** SQL 1件更新 管理者 パスワード更新有 */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = :password, user_name = :username, role = :role WHERE user_id = :userId";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_DETAIL_ONE = "UPDATE m_user SET user_name = :username, role = :role WHERE user_id = :userId";
	
	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM m_user WHERE user_id = :userId";
	
	private static final String SQL_INSERT_PROFILE = "INSERT INTO profile_m(user_id, user_name, qualification, nickname, self_comment) VALUES (:userId, :username, :qualification, :nickname, :comment)";

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private JdbcTemplate jdbc2;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Userテーブルから全データを取得.
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc2.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		UserEntity entity = new UserEntity();

		for (Map<String, Object> map : resultList) {
			UserData data = new UserData();
			data.setUser_id((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setEnabled((boolean) map.get("enabled"));
			data.setQualification((String) map.get("qualification"));
			data.setNickname((String) map.get("nickname"));
			data.setComment((String) map.get("comment"));
			data.setRole((String) map.get("role"));

			entity.getUserlist().add(data);
		}
		return entity;
	}

	/**
	 * Userテーブルにデータを1件追加する.
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(UserData data) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", data.getUser_id());
		params.put("password", passwordEncoder.encode(data.getPassword()));
		params.put("username", data.getUser_name());
		params.put("enabled", data.isEnabled());
		params.put("role", data.getRole());
		int rowNumber = jdbc.update(SQL_INSERT_ONE, params);
		execute(data.getUser_id(), data.getUser_name());
		return rowNumber;
	}
	
	private void execute(String user_id,String user_name) {
		Map <String,Object> params = new HashMap<>();
		params.put("userId", user_id);
		params.put("username", user_name);
		params.put("qualification", "まだ取得していません");
		params.put("nickname", "名無しさん");
		params.put("comment", "よろしくお願いします。");
		jdbc.update(SQL_INSERT_PROFILE,params);
	}
	/**
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException
	 */
	public UserData selectOne(String user_id) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, params);
		UserEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		UserData data = entity.getUserlist().get(0);
		return data;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新有).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOneWithPassword(UserData data) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", data.getUser_id());
		params.put("password", passwordEncoder.encode(data.getPassword()));
		params.put("username", data.getUser_name());
		params.put("role", data.getRole());
		int rowNumber = jdbc.update(SQL_UPDATE_ONE_WITH_PASSWORD, params);
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param data 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException
	 */
	public int updateOne(UserData userData) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userData.getUser_id());
		params.put("username", userData.getUser_name());
		params.put("role", userData.getRole());
		int rowNumber = jdbc.update(SQL_UPDATE_DETAIL_ONE,params);
		return rowNumber;
	}
	
	/**
	 * Userテーブルのデータを1件削除する.
	 * @param user_id 削除するユーザID
	 * @return 削除データ数
	 * @throws DataAccessException
	 */
	public int deleteOne(String user_id) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user_id);
		int rowNumber = jdbc.update(SQL_DELETE_ONE, params);
		return rowNumber;
	}

}
