DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS productcategory;
DROP TABLE IF EXISTS orderlist;
DROP TABLE IF EXISTS shoppingcart;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
  id       INTEGER NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  username VARCHAR(40),
  password VARCHAR(40)
);


CREATE TABLE product
(
id INT PRIMARY KEY,
name varchar(40),
description varchar(40),
defaultPrice INTEGER,
defaultCurrency VARCHAR(40),
productCategory VARCHAR (40),
supplier VARCHAR (40)
);
CREATE TABLE supplier
(
id INT PRIMARY KEY,
name varchar(40),
description varchar(40),
product VARCHAR (80)
);
CREATE UNIQUE INDEX supplier_name_uindex ON public.supplier (name);

CREATE TABLE productcategory
(
  id          INTEGER NOT NULL
    CONSTRAINT productcategory_pkey
    PRIMARY KEY,
  name        VARCHAR(40),
  description VARCHAR(40),
  department  VARCHAR(40),
  products    VARCHAR(80)
);

CREATE UNIQUE INDEX productcategory_name_uindex
  ON productcategory (name);
CREATE TABLE orderlist
(
  id         INTEGER NOT NULL
    CONSTRAINT orderlist_pkey
    PRIMARY KEY,
  date       DATE,
  status     VARCHAR(40),
  totalprice INTEGER,
  products   VARCHAR(80),
  userid     INTEGER NOT NULL
);

CREATE TABLE shoppingcart
(
  userid  INTEGER NOT NULL,
  product VARCHAR(40),
  id      INTEGER NOT NULL
    CONSTRAINT shoppingcart_id_pk
    PRIMARY KEY
);

CREATE UNIQUE INDEX shoppingcart_id_uindex
  ON shoppingcart (id);

ALTER TABLE public.orderlist
  ADD CONSTRAINT orderlist_users_id_fk
FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE public.shoppingcart
  ADD CONSTRAINT shoppingcart_users_id_fk
FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE public.product
  ADD CONSTRAINT product_productcategory_name_fk
FOREIGN KEY (productcategory) REFERENCES productcategory (name);

ALTER TABLE public.product
  ADD CONSTRAINT product_supplier_name_fk
FOREIGN KEY (supplier) REFERENCES supplier (name);

CREATE SEQUENCE public.users_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq');
ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

CREATE SEQUENCE public.orderlist_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.orderlist ALTER COLUMN id SET DEFAULT nextval('public.orderlist_id_seq');
ALTER SEQUENCE public.orderlist_id_seq OWNED BY public.orderlist.id;

CREATE SEQUENCE public.shoppingcart_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.shoppingcart ALTER COLUMN id SET DEFAULT nextval('public.shoppingcart_id_seq');
ALTER SEQUENCE public.shoppingcart_id_seq OWNED BY public.shoppingcart.id;