CREATE TABLE questions (
  questionId BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  questionText TEXT NOT NULL
);

CREATE TABLE possible_answers (
  answerId BIGINT AUTO_INCREMENT PRIMARY KEY,
  answerText TEXT NOT NULL,
  correct BIT NOT NULL,
  questionId BIGINT NOT NULL,
  CONSTRAINT fk_question
    FOREIGN KEY (questionId)
    REFERENCES questions(questionId)
);

CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(50) DEFAULT NULL,
  password varchar(120) DEFAULT NULL,
  username varchar(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (username),
  UNIQUE KEY (email)
);

CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT,
  name enum('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER') DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
