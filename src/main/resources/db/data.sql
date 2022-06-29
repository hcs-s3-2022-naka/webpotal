/* 開発用にデータ削除を追加 : リリース時は消す */
DELETE FROM m_user;
DELETE FROM task;

/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'true', 'ROLE_ADMIN');
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村健司', 'true', 'ROLE_GENERAL');
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, role)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '鈴木ウド', 'false', 'ROLE_GENERAL');

/* プロフィールマスタのデータ（ADMIN権限) */
INSERT INTO profile_m (user_id, user_name, qualification, nickname, self_comment)
VALUES('yamada@xxx.co.jp', '情報太郎', '基本情報処理技術者/応用情報処理技術者/情報処理安全確保支援士', 'たろーせんせー', '担任です。よろしくお願いします。');
/* プロフィールマスタのデータ（上位権限） */
INSERT INTO profile_m (user_id, user_name, qualification, nickname, self_comment)
VALUES('hanako@xxx.co.jp', '情報花子', 'Python3 基礎認定', '花子さん', 'こんにちは。よろしくおねがいします。');
/* プロフィールマスタのデータ（一般権限） */
INSERT INTO profile_m (user_id, user_name, qualification, nickname, self_comment)
VALUES('goro@xxx.co.jp', '情報五郎', 'まだ取得していません。', 'ごろーちゃん', '基本情報取得に目指して頑張ります！！');

/* タスクテーブルのデータ */
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (1, 'user', 'HIGH','a', 'これやる', '2020-03-23');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (2, 'master', 'MIDDLE','b', 'あれやる', '2020-03-24');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (3, 'tester', 'LOW','c', 'それやる', '2020-03-31');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (4, 'owner', 'LOW','d', 'どれやる', '2020-03-25');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (5, 'checker', 'LOW','e', 'もっとやる', '2020-04-20');