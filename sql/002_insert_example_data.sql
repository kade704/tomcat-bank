ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

-- users 샘플 데이터
INSERT INTO users (id, password_hash, password_salt, full_name, email, phone_number, age) -- 비밀번호: guest1234
        VALUES ('guest', '9cbecd18e548e0ba50444c8dbf48ebb751f15172e86c0f9c2c42a0b567142888', '86E0EC341B2F594182925ECE5FDDC36F', '게스트', 'guest@example.com', '010-1234-5678', 30);
INSERT INTO users (id, password_hash, password_salt, full_name, email, phone_number, age) -- 비밀번호: user1234
        VALUES ('user', '6736996155337c09cc42109f9803135ebfd7d35ae0d0acbfaba365866668558c', 'D194F017FAE53D61DDCABAE0905A2C3A', '유저', 'user@example.com', '010-9876-5432', 25);


-- accounts 샘플 데이터
INSERT INTO accounts (id, user_id, balance) VALUES ('999-111-000001', 'guest', 1000);
INSERT INTO accounts (id, user_id, balance) VALUES ('999-111-000002', 'guest', 200);
INSERT INTO accounts (id, user_id, balance) VALUES ('999-111-000003', 'user', 500);
INSERT INTO accounts (id, user_id, balance) VALUES ('999-111-000004', 'user', 1500);


-- transactions 샘플 데이터
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000001', NULL, NULL, 'DEPOSIT', 1300, 1300);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000002', NULL, NULL, 'DEPOSIT', 300, 300);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000001', '999-111-000002', '00000001', 'TRANSFER_OUT', -300, 1000);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000002', '999-111-000001', '00000001', 'TRANSFER_IN', 300, 600);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000002', NULL, NULL, 'WITHDRAW', -100, 200);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000003', NULL, NULL, 'DEPOSIT', 500, 500);
INSERT INTO transactions (account_id, account_id_transfer, group_id_transfer, type, amount, balance_after)
        VALUES ('999-111-000004', NULL, NULL, 'DEPOSIT', 1500, 1500);


-- 시퀸스 리셋
DROP SEQUENCE account_id_seq;
DROP SEQUENCE transaction_group_id_seq;

CREATE SEQUENCE account_id_seq START WITH 5 INCREMENT BY 1;
CREATE SEQUENCE transaction_group_id_seq START WITH 2 INCREMENT BY 1;
