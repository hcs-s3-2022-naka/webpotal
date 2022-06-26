package jp.ac.hcs.s3a300.task;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分のタスク情報です。
 * 
 * <p>各データ構造については、データベース定義をご覧ください。
 * @author 情報太郎
 */
@Data
@NoArgsConstructor
public class TaskData {

	/**
	 * タスクID
	 * 主キー、SQLにて自動採番
	 */
	private int id;

	/**
	 * ユーザID（メールアドレス）
	 * Userテーブルの主キーと紐づく、ログイン情報から取得
	 */
	private String userId;

	/**
	 * 件名
	 * 必須入力
	 */
	private String title;
	
	/**
	 * 優先度
	 * 大、中、小の3種類
	 */
	private Priority priority;

	/**
	 * 期限日
	 * 必須入力
	 */
	private Date limitday;
	
	private String comment;
}
	enum Priority {
		HIGH(1,"大"),
		MIDDLE(2,"中"),
		LOW(3,"小");

		private int id;

		private String value;

		Priority(int id, String value) {
			this.id = id;
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public int getId() {
			return this.id;
		}

		public static Priority idOf(int id) {
			for (Priority priority : values()) {
				if (priority.getId() == id) {
					return priority;
				}
			}
			throw new IllegalArgumentException("指定されたIDのPriorityが存在しません");
		}
	}