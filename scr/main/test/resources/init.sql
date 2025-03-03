CREATE TABLE Users (
    id INT,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    age INT
);

CREATE TABLE Sites (
    id INT,
    url VARCHAR(255),
    userId INT,
    categories VARCHAR(255),
    name VARCHAR(255)
)
