CREATE DATABASE senior_test OWNER postgres;

ALTER USER postgres WITH PASSWORD 'postgres';

CREATE TABLE order_information (
   id SERIAL PRIMARY KEY,
   status varchar(100) NOT NULL ,
   creation_date TIMESTAMP NOT NULL
);

CREATE TABLE transaction (
   id varchar(40) PRIMARY KEY,
   order_id bigint NOT NULL ,
   type varchar(100) NOT NULL ,
   status varchar(100) NOT NULL,
   payment_method varchar(50) NOT NULL,
   merchant_name varchar(50) NOT NULL,
   value bigint NOT NULL,
   currency varchar(5) NOT NULL,
   buyer_name varchar(50) NOT NULL,
   buyer_document varchar(25) NOT NULL,
   message varchar(100) ,
   antifraud_status varchar(100),
   antifraud_message varchar(100),
   creation_date TIMESTAMP
);