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



CREATE TABLE articles (
                          id BIGSERIAL PRIMARY KEY,
                          title TEXT,
                          content TEXT,
                          url TEXT NOT NULL,
                          site_id BIGINT NOT NULL REFERENCES sites(id) ON DELETE CASCADE

);

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name TEXT NOT NULL,
                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT unique_user_category UNIQUE (user_id, name)
);

CREATE TABLE user_article_category (
                                       id BIGSERIAL PRIMARY KEY,
                                       user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                       CONSTRAINT unique_user_category_id UNIQUE (user_id, category_id),
                                       article_id BIGINT NOT NULL REFERENCES articles(id) ON DELETE CASCADE,
                                       category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE
);
