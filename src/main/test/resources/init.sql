CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       password TEXT NOT NULL
);

CREATE TABLE sites (
                       id BIGSERIAL PRIMARY KEY,
                       url TEXT NOT NULL,
                       user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       CONSTRAINT unique_user_url UNIQUE (user_id, url)
);
