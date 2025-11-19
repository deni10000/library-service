CREATE TABLE book
(
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    author              VARCHAR(255) NOT NULL,
    status              VARCHAR(20)  NOT NULL DEFAULT 'AVAILABLE',
    borrowed_by_user_id BIGINT
);

CREATE INDEX idx_book_status ON book (status);
CREATE INDEX idx_book_borrowed_by_user_id ON book (borrowed_by_user_id);
CREATE INDEX idx_book_title_author ON book (title, author);