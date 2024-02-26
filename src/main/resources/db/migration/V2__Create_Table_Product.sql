
CREATE TABLE IF NOT EXISTS tb_product(
  id SERIAL CONSTRAINT pk_id_product PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(250) NOT NULL,
  price NUMERIC(10,2)
);

INSERT INTO tb_product (name, price) VALUES ('Camiseta VivLeroa', 30.75);
INSERT INTO tb_product (name, price) VALUES ('Calça', 80.0);
INSERT INTO tb_product (name, price) VALUES ('Tênis', 230.50);
INSERT INTO tb_product (name, price) VALUES ('Sapato', 95.90);

