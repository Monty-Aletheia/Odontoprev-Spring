ALTER TABLE tb_dentist
    ADD password VARCHAR2(255);

UPDATE tb_dentist
SET password = '1234'
WHERE password IS NULL;

ALTER TABLE tb_dentist
    MODIFY password VARCHAR2(255) NOT NULL;
