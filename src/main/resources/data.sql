CREATE TABLE status
(
  status_id bigserial NOT NULL,
  name character varying(255) NOT NULL,
  created_date timestamp without time zone NULL,
  created_by character varying(255) NULL,
  modified_date timestamp without time zone NULL,
  modified_by character varying(255) NULL,
  CONSTRAINT "STATUS_pkey" PRIMARY KEY (status_id)
);
CREATE TABLE users
(
    user_id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    status_id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    dob character varying(255) NOT NULL,
    created_date timestamp without time zone NULL,
    created_by character varying(255) NULL,
    modified_date timestamp without time zone NULL,
    modified_by character varying(255) NULL,
    CONSTRAINT "USERS_pkey" PRIMARY KEY (user_id),
    CONSTRAINT "fk_USERS_STATUS_ID" FOREIGN KEY (status_id)
        REFERENCES status (status_id)
);

CREATE TABLE user_document
(
    user_document_id uuid NOT NULL,
    user_id bigserial NOT NULL,
    document_name character varying(255) NOT NULL,
    document_location character varying(500) NOT NULL,
    status_id bigint NOT NULL,
    created_date timestamp without time zone NULL,
    created_by character varying(255) NULL,
    modified_date timestamp without time zone NULL,
    modified_by character varying(255) NULL,
    CONSTRAINT "USER_DOCUMENT_pkey" PRIMARY KEY (user_document_id),
    CONSTRAINT "fk_USERS_DOCUMENT_STATUS_ID" FOREIGN KEY (status_id)
        REFERENCES status (status_id),
    CONSTRAINT "fk_USERS_DOCUMENT_USER_ID" FOREIGN KEY (user_id)
        REFERENCES users (user_id)
);

INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (1, 'ACTIVE', now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (2, 'IN_ACTIVE', now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (3, 'DELETED', now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (4, 'PENDING', now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (5, 'SUCCESS', now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO status (status_id, name, created_date, created_by, modified_date, modified_by) VALUES (6, 'FAILURE', now(), 'SYSTEM', now(), 'SYSTEM');


INSERT INTO users (user_id, name, first_name,last_name, created_date, created_by, modified_date, modified_by,status_id,dob) VALUES (1, 'Kumar K','Kumar','K', now(), 'SYSTEM', now(), 'SYSTEM',1,'1990-02-01');
INSERT INTO users (user_id, name, first_name,last_name, created_date, created_by, modified_date, modified_by,status_id,dob) VALUES (2, 'Raja R','Raja','R', now(), 'SYSTEM', now(), 'SYSTEM',1,'1991-03-01');
INSERT INTO users (user_id, name, first_name,last_name, created_date, created_by, modified_date, modified_by,status_id,dob) VALUES (3, 'Arun R','Arun','R', now(), 'SYSTEM', now(), 'SYSTEM',1,'1993-05-08');

