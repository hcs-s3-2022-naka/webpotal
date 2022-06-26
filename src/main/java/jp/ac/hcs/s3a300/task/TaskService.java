package jp.ac.hcs.s3a300.task;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスク情報を操作する.
 */
@Transactional
@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	/**
	 * 指定したユーザIDのタスク情報を全件取得する.
	 * @param userId ユーザID
	 * @return TaskEntity
	 * @throws DataAccessException
	 */
	public TaskEntity selectAll(String userId) throws DataAccessException {
		return taskRepository.selectAll(userId);
	}

	/**
	 * 指定したユーザIDのタスク情報を前3件取得する.
	 * @param userId ユーザID
	 * @return TaskEntity(3件)
	 * @throws DataAccessException
	 */
	public TaskEntity selectThree(String userId) throws DataAccessException {
		return taskRepository.selectThree(userId);
	}

	/**
	 * タスク情報を1件追加する.
	 * @param taskdata 追加するタスク情報
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean insertOne(TaskData taskdata) throws DataAccessException {
		int rowNumber = taskRepository.insertOne(taskdata);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * タスク情報を1件削除する.
	 * @param id タスクID
	 * @return 処理結果(成功:true, 失敗:false)
	 * @throws DataAccessException
	 */
	public boolean deleteOne(int id) throws DataAccessException {
		int rowNumber = taskRepository.deleteOne(id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * タスク情報をCSVファイルとしてサーバに保存する.
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void taskListCsvOut(String user_id) throws DataAccessException {
		taskRepository.tasklistCsvOut(user_id);
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}

}
