/* 開発用にデータ削除を追加 : リリース時は消す */
DELETE FROM m_user;
DELETE FROM task;

/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, qualification, nickname, comment, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'true', 'なにももってない。','山ちゃん', 'みんなで和気藹々とやっていきましょう', 'ROLE_ADMIN');
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, qualification, nickname, comment, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村健司', 'true', 'aaaaaasasasasa', 'たむけん', '獅子踊りは任せたまえ', 'ROLE_GENERAL');
INSERT INTO m_user (user_id, encrypted_password, user_name, enabled, qualification, nickname, comment, role)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '鈴木ウド', 'false', 'navi', 'ウドちゃん','キャイ～ン！', 'ROLE_GENERAL');

/* タスクテーブルのデータ */
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (1, 'user', 'HIGH','a', 'これやる', '2020-03-23');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (2, 'master', 'MIDDLE','b', 'あれやる', '2020-03-24');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (3, 'tester', 'LOW','c', 'それやる', '2020-03-31');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (4, 'owner', 'LOW','d', 'どれやる', '2020-03-25');
INSERT INTO task (id, user_id, priority, title, comment, limitday) VALUES (5, 'checker', 'LOW','e', 'もっとやる', '2020-04-20');