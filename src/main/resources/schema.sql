
CREATE TABLE User (
  id BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR (100) NOT NULL  ,
  email VARCHAR (255) NOT NULL UNIQUE ,
  sub VARCHAR(255),
  picture VARCHAR(2550) DEFAULT NULL

);
CREATE TABLE image(
                      image_id BIGINT AUTO_INCREMENT PRIMARY KEY ,
                      heading VARCHAR(100),
                      image_encode_string VARCHAR  NOT NULL,
                      user_id BIGINT NOT NULL ,
                      google_id BIGINT ,
                      CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES User(id)
);

