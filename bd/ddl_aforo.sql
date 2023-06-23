create table hechos.aforo
(
id_aforo bigserial not null,
fecha_ingreso TIMESTAMP WITH TIME ZONE null,
fecha_salida TIMESTAMP WITH TIME ZONE null,
primary key (id_aforo)
);

create table hechos.aforo_ingreso 
(
	fecha_ingreso timestamp WITH TIME ZONE null,
	conteo int8 null,
	primary key (fecha_ingreso)
);

create table hechos.aforo_salida 
(
	fecha_salida timestamp WITH TIME ZONE null,
	conteo int8 null,
	primary key (fecha_salida)
);



create table configuracion.parametro 
(
	id_parametro bigserial not null,
	parametro varchar(50) not null,
	valor varchar(50) not null,
	primary key (id_parametro)
);



SET timezone TO 'America/Bogota';

DROP TRIGGER trigger_conteo_ingreso ON hechos.aforo_ingreso;
DROP TRIGGER trigger_conteo_salida ON hechos.aforo_salida;

CREATE TRIGGER trigger_conteo_ingreso
BEFORE INSERT 
ON hechos.aforo_ingreso
FOR EACH ROW
EXECUTE PROCEDURE hechos.conteo_ingreso();


CREATE TRIGGER trigger_conteo_salida
BEFORE INSERT 
ON hechos.aforo_salida
FOR EACH ROW
EXECUTE PROCEDURE hechos.conteo_salida();


CREATE OR REPLACE FUNCTION hechos.conteo_ingreso()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	DECLARE 
		nuevo_conteo int4 = 0;
	BEGIN

		nuevo_conteo = 
		(
			--Obtengo la fecha con hora exacta para incrementar el conteo para la fecha y hora actual
			select (a.conteo + 1) as conteo
			from hechos.aforo_ingreso a
			where date_trunc('hour', a.fecha_ingreso) = date_trunc('hour', CURRENT_TIMESTAMP)
			--and a.fecha_ingreso >= (date_trunc('hour', CURRENT_TIMESTAMP) + interval '2 hour') --simulo una hora mas
		);
	
		--Si el conteo es nulo, quiere decir q inicio una nueva hora y debemos resetear el contador a 1 para comienze a contar los ingresos en esa hora
		if (nuevo_conteo is null) then		
			new.conteo = 1;
			new.fecha_ingreso = date_trunc('hour', new.fecha_ingreso);
		
			return new;
		else
			--Se actualiza el ingreso según su fecha y hora actual exacta
			delete from hechos.aforo_ingreso
			where fecha_ingreso = date_trunc('hour', CURRENT_TIMESTAMP);
			--where fecha_ingreso = (date_trunc('hour', CURRENT_TIMESTAMP) + interval '2 hour'); --simulo una hora mas
	
			new.conteo = nuevo_conteo;
			new.fecha_ingreso = date_trunc('hour', CURRENT_TIMESTAMP);
			
			return new;
		end if;
	END;
$function$
;



CREATE OR REPLACE FUNCTION hechos.conteo_salida()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
	DECLARE 
		nuevo_conteo int4 = 0;
	BEGIN

		nuevo_conteo = 
		(
			--Obtengo la fecha con hora exacta para incrementar el conteo para la fecha y hora actual
			select (a.conteo + 1) as conteo
			from hechos.aforo_salida a
			where date_trunc('hour', a.fecha_salida) = date_trunc('hour', CURRENT_TIMESTAMP)
			--and a.fecha_salida >= (date_trunc('hour', CURRENT_TIMESTAMP) + interval '2 hour') --simulo una hora mas
		);
	
		--Si el conteo es nulo, quiere decir q inicio una nueva hora y debemos resetear el contador a 1 para comienze a contar los ingresos en esa hora
		if (nuevo_conteo is null) then		
			new.conteo = 1;
			new.fecha_salida = date_trunc('hour', new.fecha_salida);
	
			return new;
		else
			--Se actualiza el ingreso según su fecha y hora actual exacta
			delete from hechos.aforo_salida 
			where fecha_salida = date_trunc('hour', CURRENT_TIMESTAMP);
			--where fecha_salida = (date_trunc('hour', CURRENT_TIMESTAMP) + interval '2 hour'); --simulo una hora mas
			
			new.conteo = nuevo_conteo;
			new.fecha_salida = date_trunc('hour', CURRENT_TIMESTAMP);
			
			return new;
		end if;
	END;
$function$
;






/*********************************************************************************************************************************
 * MIGRACION
 *********************************************************************************************************************************/

/* VOLCANDO INFO DE AFORO A AFORO_INGRESO */
insert into hechos.aforo_ingreso
select date_trunc('hour', a.fecha_ingreso) as fecha_ingreso, count(1) conteo
from hechos.aforo a
where 1=1
and a.fecha_ingreso is not null 
--and a.fecha_ingreso >= (CURRENT_DATE)
group by date_trunc('hour', fecha_ingreso)
order by 1
;


/* VOLCANDO INFO DE AFORO A AFORO_SALIDA */
insert into hechos.aforo_salida	
select date_trunc('hour', a.fecha_salida) as fecha_salida, count(1) conteo
from hechos.aforo a
where 1=1
and a.fecha_salida is not null 
--and a.fecha_salida >= (CURRENT_DATE)
group by date_trunc('hour', fecha_salida) 
order by 1
;





select count(1), max(id_aforo), min(id_aforo), max(fecha_ingreso), min(fecha_ingreso) from hechos.aforo;



select ingreso, salida, (ingreso - salida) aforo, (ingreso + salida) total
from 
(
	select 
	(
		select count(1)
		from hechos.aforo
		where fecha_ingreso is not null and fecha_ingreso >= CURRENT_DATE-1
	) ingreso,
	(
		select count(1)
		from hechos.aforo
		where fecha_salida is not null and fecha_salida >= CURRENT_DATE-1
	) salida
) tabla
;



select ingreso, salida, (ingreso - salida) aforo, (ingreso + salida) total
from 
(
	select 
	(
		select sum(conteo)
		from hechos.aforo_ingreso
		where fecha_ingreso >= CURRENT_DATE-1 --between (CURRENT_DATE-1) and (CURRENT_DATE) --CURRENT_DATE-1
	) ingreso,
	(
		select sum(conteo)
		from hechos.aforo_salida
		where fecha_salida >= CURRENT_DATE-1 --between (CURRENT_DATE-1) and (CURRENT_DATE) --CURRENT_DATE-1
	) salida
) tabla
;





select a.* --, date_trunc('hour', a.fecha_ingreso), date_trunc('hour', CURRENT_TIMESTAMP)
from hechos.aforo_ingreso a
where 1=1
and a.fecha_ingreso >= CURRENT_DATE
--and date_trunc('hour', a.fecha_ingreso) = date_trunc('hour', CURRENT_TIMESTAMP)
order by 1 desc
;


select a.* --, date_trunc('hour', a.fecha_ingreso), date_trunc('hour', CURRENT_TIMESTAMP)
from hechos.aforo_salida a
where 1=1
and a.fecha_salida >= CURRENT_DATE
--and date_trunc('hour', a.fecha_salida) = date_trunc('hour', CURRENT_TIMESTAMP)
order by 1 desc
;


INSERT INTO hechos.aforo_ingreso (fecha_ingreso, conteo) values ('2021-11-20 21:00:00.000', NULL);

INSERT INTO hechos.aforo_salida (fecha_salida, conteo) values ('2021-11-20 21:22:00.000', NULL);





truncate table hechos.aforo_ingreso;
truncate table hechos.aforo_salida;

















/*************** FUSIONAR INFO BD **************/
--CREATE TABLE hechos.aforo_ingreso as (
select date_trunc('hour', fecha_ingreso) as fecha_ingreso, count(1) conteo
from hechos.aforo
where 1=1
--and fecha_ingreso >= (CURRENT_DATE-1)
--and fecha_ingreso is not null
group by date_trunc('hour', fecha_ingreso)
order by 1
--)
;

select * from hechos.aforo_ingreso order by 1;

CREATE TABLE hechos.aforo_salida as (
select max(id_aforo) as id_aforo, date_trunc('hour', fecha_salida) as fecha_salida, count(1) conteo
from hechos.aforo
where 1=1
and fecha_salida >= (CURRENT_DATE-1)
group by date_trunc('hour', fecha_salida)
order by 1
)
;

select * from hechos.aforo_salida;

select 
* --max(id_aforo) 
from hechos.aforo where id_aforo > 8299190; --8299190



--truncate table hechos.aforo;

insert into hechos.aforo (fecha_ingreso, fecha_salida) 
(
	select ai.fecha_ingreso, null, ai.conteo
	from hechos.aforo_ingreso ai
)
;

insert into hechos.aforo (fecha_ingreso, fecha_salida) 
(
	select null, asal.fecha_salida, asal.conteo
	from hechos.aforo_salida asal
)
;

drop table hechos.aforo_ingreso;
drop table hechos.aforo_salida;

--3,876,057 sin filtro de hora
--293,391 con filtro de minuto
--5,890 con filtro de hora --> mejor opcion

select date_trunc('hour', now());

Desplegar Api-Rest con el nuevo campo de conteo

/*************** FUSIONAR INFO BD **************/





CREATE INDEX aforo_ingreso_fecha_ingreso_idx ON hechos.aforo_ingreso (fecha_ingreso);

CREATE INDEX aforo_salida_fecha_salida_idx ON hechos.aforo_salida (fecha_salida);


  
select CURRENT_DATE-345, CURRENT_DATE-313;

select * --sum(i.conteo)
from hechos.aforo_ingreso i
where i.fecha_ingreso between CURRENT_DATE-344 and CURRENT_DATE-313
--order by i.fecha_ingreso asc
;

select * --sum(i.conteo)
from hechos.aforo_salida i
where i.fecha_salida between CURRENT_DATE-344 and CURRENT_DATE-313
;

201990




/************************************************************************************
 * CAMPAÑAS QLIK
 ************************************************************************************/


select * from sisbol.campania;

11 "Entrega de Bolsos Reciclados Disponibilidad hasta agotar existencias"
13 Raspa y Gana
12 ENTREGA DE BOLSOS ECOLÓGICOS POR COMPRAS



--DIMENSION CAMPAÑA
select id_camp as campañas, nombre_camp as campaña, numpersonas_camp as numero_personas, valor_camp as valor, fechafin_camp as fecha_campaña
from sisbol.campania
;


--DIMENSION BARRIO
select id_barrio as barrios, comuna_bar as comuna, nombre_bar barrio
from sisbol.barrio
;



--DIMENSION CATEGORIAS
select codi_tip as categorias, desc_tip as categoria
from sisbol.vw_categoria
;



--DIMENSION LOCALES
select * --codi_tip as locales, desc_tip as local
from sisbol.tipo
where codi_gru = '03'
and valo_tip is not null
;



--DIMENSION CLIENTE
SELECT 
c.codi_cli as clientes, 
c.nrod_cli as numero_documento, 
tp.desc_tip as tipo_documento, 
c.nomb_cli as nombre_cliente, 
c.apel_cli as apellido_cliente, 
c.dire_cli as direccion_cliente, 
c.tele_cli as telefono_cliente, 
c.emai_cli as mail_cliente, 
case c.sexo_cli WHEN 'M' THEN 'Masculino' WHEN 'F' THEN 'Femenino' ELSE 'NA' end as sexo_cliente,
c.fnac_cli as fecha_nacimiento
FROM sisbol.cliente c
inner join sisbol.tipo tp on tp.codi_tip = c.tpid_cli
where 1=1
and c.codi_cli in ('57451','48592')
;


select *
from sisbol.boleta b
where b.id_camp = 9
;

select 
--count(1) 
--count(distinct b.codi_cli )
from sisbol.compra c 
where c.codi_bol in (select codi_bol from sisbol.boleta where id_camp = 7)
--and c.loca_com is null
;



select * from sisbol.tipo t where t.codi_tip = '02001';

select hc.*
--count(1) 
--count(distinct b.codi_cli )
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
where hc.codi_bol in (select codi_bol from sisbol.boleta where id_camp = 7)
order by 5
;


--HECHOS COMPRA
select 
--count(1)  
count(distinct b.codi_cli)
--distinct hc.fech_com, valo_com 
/*hc.codi_com as compras, 
cam.id_camp as campañas,  
ba.id_barrio as barrios, 
cate.codi_tip as categorias, 
hc.loca_com as locales, 
c.codi_cli as clientes, 
hc.valo_com as valor_compra, 
hc.fech_com as fecha_compra*/
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
where cam.id_camp in (7)
--and c.codi_cli in ('57451','48592')
and date_trunc('day', hc.fech_com) between TO_DATE('20191201','YYYYMMDD') and TO_DATE('20191201','YYYYMMDD')
and cate.codi_tip = '05017'
order by 1
;






/************************************************************************************
 * VALIDACION DATA
 ************************************************************************************/

6503 clientes 6	junio 	7	Campaña Noviembre
4743 clientes 8	Campaña Diciembre
246	 clientes 9	PAPÁ NOEL SE MUDÓ A UNICENTRO PASTO


--CLIENTES NUEVOS
select COUNT(distinct c.codi_cli)
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
where cam.id_camp = 8
and c.codi_cli not in 
(
select distinct c.codi_cli 
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
where cam.id_camp in (7)
)
;



--CLIENTES PERDIDOS
select distinct c.nrod_cli, c.nomb_cli, catee.codi_tip, catee.desc_tip, cam.nombre_camp --COUNT(distinct c.codi_cli)
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria catee on catee.codi_tip = t.valo_tip 
where 1=1
and cam.id_camp = 7
and catee.codi_tip = '05019'
and c.codi_cli not in 
(
	select distinct c.codi_cli 
	FROM sisbol.compra hc
	inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
	inner join sisbol.campania cam on cam.id_camp = b.id_camp 
	inner join sisbol.cliente c on c.codi_cli = b.codi_cli
	inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
	inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
	inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
	where 1=1
	and cam.id_camp = 8
	--and date_trunc('day', hc.fech_com) between TO_DATE('20191201','YYYYMMDD') and TO_DATE('20191201','YYYYMMDD')
	--and cate.codi_tip = '05017'
	--and c.nrod_cli in ('13005784')
)
--and date_trunc('day', hc.fech_com) between TO_DATE('20191201','YYYYMMDD') and TO_DATE('20191201','YYYYMMDD')
--and c.nrod_cli in ('13005784')
;


--CLIENTES FIDELIZADOS
select COUNT(distinct c.codi_cli)
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
where 1=1
and cam.id_camp in (7)
and c.codi_cli in 
(
select distinct c.codi_cli 
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
inner join sisbol.campania cam on cam.id_camp = b.id_camp 
inner join sisbol.cliente c on c.codi_cli = b.codi_cli
inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio 
inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
where 1=1
and cam.id_camp = 8
--and c.nrod_cli in ('30712024','13005784')
)
--and c.nrod_cli in ('30712024','13005784')
;

select date_trunc('hour', now());

select hc.* FROM sisbol.compra hc 
where 1=1
--and hc.codi_bol = '12693'
and date_trunc('day', hc.fech_com) between TO_DATE('20201001','YYYYMMDD') and TO_DATE('20201031','YYYYMMDD')
;

select * -- sum(case p.total WHEN 0 THEN p.total_convenio ELSE p.total end) as valor
from hechos.parking p
where date_trunc('day', p.fecha_factura) between TO_DATE('20201001','YYYYMMDD') and TO_DATE('20201001','YYYYMMDD')
;




select *
FROM hechos.aforo 
where date_trunc('day', fecha_ingreso) between TO_DATE('20201001','YYYYMMDD') and TO_DATE('20201001','YYYYMMDD')
;



select * FROM sisbol.boleta where codi_bol = '12693';

select * FROM sisbol.cliente where codi_cli = '48592';

select * FROM sisbol.barrio where id_barrio = 561;

select * FROM sisbol.campania order by 1;--where id_camp = 7;

select * from sisbol.tipo where codi_gru = '03' and valo_tip is not null and codi_tip in ('03027','03077','03071');

select * from sisbol.vw_categoria where codi_tip in ('05015');








/************************************************************************************
 * ACTUALIZACION DATA
 ************************************************************************************/

select * from  sisbol.cliente where id_barrio is null;

select * 
from sisbol.compra c 
where c.loca_com is null
;

select * from sisbol.campania c order by 1;

delete from sisbol.campania c where c.id_camp = 10;

update sisbol.compra set loca_com = '03113' where loca_com is null;


select * from sisbol.boleta where id_camp = 10;

update sisbol.boleta set id_camp = 8 where id_camp = 10;












/********************************************************************************************************************************
PARKING
********************************************************************************************************************************/

select 
--count(1), id_billing 
--p.id_billing, p.dispositivo_pago, p.punto_pago, p.consola_ing as entrada, p.fecha_factura, (p.minutos_facturados / 60) as horas_facturadas, 
--p.minutos_facturados, case p.total WHEN 0 THEN p.total_convenio ELSE p.total end as total, 
--p.tarifa, case when 0 != position('bicicleta' in lower(p.tarifa)) then 'BICICLETA' else upper(p.zona) end zona, p.placa2
* 
from hechos.parking p 
where 1=1
--and p.id_billing = 1598974311448
--and p.total > 2800
--and p.placa2 is null 
--and p.tarifa like '%Perno%'
--group by id_billing having count(1) > 1;

;


select 
      p.id_billing as cantidad, 
      p.punto_pago, 
      p.consola_ing as entrada, 
      p.fecha_factura as fecha_ingreso, 
      case when 0 != p.minutos_facturados then (p.minutos_facturados / 60) else 0 end as horas, 
      p.minutos_facturados as minutos, 
      case p.total WHEN 0 THEN p.total_convenio ELSE p.total end as valor, 
      p.tarifa as descripcion, 
      case when 0 != position('bicicleta' in lower(p.tarifa)) then 'BICICLETA' else upper(p.zona) end tipos_vehiculo, 
      p.placa2 as vehiculo,
      case when p.minutos_facturados <= 60 then 1 when (p.minutos_facturados > 60 and p.minutos_facturados <= 120) then 2 when (p.minutos_facturados > 120 and p.minutos_facturados <= 180) then 3 
      when (p.minutos_facturados > 180 and p.minutos_facturados <= 240) then 4 else 5 end as rango
from hechos.parking p
where 1=1
and p.fecha_factura >= (current_date) 
--and p.tarifa = 'Salida Parqueadero'
--and p.placa2 in ('KIB787')
--and p.minutos_facturados is null
--and p.punto_pago = 'PQ3'
order by p.total, p.punto_pago desc
;




--select minutos_facturados 
update hechos.parking set 
fecha_factura = '2020-12-01 00:30:00'
--minutos_facturados = 240,
--total = 2800,
--punto_pago = null
--fecha_factura = (current_date+1)
where id_billing in (1010)
;


SELECT
id_aforo as personas,
fecha_ingreso,
fecha_salida
FROM hechos.aforo 
where id_aforo = 11354
;





/************************************************************************************
 *	MEJORAMIENTO ESTRELLA
 ************************************************************************************/

select 
hc.codi_com as compras, 
b.id_camp as campañas,  
--ba.id_barrio as barrios, 
--cate.codi_tip as categorias, 
hc.loca_com as locales, 
b.codi_cli as clientes, 
hc.valo_com as valor, 
hc.fech_com as fecha_compra
FROM sisbol.compra hc
inner join sisbol.boleta b on b.codi_bol = hc.codi_bol and b.anul_bol <> 'S' 
--inner join sisbol.campania cam on cam.id_camp = b.id_camp 
--inner join sisbol.cliente c on c.codi_cli = b.codi_cli
--inner join sisbol.barrio ba on ba.id_barrio = c.id_barrio homologar 
--inner join sisbol.tipo t on t.codi_tip = hc.loca_com 
--inner join sisbol.vw_categoria cate on cate.codi_tip = t.valo_tip 
;
--68,727



select * from sisbol.vw_categoria cate;