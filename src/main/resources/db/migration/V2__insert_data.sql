INSERT INTO role (role_name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO users (username, email, phone_number, password, created_at) VALUES
('user1', 'andrei.bulavskiy@example.com', '375297777777', '$2a$10$h9iwlx6rMBPoM1bkBIXwVOF/TyPYeFUHvRX9PlQ9EgJovMYEG3rRm', CURRENT_TIMESTAMP),
('user2', 'ivan.ivanov@example.com', '375298888888', '$2a$10$h9iwlx6rMBPoM1bkBIXwVOF/TyPYeFUHvRX9PlQ9EgJovMYEG3rRm', CURRENT_TIMESTAMP);

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(2, 2);

INSERT INTO profile (name, last_name, photo_url, description, age, user_id) VALUES
('Andrei', 'Bulavskiy', 'http://example.com/photo_andrei.jpg', 'Description for Andrei', 28, 1),
('Ivan', 'Ivanov', 'http://example.com/photo_ivan.jpg', 'Description for Ivan', 30, 2);


INSERT INTO community (name, description, created_at, photo_url) VALUES
('Community 1', 'Description for community 1', CURRENT_TIMESTAMP, 'http://example.com/community1.jpg'),
('Community 2', 'Description for community 2', CURRENT_TIMESTAMP, 'http://example.com/community2.jpg');

INSERT INTO member (community_id, user_id, joined_at, position) VALUES
(1, 1, CURRENT_TIMESTAMP, 'Member'),
(1, 2, CURRENT_TIMESTAMP, 'Member');

INSERT INTO chat (first_user_id, second_user_id) VALUES
(1, 2);

INSERT INTO message (content, sent_at, user_id, chat_id) VALUES
('Hello Ivan!', CURRENT_TIMESTAMP, 1, 1),
('Hi Andrei!', CURRENT_TIMESTAMP, 2, 1);

INSERT INTO groups (name, created_at, photo_url) VALUES
('Group 1', CURRENT_TIMESTAMP, 'http://example.com/group1.jpg');

INSERT INTO group_user (user_id, group_id) VALUES
(1, 1),
(2, 1);

INSERT INTO post (user_id, content, created_at) VALUES
(1, 'This is my first post!', CURRENT_TIMESTAMP),
(2, 'Hello everyone!', CURRENT_TIMESTAMP);

INSERT INTO publication (content, created_at, member_id) VALUES
('Publication content by Andrei', CURRENT_TIMESTAMP, 1),
('Publication content by Ivan', CURRENT_TIMESTAMP, 2);