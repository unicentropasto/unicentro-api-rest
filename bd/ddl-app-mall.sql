
DROP TABLE appmall.menu_options_role;
DROP TABLE appmall.menu_options; 
drop table appmall.roles; 
drop table appmall.promotions;
drop table appmall.stores;
drop table appmall.categories;
DROP VIEW appmall.vw_neighborhoods;
DROP VIEW appmall.vw_identification_types;
drop table appmall.states;
 
 
 
create table appmall.states ( 
	id_state bigserial not null, 
	name varchar(50) not null, 
	description varchar(200) not null, 
	constraint states_pk primary key (id_state) 
); 
 

create table appmall.roles ( 
	id_role bigserial not null, 
	name varchar(50) not null, 
	description varchar(200) null, 
	constraint roles_pk primary key (id_role) 
); 
 
 

CREATE TABLE appmall.menu_options (
	id_menu_option bigserial NOT NULL,
	id_menu_option_parent int8 NULL,
	id_state int8 NOT NULL,
	menu_option varchar(100) NOT NULL,
	description varchar(100) NOT NULL,
	url_menu_option_image varchar(200) NOT NULL,
	redirect varchar(100) NULL,
	color varchar(10) NULL,
	"level" int4 NOT NULL,
	menu_option_order int4 NOT NULL,
	creation_date timestamp NOT NULL,
	modification_date timestamp NULL,
	CONSTRAINT menu_options_pk PRIMARY KEY (id_menu_option)
);

ALTER TABLE appmall.menu_options ADD CONSTRAINT menu_options_states_fk FOREIGN KEY (id_state) REFERENCES appmall.states(id_state);
 
 

CREATE TABLE appmall.menu_options_role (
	id_menu_option_role bigserial NOT NULL,
	id_menu_option int8 NOT NULL,
	id_role int8 NOT NULL,
	CONSTRAINT menu_options_role_pk PRIMARY KEY (id_menu_option_role)
);

ALTER TABLE appmall.menu_options_role ADD CONSTRAINT menu_options_role_menu_options_fk FOREIGN KEY (id_menu_option) REFERENCES appmall.menu_options(id_menu_option);
ALTER TABLE appmall.menu_options_role ADD CONSTRAINT menu_options_role_roles_fk FOREIGN KEY (id_role) REFERENCES appmall.roles(id_role);



CREATE OR REPLACE VIEW appmall.vw_neighborhoods AS 
select 
id_barrio as id_neighborhood,
nombre_bar as neighborhood
FROM sisbol.barrio
;



CREATE OR REPLACE VIEW appmall.vw_identification_types AS 
SELECT x.codi_tip as id_identification_type, 
x.valo_tip as name,
x.desc_tip as description
FROM sisbol.tipo x WHERE codi_gru = '01'
;



create table appmall.categories ( 
	id_category bigserial not null,  
	id_state int8 not null, 
	name varchar(50) not null, 
	background_color varchar(15) null, 
	type varchar(15) null,
	url_category_image varchar(200) null, 
	url_category_icon1 varchar(200) null, 
	url_category_icon2 varchar(200) null, 
	order_category int4 null, 
	constraint categories_pk primary key (id_category) 
);

alter table appmall.categories add constraint categories_states_fk foreign key (id_state) references appmall.states(id_state); 



create table appmall.stores ( 
	id_store bigserial not null,  
	id_category int8 not null,
	id_state int8 not null,
	name varchar(50) not null, 
	description varchar(100) not null, 
	store_number varchar(20) not null,
	store_location varchar(20) not null, 
	phone varchar(200) null, 
	website varchar(200) null, 
	instagram varchar(200) null, 
	facebook varchar(200) null, 
	whatsapp varchar(20) null, 
	url_store_logo varchar(200) not null, 
	url_store_image varchar(200) not null, 
	constraint stores_pk primary key (id_store) 
);


alter table appmall.stores add constraint stores_categories_fk foreign key (id_category) references appmall.categories(id_category); 
alter table appmall.stores add constraint stores_states_fk foreign key (id_state) references appmall.states(id_state);


create table appmall.promotions ( 
	id_promotion bigserial not null,   
	id_store int8 not null,
	id_state int8 not null,
	name varchar(50) not null, 
	description varchar(400) not null, 
	start_date varchar(8) not null, 
	end_date varchar(8) not null, 
	url_image varchar(200) not null, 
	creation_date timestamp not null,
	constraint promotions_pk primary key (id_promotion) 
);

alter table appmall.promotions add constraint promotions_stores_fk foreign key (id_store) references appmall.stores(id_store);  
alter table appmall.promotions add constraint promotions_states_fk foreign key (id_state) references appmall.states(id_state); 




--truncate table appmall.home cascade;
--ALTER SEQUENCE appmall.home_id_home_seq RESTART WITH 1; 

ALTER TABLE sisbol.cliente ADD pet boolean NULL;
ALTER TABLE sisbol.cliente ADD terms_conditions boolean NULL;





drop  table appmall.configurations;

create table appmall.configurations 
(
	id_configuration bigserial not null,
	description varchar(400) not null,
	value varchar(400) not null,
	type varchar(50) not null,
	primary key (id_configuration)
);


INSERT INTO appmall.configurations (description,value,type) VALUES
	 ('url imagen sorteos','https://drive.google.com/uc?id=1qUDBSVMpzdDvplwkw43k2sPzU50PGb9g', 'raffles'),
	 ('url imagen banner 1','https://drive.google.com/uc?id=1xMQkLbEUjHkxbdJI6k137fma9sBWnb9t', 'banner'),
	 ('url imagen banner 2','https://drive.google.com/uc?id=1GUnjHmt-OiTmqLRTzWBfDNc5EsiC6Dcx', 'banner'),
 	 ('url logo home tab bar','https://drive.google.com/uc?id=1OgpNidwbdCDZB3d4yGL7oQgyRdsOL3VH', 'home');





-- DATOS BASICOS DEL SISTEMA

INSERT INTO appmall.states ("name",description) VALUES
	 ('ACTIVO','Representa estado logico de operaciones y componentes'),
	 ('INACTIVO','Representa estado logico de operaciones y componentes');


INSERT INTO appmall.menu_options (id_menu_option_parent,id_state,menu_option,description,url_menu_option_image,redirect,color,"level",menu_option_order,creation_date,modification_date) VALUES
	 (NULL,1,'Directorio','Tiendas, entretenimiento y gastronomía','storefront-outline',NULL,'#F18032',1,1,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Registrar Facturas','Registra tus facturas','camera',NULL,'#39BFC2',1,2,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Promociones','Disfruta increíbles descuentos','handbag',NULL,'#F05642',1,3,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Agenda','Programación de eventos','calendar','https://www.ccunicentropasto.com/cartelera/','#DAD2BD',1,4,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Noticias','Información de servicios y eventos','bell','https://www.ccunicentropasto.com/servicios/','#F05642',1,5,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Cómo llegar','Rutas y parqueaderos','location-pin','','#39BFC2',1,6,'2022-12-07 00:00:00.000',NULL),
	 (NULL,1,'Contacto','Queremos saber que piensas','call-out',NULL,'#F18032',1,7,'2022-12-07 00:00:00.000',NULL);


INSERT INTO appmall.roles ("name",description) VALUES
	 ('Admin','Rol administrador de la app'),
	 ('Cliente','Rol de cliente generico');


INSERT INTO appmall.menu_options_role (id_menu_option,id_role) VALUES
	 (1,1),
	 (2,1),
	 (3,1),
	 (4,1),
	 (5,1),
	 (6,1),
	 (7,1),
	 (1,2),
	 (2,2),
	 (3,2);
	
INSERT INTO appmall.menu_options_role (id_menu_option,id_role) VALUES
	 (4,2),
	 (5,2),
	 (6,2),
	 (7,2);


INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(1, 1, 'Tiendas', '#39BFC2', 'DIRECTORY', 'https://drive.google.com/uc?id=1VRnZHt4wIfbSMGGAII0st2NUKODVYOJd', 'https://drive.google.com/uc?id=1KZ1NJJs9cW1Shubz5vXtbV9NOY6Rn7iR', 'https://drive.google.com/uc?id=1IVk0fGlmN04EgJy4F35dP9U2JNfMJlGp', 1);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(2, 1, 'Bancos', '#39BFC2', NULL, 'https://drive.google.com/uc?id=1clMTgv_8V3gQvhacKxeETtwBW5o221aL', 'https://drive.google.com/uc?id=1KZ1NJJs9cW1Shubz5vXtbV9NOY6Rn7iR', 'https://drive.google.com/uc?id=1IVk0fGlmN04EgJy4F35dP9U2JNfMJlGp', 2);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(3, 1, 'Entretenimiento', '#D9E021', 'DIRECTORY', 'https://drive.google.com/uc?id=1_KqdaI5VNLhyJegbSe7gjf_z74wQNerW', 'https://drive.google.com/uc?id=19WW6ORDtnjfyTKrh5que_mDyJDXOgFrx', 'https://drive.google.com/uc?id=1PjEdPpTOnYhJ2F-knZhHtS7Vv61YyAtp', 3);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(4, 1, 'Comidas', '#ED3557', 'DIRECTORY', 'https://drive.google.com/uc?id=13urKtHs4Fz9P5fwRMfbifE84GJWyM375', 'https://drive.google.com/uc?id=1tHvhFf3iGhBhXoWJGIF8ctGJO3vvaNJ2', 'https://drive.google.com/uc?id=1o1YGD4fwqJbdS84H_LHpPEFRZzihGOlf', 4);
