package jp.ac.hcs.s3a300.profile;

import lombok.Data;

@Data
public class ProfileData {
	
	/**
	 * ユーザID（メールアドレス）
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;
	
	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;
	
	/**
	 * 取得した資格
	 */
	private String qualification;
	
	/**
	 * 表記名、ニックネーム
	 * プロフィール画面にて変更
	 */
	private String nickname;
	
	/**
	 * 一言コメント
	 * プロフィール画面にて変更
	 * 
	 */
	private String comment;

}
