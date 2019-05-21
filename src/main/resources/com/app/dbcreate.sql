DROP TABLE IF EXISTS ADMIN;
CREATE TABLE ADMIN
(
    id           INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    first_name   TEXT           NOT NULL,
    last_name    TEXT           NOT NULL,
    display_name TEXT,
    password     TEXT,
    email        TEXT
);

DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER
(

    id            INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    first_name    TEXT           NOT NULL,
    last_name     TEXT           NOT NULL,
    display_name  TEXT,
    password      TEXT,
    email         TEXT,
    interests     TEXT,
    address       TEXT,
    telephone     TEXT,
    balance       REAL,
    member_status INTEGER                                           DEFAULT 0x01,
    -- 0x01 a customer IS NOT a member
    -- 0x02 a customer IS a member
    CHECK (member_status IN (0x01, 0x02))
);

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE
(
    id           INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    first_name   TEXT           NOT NULL,
    last_name    TEXT           NOT NULL,
    display_name TEXT,
    password     TEXT,
    email        TEXT,
    store        INTEGER        NOT NULL REFERENCES STORE (id),
    permissions  BLOB,
    position     TEXT
);

DROP TABLE IF EXISTS STORE;
CREATE TABLE STORE
(
    id      INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    name    TEXT           NOT NULL,
    address TEXT           NOT NULL,
    manager INTEGER REFERENCES EMPLOYEE (id)
);

DROP TABLE IF EXISTS SALE;
CREATE TABLE SALE
(
    customer    INTEGER NOT NULL REFERENCES CUSTOMER (id),
    employee    INTEGER NOT NULL REFERENCES EMPLOYEE (id),
    discount    REAL     DEFAULT 0,
    final_price REAL,
    date        DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP, 'localtime')),

    CONSTRAINT PK_sale PRIMARY KEY (date, customer, employee)
);

DROP TABLE IF EXISTS SALE_ITEM;
CREATE TABLE SALE_ITEM
(
    sale_date DATETIME NOT NULL REFERENCES SALE (date),
    customer  INTEGER  NOT NULL REFERENCES SALE (customer),
    employee  INTEGER  NOT NULL REFERENCES SALE (employee),
    model     TEXT     NOT NULL REFERENCES MODEL (id),
    quantity  INTEGER  NOT NULL DEFAULT 1,

    CONSTRAINT PK_saleItem PRIMARY KEY (sale_date, customer, employee, model)
);

DROP TABLE IF EXISTS MODEL;
CREATE TABLE MODEL
(
    id          TEXT NOT NULL UNIQUE PRIMARY KEY,
    name        TEXT NOT NULL,
    type        TEXT NOT NULL,
    price       REAL NOT NULL,
    subject     TEXT NOT NULL,
    description TEXT NOT NULL,
    stocked     DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP, 'localtime'))
);

DROP TABLE IF EXISTS MODEL_STORE;
CREATE TABLE MODEL_STORE
(
    model_id TEXT REFERENCES MODEL (id),
    store_id INTEGER REFERENCES STORE (id),
    location TEXT NOT NULL,
    quantity INTEGER,

    CONSTRAINT PK_modelStore PRIMARY KEY (model_id, store_id)
);

DROP TABLE IF EXISTS SUPPLIER;
CREATE TABLE SUPPLIER
(
    id               INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    name             TEXT    NOT NULL,
    address          TEXT    NOT NULL,
    delivery_details TEXT    NOT NULL
);

DROP TABLE IF EXISTS SUPPLIER_CONTACT;
CREATE TABLE SUPPLIER_CONTACT
(
    supplier INTEGER REFERENCES SUPPLIER (id),
    name     TEXT NOT NULL,
    email    TEXT,
    phone    TEXT,

    CONSTRAINT PK_supplierContact PRIMARY KEY (supplier, name, email, phone)
);

DROP TABLE IF EXISTS MODEL_SUPPLIER;
CREATE TABLE MODEL_SUPPLIER
(
    model_id    TEXT REFERENCES MODEL (id),
    supplier_id INTEGER REFERENCES SUPPLIER (id),
    price       REAL NOT NULL,

    CONSTRAINT PK_modelSupplier PRIMARY KEY (model_id, supplier_id)
);

-- Prepare test data

INSERT INTO ADMIN (first_name, last_name, display_name, password, email)
VALUES ('Tim', 'Toys', 'tim', '1234', 'tim@toyshop.co');

INSERT INTO STORE (name, address, manager)
VALUES ('A Store', '123 Store St, City, 1243 Australia', NULL);

INSERT INTO EMPLOYEE (first_name, last_name, display_name, password, email, store, permissions, position)
VALUES ('First', 'Last', 'employee', 'test', 'employee@toyshop.co', 1, NULL, 'Manager');

UPDATE STORE
SET manager = 1
WHERE STORE.id = 1;

INSERT INTO MODEL (id, name, type, price, subject, description)
VALUES ('Model-01', 'Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (id, name, type, price, subject, description)
VALUES ('Model-02', 'Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (id, name, type, price, subject, description)
VALUES ('Model-03', 'Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (id, name, type, price, subject, description)
VALUES ('Model-04', 'Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (id, name, type, price, subject, description)
VALUES ('Model-05', 'Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES ('Model-01', 1, 'Model Trains Row 1', 2);

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES ('Model-02', 1, 'Model Trains Row 1', 9);

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES ('Model-04', 1, 'Model Trains Row 1', 20);

INSERT INTO SUPPLIER (name, address, delivery_details)
VALUES ('Model Supplier 2', '123 Supplier St, Supplier City', 'Delivery Details');

INSERT INTO SUPPLIER_CONTACT (supplier, name, phone)
VALUES (1, 'Supplier', '00423857');

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES ('Model-01', 1, 15);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES ('Model-02', 1, 12);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES ('Model-03', 1, 8);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES ('Model-04', 1, 20);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES ('Model-05', 1, 2);