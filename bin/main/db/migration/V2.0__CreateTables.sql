CREATE TABLE public."command" (
    id serial NOT NULL PRIMARY KEY,
    channel_name VARCHAR NOT NULL,
    command_name VARCHAR NOT NULL,
    command_body VARCHAR NOT NULL,
    command_added DATE NOT NULL,
    command_added_by VARCHAR NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public."command" TO ${command_user};

GRANT SELECT, UPDATE, USAGE ON SEQUENCE public.command_id_seq TO ${command_user};

CREATE TABLE public."history" (
    id serial NOT NULL PRIMARY KEY,
    command_id int NOT NULL,
    channel_name VARCHAR NOT NULL,
    connected_from DATE NOT NULL,
    connected_to DATE NOT NULL,
    command_added_by VARCHAR NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public."history" TO ${command_user};

GRANT SELECT, UPDATE, USAGE ON SEQUENCE public.history_id_seq TO ${command_user};