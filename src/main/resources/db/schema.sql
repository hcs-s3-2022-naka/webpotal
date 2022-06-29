/* 開発用にデータ削除を追加 : リリース時は消す
DROP TABLE m_user;
DROP TABLE task;
 */

/* ユーザマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(50) PRIMARY KEY,
    encrypted_password VARCHAR(100),
    user_name VARCHAR(50),
    enabled boolean,
    role VARCHAR(50)
);

/* プロフィールマスタ */
CREATE TABLE IF NOT EXISTS profile_m (
	user_id VARCHAR(50) PRIMARY KEY,
	user_name VARCHAR(50),
	qualification VARCHAR(255),
	nickname VARCHAR(50),
	self_comment VARCHAR(100)
); 

/* タスクテーブル */
CREATE TABLE IF NOT EXISTS task (
  id INT PRIMARY KEY,
  user_id VARCHAR(50),
  priority VARCHAR(10),
  title VARCHAR(50),
  comment VARCHAR(200),
  limitday DATE
);
