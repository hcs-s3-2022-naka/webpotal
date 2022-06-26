package jp.ac.hcs.s3a300.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * タスク情報のデータを管理する.
 * - Taskテーブル
 */
@Repository
public class TaskRepository {

	/** SQL 全件取得（期限日昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM task WHERE user_id = ? order by limitday";

	/** SQL 3件取得（期限日昇順） */
	private static final String SQL_SELECT_THREE = "SELECT * FROM task WHERE user_id = ? order by limitday limit 3";

	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO task(id, user_id, priority, title, comment, limitday) VALUES((SELECT MAX(id) + 1 FROM task), ?, ?, ?, ?, ?)";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM task WHERE id = ?";

	@Autowired
	JdbcTemplate jdbc;

	/**
	 * TaskテーブルからユーザIDをキーに全データを取得.
	 * @param user_id 検索するユーザID
	 * @return TaskEntity
	 * @throws DataAccessException
	 */
	public TaskEntity selectAll(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL, user_id);
		TaskEntity taskEntity = mappingSelectResult(resultList);
		return taskEntity;
	}

	/**
	 * TaskテーブルからユーザIDをキーにデータを前3件取得.
	 * @param user_id 検索するユーザID
	 * @return TaskEntity
	 * @throws DataAccessException
	 */
	public TaskEntity selectThree(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_THREE, user_id);
		TaskEntity taskEntity = mappingSelectResult(resultList);
		return taskEntity;
	}

	/**
	 * Taskテーブルから取得したデータをTaskEntity形式にマッピングする.
	 * @param resultList Taskテーブルから取得したデータ
	 * @return TaskEntity
	 */
	private TaskEntity mappingSelectResult(List<Map<String, Object>> resultList) throws DataAccessException {
		TaskEntity entity = new TaskEntity();

		for (Map<String, Object> map : resultList) {
			TaskData data = new TaskData();
			data.setId((Integer) map.get("id"));
			data.setUserId((String) map.get("user_id"));
			data.setPriority(Priority.valueOf((String)map.get("priority")));
			data.setTitle((String) map.get("title"));
			data.setComment((String) map.get("comment"));
			data.setLimitday((Date) map.get("limitday"));

			entity.getTasklist().add(data);
		}
		return entity;
	}

	/**
	 * Taskテーブルにデータを1件追加する.
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException
	 */
	public int insertOne(TaskData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
				data.getUserId(),
				data.getPriority().toString(),
				data.getTitle(),
				data.getComment(),
				data.getLimitday());
		return rowNumber;
	}

	/**
	 * Taskテーブルのデータを1件削除する.
	 * @param id 削除するタスクID
	 * @return 削除データ数
	 * @throws DataAccessException
	 */
	public int deleteOne(int id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, id);
		return rowNumber;
	}

	/**
	 * TaskテーブルからユーザIDをキーにデータを全件取得し、CSVファイルとしてサーバに保存する.
	 * @param user_id 検索するユーザID
	 * @throws DataAccessException
	 */
	public void tasklistCsvOut(String user_id) throws DataAccessException {

		// CSVファイル出力用設定
		TaskRowCallbackHandler handler = new TaskRowCallbackHandler();

		jdbc.query(SQL_SELECT_ALL, handler, user_id);
	}

}
