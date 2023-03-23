DROP TABLE appmall.menu_options_role; commit;
DROP TABLE appmall.menu_options; commit;
drop table appmall.roles; commit;
drop table appmall.promotions; commit;
drop table appmall.stores; commit;
drop table appmall.categories; commit;
DROP VIEW appmall.vw_neighborhoods; commit;
DROP VIEW appmall.vw_identification_types; commit;
drop table appmall.configuration_images; commit;
drop table appmall.states; commit;

commit;


CREATE TABLE appmall.configuration_images (
	id_configuration bigserial NOT NULL,
	description varchar(400) NOT NULL,
	value varchar(400) NOT NULL,
	"type" varchar(50) NOT NULL,
	CONSTRAINT configuration_images_pkey PRIMARY KEY (id_configuration)
);



CREATE TABLE appmall.roles (
	id_role bigserial NOT NULL,
	"name" varchar(50) NOT NULL,
	description varchar(200) NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id_role)
);



CREATE TABLE appmall.states (
	id_state bigserial NOT NULL,
	"name" varchar(50) NOT NULL,
	description varchar(200) NOT NULL,
	CONSTRAINT states_pk PRIMARY KEY (id_state)
);


CREATE TABLE appmall.categories (
	id_category bigserial NOT NULL,
	id_state int8 NOT NULL,
	"name" varchar(50) NOT NULL,
	background_color varchar(15) NULL,
	"type" varchar(15) NULL,
	url_category_image varchar(200) NULL,
	url_category_icon1 varchar(200) NULL,
	url_category_icon2 varchar(200) NULL,
	order_category int4 NULL,
	CONSTRAINT categories_pk PRIMARY KEY (id_category),
	CONSTRAINT categories_states_fk FOREIGN KEY (id_state) REFERENCES appmall.states(id_state)
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
	CONSTRAINT menu_options_pk PRIMARY KEY (id_menu_option),
	CONSTRAINT menu_options_states_fk FOREIGN KEY (id_state) REFERENCES appmall.states(id_state)
);



CREATE TABLE appmall.menu_options_role (
	id_menu_option_role bigserial NOT NULL,
	id_menu_option int8 NOT NULL,
	id_role int8 NOT NULL,
	CONSTRAINT menu_options_role_pk PRIMARY KEY (id_menu_option_role),
	CONSTRAINT menu_options_role_menu_options_fk FOREIGN KEY (id_menu_option) REFERENCES appmall.menu_options(id_menu_option),
	CONSTRAINT menu_options_role_roles_fk FOREIGN KEY (id_role) REFERENCES appmall.roles(id_role)
);



CREATE TABLE appmall.stores (
	id_store bigserial NOT NULL,
	id_category int8 NOT NULL,
	id_state int8 NOT NULL,
	"name" varchar(50) NOT NULL,
	description varchar(100) NOT NULL,
	store_number varchar(50) NOT NULL,
	store_location varchar(100) NOT NULL,
	url_store_logo varchar(200) NOT NULL,
	url_store_image varchar(200) NOT NULL,
	phone varchar(200) NULL,
	website varchar(200) NULL,
	instagram varchar(200) NULL,
	facebook varchar(200) NULL,
	whatsapp varchar(20) NULL,
	twitter varchar(200) NULL,
	CONSTRAINT stores_pk PRIMARY KEY (id_store),
	CONSTRAINT stores_categories_fk FOREIGN KEY (id_category) REFERENCES appmall.categories(id_category),
	CONSTRAINT stores_states_fk FOREIGN KEY (id_state) REFERENCES appmall.states(id_state)
);



CREATE TABLE appmall.promotions (
	id_promotion bigserial NOT NULL,
	id_store int8 NOT NULL,
	id_state int8 NOT NULL,
	"name" varchar(50) NOT NULL,
	description varchar(400) NOT NULL,
	start_date varchar(8) NOT NULL,
	end_date varchar(8) NOT NULL,
	url_image varchar(200) NOT NULL,
	creation_date timestamp NOT NULL,
	CONSTRAINT promotions_pk PRIMARY KEY (id_promotion),
	CONSTRAINT promotions_states_fk FOREIGN KEY (id_state) REFERENCES appmall.states(id_state),
	CONSTRAINT promotions_stores_fk FOREIGN KEY (id_store) REFERENCES appmall.stores(id_store)
);


CREATE OR REPLACE VIEW appmall.vw_identification_types
AS SELECT x.codi_tip AS id_identification_type,
    x.valo_tip AS name,
    x.desc_tip AS description
FROM sisbol.tipo x
WHERE x.codi_gru = '01';

commit;


CREATE OR REPLACE VIEW appmall.vw_neighborhoods
AS SELECT barrio.id_barrio AS id_neighborhood, barrio.nombre_bar AS neighborhood FROM sisbol.barrio;

commit;  


-- DATOS BASICOS DEL SISTEMA
INSERT INTO appmall.configuration_images (description,value,type) VALUES
	 ('url imagen sorteos','https://drive.google.com/uc?id=1qUDBSVMpzdDvplwkw43k2sPzU50PGb9g', 'raffles'),
	 ('url imagen banner 1','https://drive.google.com/uc?id=1xMQkLbEUjHkxbdJI6k137fma9sBWnb9t', 'banner'),
	 ('url imagen banner 2','https://drive.google.com/uc?id=1GUnjHmt-OiTmqLRTzWBfDNc5EsiC6Dcx', 'banner');

commit;

INSERT INTO appmall.states ("name",description) VALUES
	 ('ACTIVO','Representa estado logico de operaciones y componentes'),
	 ('INACTIVO','Representa estado logico de operaciones y componentes');

commit;

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

commit;


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
	
commit;
	
INSERT INTO appmall.menu_options_role (id_menu_option,id_role) VALUES
	 (4,2),
	 (5,2),
	 (6,2),
	 (7,2);

commit;

INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(1, 1, 'Tiendas', '#39BFC2', 'DIRECTORY', 'https://drive.google.com/uc?id=1VRnZHt4wIfbSMGGAII0st2NUKODVYOJd', 'https://drive.google.com/uc?id=1KZ1NJJs9cW1Shubz5vXtbV9NOY6Rn7iR', 'https://drive.google.com/uc?id=1IVk0fGlmN04EgJy4F35dP9U2JNfMJlGp', 1);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(2, 1, 'Bancos', '#39BFC2', NULL, 'https://drive.google.com/uc?id=1clMTgv_8V3gQvhacKxeETtwBW5o221aL', 'https://drive.google.com/uc?id=1KZ1NJJs9cW1Shubz5vXtbV9NOY6Rn7iR', 'https://drive.google.com/uc?id=1IVk0fGlmN04EgJy4F35dP9U2JNfMJlGp', 2);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(3, 1, 'Entretenimiento', '#D9E021', 'DIRECTORY', 'https://drive.google.com/uc?id=1_KqdaI5VNLhyJegbSe7gjf_z74wQNerW', 'https://drive.google.com/uc?id=19WW6ORDtnjfyTKrh5que_mDyJDXOgFrx', 'https://drive.google.com/uc?id=1PjEdPpTOnYhJ2F-knZhHtS7Vv61YyAtp', 3);
INSERT INTO appmall.categories(id_category, id_state, name, background_color, "type", url_category_image, url_category_icon1, url_category_icon2, order_category) VALUES(4, 1, 'Comidas', '#ED3557', 'DIRECTORY', 'https://drive.google.com/uc?id=13urKtHs4Fz9P5fwRMfbifE84GJWyM375', 'https://drive.google.com/uc?id=1tHvhFf3iGhBhXoWJGIF8ctGJO3vvaNJ2', 'https://drive.google.com/uc?id=1o1YGD4fwqJbdS84H_LHpPEFRZzihGOlf', 4);

commit;

--truncate table appmall.home cascade;
--ALTER SEQUENCE appmall.home_id_home_seq RESTART WITH 1; 

--ALTER TABLE sisbol.cliente ADD pet boolean NULL;
--ALTER TABLE sisbol.cliente ADD terms_conditions boolean NULL;