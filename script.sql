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

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
