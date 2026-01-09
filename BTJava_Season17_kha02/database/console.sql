CREATE DATABASE todo_db;

CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       task_name VARCHAR(255) NOT NULL,
                       status VARCHAR(50) NOT NULL
);

CREATE OR REPLACE FUNCTION add_task(
    p_task_name VARCHAR,
    p_status VARCHAR
)
    RETURNS VOID AS $$
BEGIN
    INSERT INTO tasks(task_name, status)
    VALUES (p_task_name, p_status);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION list_tasks()
    RETURNS TABLE (
                      id INT,
                      task_name VARCHAR,
                      status VARCHAR
                  )
AS $$
BEGIN
    RETURN QUERY
        SELECT t.id, t.task_name, t.status
        FROM tasks t;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION update_task_status(
    p_id INT,
    p_status VARCHAR
)
    RETURNS VOID AS $$
BEGIN
    UPDATE tasks
    SET status = p_status
    WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_task(
    p_id INT
)
    RETURNS VOID AS $$
BEGIN
    DELETE FROM tasks WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION search_task_by_name(
    p_name VARCHAR
)
    RETURNS TABLE (
                      id INT,
                      task_name VARCHAR,
                      status VARCHAR
                  )
AS $$
BEGIN
    RETURN QUERY
        SELECT t.id, t.task_name, t.status
        FROM tasks t
        WHERE t.task_name ILIKE '%' || p_name || '%';
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION task_statistics()
    RETURNS TABLE (
                      status VARCHAR,
                      total INT
                  )
AS $$
BEGIN
    RETURN QUERY
        SELECT t.status, COUNT(*)::INT
        FROM tasks t
        GROUP BY t.status;
END;
$$ LANGUAGE plpgsql;





