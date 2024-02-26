
CREATE TABLE IF NOT EXISTS tb_user(
  id SERIAL CONSTRAINT pk_id_user PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL UNIQUE,
  password TEXT NOT NULL,
  user_role VARCHAR(10)
);


/* CREATE TABLE IF NOT EXISTS tb_role(
  id INTEGER CONSTRAINT pk_id_role PRIMARY KEY,
  authority VARCHAR(40) NOT NULL
);


CREATE TABLE IF NOT EXISTS tb_user_role (
  id_user INTEGER,
  id_role INTEGER,  
   CONSTRAINT fk_user FOREIGN KEY(id_user) REFERENCES tb_user(id) ON DELETE CASCADE,
   CONSTRAINT fk_role FOREIGN KEY(id_role) REFERENCES tb_role(id) ON DELETE CASCADE
); */

