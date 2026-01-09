create database shop_db;
select current_database();
SELECT current_schema();

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10,2) NOT NULL
);

CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id INT REFERENCES customers(id),
                        order_date DATE NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL
);

CREATE OR REPLACE PROCEDURE add_product(p_name VARCHAR, p_price DOUBLE PRECISION)
    LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM products WHERE name = p_name) THEN
        RAISE EXCEPTION 'Product name % already exists', p_name;
    ELSE
        INSERT INTO products(name, price) VALUES (p_name, p_price);
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE update_customer(p_id INT, p_name VARCHAR, p_email VARCHAR)
    LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM customers WHERE id = p_id) THEN
        UPDATE customers SET name = p_name, email = p_email WHERE id = p_id;
    ELSE
        RAISE EXCEPTION 'Customer ID % does not exist', p_id;
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE create_order(p_customer_id INT, p_total DOUBLE PRECISION)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO orders(customer_id, order_date, total_amount)
    VALUES (p_customer_id, CURRENT_DATE, p_total);
END;
$$;

CREATE OR REPLACE PROCEDURE list_all_orders(ref OUT REFCURSOR)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref FOR
        SELECT o.id AS order_id, c.name AS customer_name, o.order_date, o.total_amount
        FROM orders o
                 JOIN customers c ON o.customer_id = c.id
        ORDER BY o.id;
END;
$$;

CREATE OR REPLACE PROCEDURE get_orders_by_customer(p_customer_id INT, ref OUT REFCURSOR)
    LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref FOR
        SELECT id AS order_id, order_date, total_amount
        FROM orders
        WHERE customer_id = p_customer_id
        ORDER BY id;
END;
$$;

Insert into customers(name,email) values('Duong Van A','A@gmail'),('Nguyen Thi B','B@gmail');