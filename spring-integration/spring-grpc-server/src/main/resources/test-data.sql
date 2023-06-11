DROP TABLE IF EXISTS grpcServerTable;
CREATE TABLE grpcServerTable AS SELECT * FROM CSVREAD('classpath:grpcServerData.csv');

