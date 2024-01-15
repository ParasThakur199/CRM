CREATE TABLE login_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    image_path VARCHAR(255),
    role VARCHAR(255) NOT NULL
);
CREATE TABLE lead_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    index_number BIGINT UNIQUE NOT NULL
);

CREATE TABLE technology (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE lead_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lead_name VARCHAR(255) NOT NULL,
    description TEXT,
    link VARCHAR(255),
    reminder_date DATE,
    reminder_topic VARCHAR(255),

    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES login_user(id),

    status_id BIGINT,
    FOREIGN KEY (status_id) REFERENCES status(id),

    lead_type_id BIGINT,
    FOREIGN KEY (lead_type_id) REFERENCES lead_type(id)
);



CREATE TABLE rejection(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    reason TEXT,
    lead_id BIGINT,
    FOREIGN KEY (lead_id) REFERENCES lead_info(id)
);


CREATE TABLE rejection_technologies (
    rejection_id BIGINT,
    technology_id BIGINT,
    PRIMARY KEY (rejection_id, technology_id),
    FOREIGN KEY (rejection_id) REFERENCES rejection(id),
    FOREIGN KEY (technology_id) REFERENCES technology(id)
);

CREATE TABLE lead_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    previous_status VARCHAR(255),
    updated_status VARCHAR(255),
    date_time DATETIME,
    user_id BIGINT,
    lead_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES login_user(id),
    FOREIGN KEY (lead_id) REFERENCES lead_info(id)
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text_field TEXT,
    lead_id BIGINT,
    FOREIGN KEY (lead_id) REFERENCES lead_info(id)
);





