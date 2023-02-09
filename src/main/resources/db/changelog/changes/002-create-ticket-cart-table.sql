--liquibase formatted sql
--changeset singparv:create-ticket-cart-table

CREATE TABLE ticket_cart(
   id INT PRIMARY KEY auto_increment,
   total_cost FLOAT
);