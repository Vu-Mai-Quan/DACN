ALTER TABLE variant_option_product
    DROP FOREIGN KEY FK7ldy51npmmk680f480qjk1tfy;

ALTER TABLE variant_option_product
    DROP FOREIGN KEY FKigg92xeileqruyty1xlvqtfru;

ALTER TABLE tai_khoan
    ADD id_manager BINARY(16) DEFAULT null NULL;

ALTER TABLE tai_khoan
    ADD CONSTRAINT FK_TAI_KHOAN_ON_ID_MANAGER FOREIGN KEY (id_manager) REFERENCES tai_khoan (id);

DROP TABLE variant_option_product;

ALTER TABLE tai_khoan
    DROP COLUMN type;

ALTER TABLE tai_khoan
    ADD type VARCHAR(8) NOT NULL;