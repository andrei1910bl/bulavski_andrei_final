USE socialnetwork_db;

INSERT INTO users (username, email, password, created_at) VALUES
('иван_иванов', 'ivan@example.com', 'password123', NOW()),
('анна_смит', 'anna@example.com', 'password456', NOW());

INSERT INTO role (role_name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(2, 2);

INSERT INTO chat (first_user_id, second_user_id) VALUES
(1, 2);

INSERT INTO message (content, sentAt, user_id, chat_id, group_id) VALUES
('Привет, как дела?', NOW(), 1, 1, NULL),
('Все хорошо, спасибо!', NOW(), 2, 1, NULL);

INSERT INTO post (user_id, content, created_at) VALUES
(1, 'Это мой первый пост!', NOW()),
(2, 'Добро пожаловать на мой профиль!', NOW());

INSERT INTO community (name, description, created_at, photo_url) VALUES
('Сообщество разработчиков', 'Сообщество для разработчиков для обмена знаниями.', NOW(), 'http://example.com/community_photo.jpg');

INSERT INTO member (community_id, user_id, joinedAt, position) VALUES
(1, 1, NOW(), 'Участник'),
(1, 2, NOW(), 'Участник');

INSERT INTO publication (content, createdAt, member_id) VALUES
('С нетерпением делюсь своим первым проектом!', NOW(), 1),
('Скиеьте видос по джаве.', NOW(), 2);

INSERT INTO friend (user_id, friend_id) VALUES
(1, 2);
INSERT INTO groups (name, description, created_at) VALUES
('MDK', 'MemsGroup.', NOW());

INSERT INTO group_user (user_id, group_id) VALUES
(1, 1),
(2, 1);

INSERT INTO profile (name, lastName, photoUrl, description, age, user_id) VALUES
('Иван', 'Иванов', 'http://example.com/ivan_photo.jpg', 'Блогер', 30, 1),
('Анна', 'Смит', 'http://example.com/anna_photo.jpg', 'Программист', 28, 2);