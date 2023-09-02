select * from appmall.stores
order by name
;

--delete from appmall.stores;



--delete from appmall.promotions;

select * 
FROM sisbol.barrio ba
--where id_barrio = 561
order by 1
;

select * 
--emai_cli, count(1)
FROM sisbol.cliente
where 1=1
and nrod_cli in ('13072207','1107034509')
--and emai_cli = 'mario42004@gmail.com'
--group by emai_cli having count(1) > 3
;

uc?id=

https://drive.google.com/uc?id=1OgpNidwbdCDZB3d4yGL7oQgyRdsOL3VH

INSERT INTO sisbol.cliente (tpid_cli,nrod_cli,exped_cli,nomb_cli,apel_cli,dire_cli,tele_cli,fnac_cli,sexo_cli,emai_cli,prof_cli,punt_cli,fuco_cli,id_barrio,customer_type,"password",id_role,pet,terms_conditions) VALUES
	 ('01001','1085253061','PASTO-NARIÑO','MARIO FERNANDO','JOJOA ACOSTA','calle 28 n 20-50 torres del cielo 2','3182102258','1986-09-29','M','mario42004@gmail.com','04001',0,NULL,561,'WEB',NULL,2,NULL,NULL);




select * 
--count(1)
from appmall.stores s
where 1=1
--and (url_store_logo like '%ojm5ic%' or url_store_image like '%ojm5ic%')
--and id_store = 4
--and id_category = 4
order by s.name
;


select *
from appmall.categories c
order by name
;

select *
from appmall.configuration_images
order by 1
;

select * from appmall.promotions p;


--https://asset.cloudinary.com/hrqvk3zwl/6650081ff266343188184a006f946b3b


update appmall.stores set url_store_logo = 'https://res.cloudinary.com/hrqvk3zwl/image/upload/v1680374988/image-repository/1-01_cafetto_logo_2022_yrkkbg.jpg', url_store_image = 'https://res.cloudinary.com/hrqvk3zwl/image/upload/v1680374981/image-repository/1-01_cafetto_fachada_2022_uflh2l.jpg';

https://res.cloudinary.com/hrqvk3zwl/image/upload/v1680379727/image-repository/icon-tiendas-home_pihde4.png

--https://medium.com/pic-s-curso-básico-de-react-native/flatlist-infinite-scroll-clase-4-b7703a8c8fcb ejemplo de paginacion


--delete from appmall.stores;

select *
from appmall.menu_options_role
;

select *
from appmall.menu_options
;




select * from appmall.vw_neighborhoods vn;



--analista.credito@coopcarvajal.com

--Carta para levantamiento de prenda 







/************************************ PANTALLA HISTORIAL *********************************************/
Campaña: XX
Boletas Acumuladas: 22

LOGO
local: 1-22 JUMBO
Valor compra acumulado: 391.535

LOGO
local: 1-22 ARTURO CALLE
Valor compra acumulado: 200.000

select 
c.nombre_camp as campaign_name,
count(db.codi_dbo) as accumulated_tickets
FROM sisbol.boleta b 
inner join sisbol.detalle_bol db on b.codi_bol = db.codi_bol
inner join sisbol.campania c on b.id_camp = c.id_camp
where 1=1
and b.id_camp = (select max(c.id_camp) FROM sisbol.campania c where c.estado_camp = 'C')
and b.codi_cli = '221'
group by c.nombre_camp
;


select 
(select url_store_logo from appmall.stores s where s.store_number like '%'||(STRING_TO_ARRAY(t.desc_tip,' '))[1]||'%') as url_store_logo,
t.desc_tip as store,
sum(c.valo_com) as value
FROM sisbol.compra c
inner join sisbol.boleta b on b.codi_bol = c.codi_bol
inner join sisbol.tipo t ON t.codi_tip = c.loca_com
where 1=1
and b.id_camp = (select max(c.id_camp) FROM sisbol.campania c where c.estado_camp = 'C')
and b.codi_cli = 221
group by t.desc_tip
;




test_user@ccunicentropasto.com

$2a$10$3Dn.EDsx5fQ.Q.lwH53kje.Qz0w7PXs9nzwCU8aO1IlqRoRBBAJGW

select * 
from sisbol.cliente c
where 1=1
and c.codi_cli = '221'
--or c.nrod_cli = '13072207'
--and customer_type is null
order by 1 desc
;

select b.codi_cli, count(1) 
from sisbol.boleta b
where 1=1
and b.codi_cli = '221'
and b.id_camp = (select max(c.id_camp) FROM sisbol.campania c where c.estado_camp = 'C')
group by b.codi_cli having count(1) > 3
;

select * 
from sisbol.detalle_bol db
where db.codi_bol in (select b.codi_bol from sisbol.boleta b where b.codi_cli = '221')
;

select * 
from sisbol.compra c
where c.codi_bol in (select b.codi_bol from sisbol.boleta b where b.codi_cli = '221')
;







/***************************************** MAQUILLAR AFORO ******************************************/
ALTER TABLE hechos.aforo_ingreso DISABLE TRIGGER trigger_conteo_ingreso;
ALTER TABLE hechos.aforo_salida DISABLE TRIGGER trigger_conteo_salida;


--no te olvide yo del futuro de preguntarle al chat gpt como disable funcition xq el trigger las llama

create table hechos.aforo_ingreso_bk as select * from hechos.aforo_ingreso;
create table hechos.aforo_salida_bk as select * from hechos.aforo_salida;


delete
from hechos.aforo_ingreso
where date_trunc('day', fecha_ingreso) between TO_DATE('20230302','YYYYMMDD') and TO_DATE('20230302','YYYYMMDD')
;

select *
from hechos.aforo_ingreso
where date_trunc('day', fecha_ingreso) between TO_DATE('20230302','YYYYMMDD') and TO_DATE('20230302','YYYYMMDD')
;

INSERT INTO hechos.aforo_ingreso
select (fecha_ingreso - interval '7 day') as fecha_ingreso, conteo
from hechos.aforo_ingreso
where date_trunc('day', fecha_ingreso) between TO_DATE('20230309','YYYYMMDD') and TO_DATE('20230309','YYYYMMDD')
order by 1 --desc
;




select * 
from hechos.aforo_salida 
where date_trunc('day', fecha_salida) between TO_DATE('20230302','YYYYMMDD') and TO_DATE('20230302','YYYYMMDD')
;

delete
from hechos.aforo_salida 
where date_trunc('day', fecha_salida) between TO_DATE('20230302','YYYYMMDD') and TO_DATE('20230302','YYYYMMDD')
;

INSERT INTO hechos.aforo_salida
select (fecha_salida - interval '7 day') as fecha_salida, conteo
from hechos.aforo_salida
where date_trunc('day', fecha_salida) between TO_DATE('20230309','YYYYMMDD') and TO_DATE('20230309','YYYYMMDD')
order by 1
;


ALTER TABLE hechos.aforo_ingreso ENABLE TRIGGER trigger_conteo_ingreso;
ALTER TABLE hechos.aforo_salida ENABLE TRIGGER trigger_conteo_salida;


/***************************************** MAQUILLAR AFORO ******************************************/




select sum(conteo) from hechos.aforo_ingreso where fecha_ingreso >= CURRENT_DATE;

select sum(conteo) from hechos.aforo_salida where fecha_salida >= CURRENT_DATE;




2.3.3 Performance: Accurate Metadata [OK] (pantallazos)

4.2.2 Design: Minimum Functionality [OK] (enlaces de internet y no es llamativa para apple)

Guideline 5.1.1(v) - Data Collection and storage [OK] (eliminar cuenta)

5.1.1 Legal: Privacy - Data Collection and storage [OK] (quitar campos obligatorios relevantes)



