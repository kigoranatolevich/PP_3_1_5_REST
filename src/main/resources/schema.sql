CREATE TABLE IF NOT EXISTS users
(
    id         INT          NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(50)  NOT NULL,
    age        INT          NOT NULL,
    password   VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id   INT         NOT NULL AUTO_INCREMENT,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (role_id) references roles (id)
);