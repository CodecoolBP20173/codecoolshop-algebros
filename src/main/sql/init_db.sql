DROP TABLE IF EXISTS supplier CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS productcategory CASCADE;
DROP TABLE IF EXISTS orderlist CASCADE;
DROP TABLE IF EXISTS shoppingcart CASCADE;
DROP TABLE IF EXISTS users CASCADE;


CREATE TABLE users
(
  id       INTEGER NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  username VARCHAR(40),
  password VARCHAR(80),
  email VARCHAR(80),
  salt VARCHAR(120),
  fullname VARCHAR(20),
  phone VARCHAR(20),
  country VARCHAR(30),
  city VARCHAR(30),
  zip VARCHAR(10),
  address VARCHAR(80)
);


CREATE TABLE products
(
id INT PRIMARY KEY,
name VARCHAR(500),
description VARCHAR(500),
defaultPrice INTEGER,
defaultCurrency VARCHAR(500),
productCategory VARCHAR (500),
supplier VARCHAR (500)
);
CREATE UNIQUE INDEX products_name_uindex ON public.products (name);
CREATE TABLE supplier
(
id INT PRIMARY KEY,
name varchar(40),
description varchar(80),
product VARCHAR (80)
);
CREATE UNIQUE INDEX supplier_name_uindex ON public.supplier (name);

CREATE TABLE productcategory
(
  id          INTEGER NOT NULL
    CONSTRAINT productcategory_pkey
    PRIMARY KEY,
  name        VARCHAR(40),
  description VARCHAR(240),
  department  VARCHAR(80),
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
  status     VARCHAR(500),
  totalprice INTEGER,
  products   VARCHAR(5000),
  userid     INTEGER NOT NULL
);

CREATE TABLE shoppingcart
(
  userid  INTEGER NOT NULL,
  productid INTEGER NOT NULL,
  quantity      INTEGER NOT NULL
);

ALTER TABLE public.supplier ALTER COLUMN description SET NOT NULL;

ALTER TABLE public.orderlist
  ADD CONSTRAINT orderlist_users_id_fk
FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE;


ALTER TABLE public.products
  ADD CONSTRAINT product_productcategory_name_fk
FOREIGN KEY (productcategory) REFERENCES productcategory (name) ON DELETE CASCADE;

ALTER TABLE public.products
  ADD CONSTRAINT product_supplier_name_fk
FOREIGN KEY (supplier) REFERENCES supplier (name) ON DELETE CASCADE;

ALTER TABLE public.shoppingcart
  ADD CONSTRAINT shoppingcart_users_id_fk
FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE public.shoppingcart
  ADD CONSTRAINT shoppingcart_products_id_fk
FOREIGN KEY (productid) REFERENCES products (id) ON DELETE CASCADE;

CREATE SEQUENCE public.supplier_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.supplier ALTER COLUMN id SET DEFAULT nextval('public.supplier_id_seq');
ALTER SEQUENCE public.supplier_id_seq OWNED BY public.supplier.id;

CREATE SEQUENCE public.users_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq');
ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

CREATE SEQUENCE public.orderlist_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.orderlist ALTER COLUMN id SET DEFAULT nextval('public.orderlist_id_seq');
ALTER SEQUENCE public.orderlist_id_seq OWNED BY public.orderlist.id;


CREATE SEQUENCE public.productcategory_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.productcategory ALTER COLUMN id SET DEFAULT nextval('public.productcategory_id_seq');
ALTER SEQUENCE public.productcategory_id_seq OWNED BY public.productcategory.id;

CREATE SEQUENCE public.products_id_seq NO MINVALUE NO MAXVALUE NO CYCLE;
ALTER TABLE public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq');
ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;

ALTER TABLE public.orderlist ALTER COLUMN date SET DEFAULT current_timestamp;