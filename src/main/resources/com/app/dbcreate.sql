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
    id               INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    first_name       TEXT           NOT NULL,
    last_name        TEXT           NOT NULL,
    email            TEXT,
    address_address  TEXT,
    address_suburb   TEXT,
    address_state    TEXT,
    address_postcode TEXT,
    telephone        TEXT,
    balance          REAL,
    subject            TEXT,
    type             TEXT,
    member_status    INTEGER                                           DEFAULT 1,
    -- 0 (false) a customer IS NOT a member
    -- 1 (true) a customer IS a member
    CHECK (member_status IN (0, 1))
);

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE
(
    id           INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    first_name   TEXT           NOT NULL,
    last_name    TEXT           NOT NULL,
    display_name TEXT UNIQUE    NOT NULL,
    password     TEXT,
    email        TEXT,
    store        INTEGER        NOT NULL REFERENCES STORE (id),
    permissions  BLOB,
    position     TEXT
);

DROP TABLE IF EXISTS STORE;
CREATE TABLE STORE
(
    id               INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    name             TEXT           NOT NULL,
    address_address  TEXT,
    address_suburb   TEXT,
    address_state    TEXT,
    address_postcode TEXT,
    manager          INTEGER REFERENCES EMPLOYEE (id)
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
    id          INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT DEFAULT 0,
    name        TEXT    NOT NULL,
    type        TEXT    NOT NULL,
    price       REAL    NOT NULL,
    subject     TEXT    NOT NULL,
    description TEXT    NOT NULL,
    stocked     DATETIME                                          DEFAULT (datetime(CURRENT_TIMESTAMP, 'localtime'))
);

DROP TABLE IF EXISTS MODEL_STORE;
CREATE TABLE MODEL_STORE
(
    model_id INTEGER REFERENCES MODEL (id),
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
    address_address  TEXT,
    address_suburb   TEXT,
    address_state    TEXT,
    address_postcode TEXT,
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
    model_id    INTEGER REFERENCES MODEL (id),
    supplier_id INTEGER REFERENCES SUPPLIER (id),
    price       REAL NOT NULL,

    CONSTRAINT PK_modelSupplier PRIMARY KEY (model_id, supplier_id)
);

DROP TRIGGER IF EXISTS update_store_manager;
CREATE TRIGGER update_store_manager
    AFTER UPDATE
    ON STORE
BEGIN
    UPDATE EMPLOYEE SET store = NEW.id WHERE id = NEW.manager AND NEW.manager != 0;
END;

DROP TRIGGER IF EXISTS insert_store_manager;
CREATE TRIGGER insert_store_manager
    AFTER INSERT
    ON STORE
BEGIN
    UPDATE EMPLOYEE SET store = NEW.id WHERE id = NEW.manager AND NEW.manager != 0;
END;

-- Prepare test data

INSERT INTO ADMIN (first_name, last_name, display_name, password, email)
VALUES ('Tim', 'Toys', 'tim', '1234', 'tim@toyshop.co');

INSERT INTO CUSTOMER (first_name, last_name, email, address_address, address_suburb, address_state, address_postcode,
                      telephone, balance, subject, type)
VALUES ('John', 'Doe', 'customer@toyshop.co', '123 Customer St',
        'Customer Suburb', 'NSW', '3241', '0912 123, 482', 0, '', '');

INSERT INTO STORE (name, address_address, address_suburb, address_state, address_postcode, manager)
VALUES ('Tim Hobby Shop', '123 Store St', 'Store Suburb', 'NSW', '2345', NULL);

INSERT INTO EMPLOYEE (first_name, last_name, display_name, password, email, store, permissions, position)
VALUES ('First', 'Last', 'employee', 'test', 'employee@toyshop.co', 1, NULL, 'Manager');

INSERT INTO EMPLOYEE (first_name, last_name, display_name, password, email, store, permissions, position)
VALUES ('Jane', 'Doe', 'employee2', 'test2', 'employee2@toyshop.co', 1, NULL, 'Sales Assistant');

UPDATE STORE
SET manager = 1
WHERE STORE.id = 1;

INSERT INTO MODEL (name, type, price, subject, description)
VALUES ('Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (name, type, price, subject, description)
VALUES ('Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (name, type, price, subject, description)
VALUES ('Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (name, type, price, subject, description)
VALUES ('Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL (name, type, price, subject, description)
VALUES ('Model', 'Static', 19.95, 'Trains', 'This is a very long description of an item');

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES (1, 1, 'Model Trains Row 1', 2);

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES (3, 1, 'Model Trains Row 1', 9);

INSERT INTO MODEL_STORE (model_id, store_id, location, quantity)
VALUES (5, 1, 'Model Trains Row 1', 20);

INSERT INTO SUPPLIER (name, address_address, address_suburb, address_state, address_postcode, delivery_details)
VALUES ('Supplier', '123 Supplier St', 'Supplier Suburb', 'NSW', '0397', 'Delivery Details');

INSERT INTO SUPPLIER_CONTACT (supplier, name, phone)
VALUES (1, 'Mary', '00423857');

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES (1, 1, 15);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES (2, 1, 12);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES (3, 1, 8);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES (4, 1, 20);

INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)
VALUES (5, 1, 2);

INSERT INTO SALE (customer, employee, discount, final_price)
VALUES (1, 1, 0, 100);