/** ELIMINA L'UTENTE MAP SE VIENE TROVATO **/
DROP USER IF EXISTS MapUser;

/** RICREA L'UTENTE MAP CON PASSWORD MAP **/
CREATE USER 'MapUser' IDENTIFIED BY 'map';

/** CREA IL DATABASE map SE NON ESISTE GIA' **/
CREATE DATABASE IF NOT EXISTS MapDB;

USE MapDB;

/** DA I PRIVILEGI ALL'UTENTE MAP VERSO IL DATABASE MAP **/
GRANT ALL PRIVILEGES ON MapDB.* TO 'MapUser'@'%';

/** CREA UNA TABELLA DI ESEMPIO exampleTab **/
DROP TABLE IF EXISTS exampleTab;

CREATE TABLE mapdb.exampleTab( 
X1 float, 
X2 float, 
X3 float 
); 
insert into mapdb.exampleTab values(1,2,0); 
insert into mapdb.exampleTab values(0,1,-1); 
insert into mapdb.exampleTab values(1,3,5); 
insert into mapdb.exampleTab values(1,3,4); 
insert into mapdb.exampleTab values(2,2,0); 

commit;
