-- public.images определение

CREATE TABLE public.images (
	id bigserial NOT NULL,
	"data" bytea NULL,
	file_size int8 NOT NULL,
	media_type varchar(255) NULL,
	CONSTRAINT images_pkey PRIMARY KEY (id)
);

-- public.users определение

CREATE TABLE public.users (
	id bigserial NOT NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	"password" varchar(255) NOT NULL,
	phone varchar(255) NULL,
	"role" int2 NULL,
	username varchar(255) NULL,
	avatar_id int8 NULL,
	CONSTRAINT uk_r53o2ojjw4fikudfnsuuga336 UNIQUE (password),
	CONSTRAINT uk_rsulcn2gynjy3cddpwmosv881 UNIQUE (avatar_id),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_role_check CHECK (((role >= 0) AND (role <= 1)))
);

-- public.users внешние включи

ALTER TABLE public.users ADD CONSTRAINT fk2lttmx8vn9eecykig5sch3v0h FOREIGN KEY (avatar_id) REFERENCES public.images(id);

INSERT INTO public.users
(id, first_name, last_name, "password", phone, "role", username, avatar_id)
VALUES(8, 'firstName', 'lastName', '$2a$10$Ln5nb58W68r7juiXxdhmVOmIoKOvRU3G5rAUGF6ZBGkV7mlzpSP4O', '+7 (999) 765-43-21', 1, 'user@gmail.com', NULL);