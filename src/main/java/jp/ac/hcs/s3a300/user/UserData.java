package jp.ac.hcs.s3a300.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分のユーザ情報.
 * 各項目のデータ仕様も合わせて管理する.
 */
@Data
@NoArgsConstructor
public class UserData {

	/**
	 * ユーザID（メールアドレス）
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;

	/**
	 * パスワード
	 * 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String password;

	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;

	/**
	 * ダークモードフラグ
	 * - ON  : true
	 * - OFF : false
	 * ユーザ作成時はfalse固定
	 */
	private boolean darkmode;
	
	/**
	 * アカウント有効性
	 * -有効：true
	 * -無効：false
	 */
	private boolean enabled;
	
	/**
	 * 権限
	 * - 管理 : "ROLE_ADMIN"
	 * - 上位："ROLE_TOP"
	 * - 一般 : "ROLE_GENERAL"
	 * 必須入力
	 */
	private String role;
	
	//以降、プロフィール画面用のData
	
	/**
	 * 取得資格
	 * プロフィール画面にて変更
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

enum Role {
	ROLE_ADMIN(1,"管理者"),ROLE_TOP(2,"上位"),ROLE_GENERAL(3,"一般");
	
	private int id;
	private String value;
	
	Role(int id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static Role idOf(int id) {
		for(Role role : values()) {
			if(role.getId() == id) {
				return role;
			}
		}
		throw new IllegalArgumentException("指定されたIDのRoleが存在しません");
	}
}
