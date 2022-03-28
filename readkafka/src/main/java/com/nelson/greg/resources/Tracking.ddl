-- DROP SCHEMA Tracking;

CREATE SCHEMA Tracking;
-- Tracking.Tracking.Orders definition

-- Drop table

-- DROP TABLE Tracking.Tracking.Orders;

CREATE TABLE Tracking.Tracking.Orders (
	OrderId bigint IDENTITY(0,1) NOT NULL,
	Description varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT Orders_PK PRIMARY KEY (OrderId)
);
