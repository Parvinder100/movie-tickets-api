--liquibase formatted sql
--changeset singparv:create-suburb-table

CREATE TABLE suburb(
   id INT PRIMARY KEY auto_increment,
   tickets JSON,
   totalCost FLOAT
);