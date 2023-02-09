--liquibase formatted sql
--changeset singparv:create-ticket-table

CREATE TABLE ticket(
   id INT PRIMARY KEY auto_increment,
   ticket_type VARCHAR(8),
   quantity INT,
   total_cost FLOAT,
   cart_id INT
);