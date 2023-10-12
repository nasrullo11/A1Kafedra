 -- Table: public.is_users

DROP TABLE public.is_users;

CREATE TABLE public.is_users
(
    id bigint NOT NULL DEFAULT nextval('is_users_id_seq'::regclass),
    full_name character varying(255) COLLATE pg_catalog."default",
    is_active boolean DEFAULT true,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default",
    token character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT is_users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_35r5bammqehhs5wleloxpee5t UNIQUE (username)
)

TABLESPACE pg_default;

ALTER TABLE public.is_users
    OWNER to postgres;