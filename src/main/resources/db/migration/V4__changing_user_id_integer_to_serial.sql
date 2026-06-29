-- 1. Create the sequence.
create SEQUENCE if not exists auth.users_user_id_seq; -- SEQUENCE expects: schema.sequence_name

-- 2. Tell the column to use that sequence by default.
ALTER TABLE auth.users
ALTER COLUMN user_id
SET DEFAULT nextval('auth.users_user_id_seq');

-- 3. Tell PostgreSQL that the sequence belongs to this column.
ALTER SEQUENCE auth.users_user_id_seq
OWNED BY auth.users.user_id; -- OWNED BY expects: schema.table.column