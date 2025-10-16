CREATE TABLE IF NOT EXISTS BOOKS (
                                     ID IDENTITY PRIMARY KEY,
                                     TITLE VARCHAR(255) NOT NULL,
    AUTHOR VARCHAR(255) NOT NULL,
    YEAR_PUBLISHED INT NOT NULL,
    GENRE VARCHAR(100) NOT NULL,
    SYNOPSIS CLOB
    );

INSERT INTO BOOKS (TITLE, AUTHOR, YEAR_PUBLISHED, GENRE, SYNOPSIS)
SELECT 'Clean Code', 'Robert C. Martin', 2008, 'Programação', 'Práticas para escrever código limpo.'
    WHERE NOT EXISTS (SELECT 1 FROM BOOKS WHERE TITLE='Clean Code');