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


delete FROM sisbol.cliente
where 1=1
and nrod_cli in ('13072207')
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





/******************************************************* QLIK 2024 ******************************************************************/

/*
SELECT 
0 as cantidad,
null as punto_pago, 
t.TransactionDate as fecha_ingreso,
null as fecha_egreso,
DATEDIFF (HOUR, t.TransactionDate , GETDATE()) as horas,
DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) as minutos, 
0 valor,
case 
	when (tv.ValueName  = 'Plate') or (tv.ValueName = 'VehicleType' and tv.Value = '1') then 'Carros'
	when (tv.ValueName = 'VehicleType' and tv.Value = '2') then 'Motos'
else 'Bicicletas' end 
as tipos_vehiculo,
case when tv.Value IN ('1','2') then '' ELSE tv.Value end
as vehiculo,
case when DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) <= 60 then 1 when (DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) > 60 and DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) <= 120) then 2 when (DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) > 120 and DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) <= 180) then 3 when (DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) > 180 and DATEDIFF (MINUTE, t.TransactionDate , GETDATE()) <= 240) then 4 else 5 end as rango
from CI_ControlAccessDb_New.dbo.Tb_Transaction t WITH (NOLOCK)
inner join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = t.Id_Transaction
where 0 = 
(
	SELECT count(1)
	FROM CI_ControlAccessDb_New.dbo.Tb_Transaction t_temp WITH (NOLOCK)
    where t_temp.Id_TransactionParent = t.Id_TransactionParent and t_temp.Id_Transaction != t.Id_TransactionParent
    --where t_temp.Id_TransactionParent = t.Id_Transaction or (t_temp.Id_Transaction = t.Id_Transaction and t_temp.Id_TransactionParent is not null) era la forma vieja para darme cuenta cuando el vehiculo no tenia facturacion
)
and tv.Id_TransactionValue = 
(
	SELECT max(tv_temp.Id_TransactionValue)
	from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
	where tv_temp.Id_Transaction = tv.Id_Transaction
)
and cast(t.TransactionDate as Date) = cast(GETDATE() as Date)
UNION*/
SELECT 
b.Id_Billing as cantidad,
b.Prefix as punto_pago, 
DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) as fecha_ingreso,
b.InvoiceDate as fecha_egreso,
case when 0 != b.MinutesBilled then (b.MinutesBilled / 60) else 0 end as horas,
b.MinutesBilled as minutos, 
case b.Total when 0 then b.TotalAgreements ELSE b.Total end as valor,
case 
	when (b.Receipt like '%Carro%') then 'Carros'
	when (b.Receipt like '%Moto%') then 'Motos'
	when (b.Receipt like '%Bicicleta%') then 'Bicicletas'
	when 'CARROS' = tz.Name then 'Carros' 
else tz.Name end 
as tipos_vehiculo,
case when '' != b.Receipt and 0 != charindex('\nEntrada', b.Receipt) then
	case when '' != TRIM(SUBSTRING(b.Receipt, charindex('Placa', b.Receipt)+6, (charindex('\nEntrada', b.Receipt)-charindex('Placa', b.Receipt))-6)) then 
		TRIM(SUBSTRING(b.Receipt, charindex('Placa', b.Receipt)+6, (charindex('\nEntrada', b.Receipt)-charindex('Placa', b.Receipt))-6))
	else
		case when tv.Value IN ('1','2') then 
			'' 
		else 
			tv.Value 
		end 
	end
else 
	''
end as vehiculo,
case when b.MinutesBilled <= 60 then 1 when (b.MinutesBilled > 60 and b.MinutesBilled <= 120) then 2 when (b.MinutesBilled > 120 and b.MinutesBilled <= 180) then 3 when (b.MinutesBilled > 180 and b.MinutesBilled <= 240) then 4 else 5 end as rango
from CI_ControlAccessDb_New.dbo.Tb_Billing b WITH (NOLOCK)
inner join CI_ControlAccessDb_New.dbo.Tb_Zone tz WITH (NOLOCK) on tz.Id_Zone = b.Id_Zone
left join CI_ControlAccessDb_New.dbo.Tb_Transaction t WITH (NOLOCK) on t.Id_Transaction = b.Id_Transaction
left join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = t.Id_TransactionParent 
and tv.Id_TransactionValue = 
(
	SELECT max(tv_temp.Id_TransactionValue)
	from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
	where tv_temp.Id_Transaction = tv.Id_Transaction 
)
where DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) >= '2023-11-01 00:00:00.000' //Trae los ultimos dos meses
;



--query create table CI_ControlAccessDb_New.dbo.temp_billing
SELECT 
--convert(varchar, b.InvoiceDate, 23), count(1) 
b.*, tz.Name, tv.Value, tv.Id_TransactionValue
from CI_ControlAccessDb_New.dbo.Tb_Billing b WITH (NOLOCK)
left join CI_ControlAccessDb_New.dbo.Tb_Zone tz WITH (NOLOCK) on tz.Id_Zone = b.Id_Zone
left join CI_ControlAccessDb_New.dbo.Tb_Transaction t WITH (NOLOCK) on t.Id_Transaction = b.Id_Transaction
left join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = t.Id_TransactionParent 
and tv.Id_TransactionValue = 
(
	SELECT max(tv_temp.Id_TransactionValue)
	from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
	where tv_temp.Id_Transaction = tv.Id_Transaction 
	and tv_temp.Value NOT IN ('1','2')
)
where 1=1
and b.IdInvoice in (491358)
--and b.Id_Billing != 1673113333958
--and DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) BETWEEN '2024-01-01 00:00:00.000' and '2024-01-01 23:59:00.000'
--and b.InvoiceDate BETWEEN '2024-01-03 00:00:00.000' and '2024-01-03 23:59:00.000'
--and b.Receipt like '%GCS827%'
--and tv.Value = 'GCS827'
--and b.SubTotal = 600
--order by b.IdInvoice
--GROUP by convert(varchar, b.InvoiceDate, 23)
;




SELECT DISTINCT
b.*, tv.Value, tv.Id_TransactionValue
, case when (b.Name is null and b.TotalAgreements = 3300) then 'Motos' when (b.Name is null and b.TotalAgreements = 4500 or b.Name is null and b.Total in(4500)) then 'Carros' 
when (b.Total in (600,1200,3600)) then 'Bicicletas' else b.Name end as tipos_vehiculo
--, case when (ag.Id_Agreement = 1) then 'Cinemark' when (ag.Id_Agreement = 3) then 'Jumbo' when (ag.Id_Agreement = 4) then 'Empleados y Domiciliarios' when (ag.Id_Agreement = 7) then 'Empleados Jumbo' else 'colocar convenio' end as convenio
--convert(varchar, b.InvoiceDate, 23) dia
--, count(1) duplicadas
from CI_ControlAccessDb_New.dbo.temp_billing b
left join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = b.Id_TransactionParent 
and tv.Id_TransactionValue = 
(
	SELECT max(tv_temp.Id_TransactionValue)
	from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
	where tv_temp.Id_Transaction = tv.Id_Transaction 
	and tv_temp.Value NOT IN ('1','2')
)
left join CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied ag WITH (NOLOCK) on ag.Id_Billing = b.Id_Billing --incluye repetidos para convenio
where 1=1
--and b.Id_Billing = 221704243247
--and b.IdInvoice in (1848809)
and b.InvoiceDate BETWEEN '2024-01-01 00:00:00.000' and '2024-01-31 23:59:00.000' 
and b.Total = 3300 --4500 --600 -- en qlik filtro por valor actual del parqueadero según vehículo
--and b.Total not in (0, 4500, 600, 3300) -- pergnotado
--and b.Total = 0 -- en qlik filtro para convenios
--and (b.Name <> 'Carros' or b.Name is null) and b.Receipt like '%Moto%' and b.Prefix <> 'OAV1' -- en qlik filtro para convenios activar cuando busque motos
--and (b.Name <> 'Motos' or b.Name is null) and b.Receipt like '%Carro%' and b.Prefix <> 'OAV1' -- en qlik filtro para convenios activar cuando busque carros
--and EXISTS (SELECT 1 from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied where Id_Billing = b.Id_Billing) -- en qlik filtro para convenios
--group by convert(varchar, b.InvoiceDate, 23)
--order by 2 asc
order by b.IdInvoice asc
--GROUP by 
;

--NOTA: el query base solo tiene q tener el where b.InvoiceDate BETWEEN '2024-01-01 00:00:00.000' and '2024-01-31 23:59:00.000'
-- los otros filtros deben aplicarse en qlik para obtener la data segun su categoria (convenios motos, carros, bicis y ciudadano de a pie moto, carro y bici)

--NOTA: el tipos_vehiculo se debe colocar de acuerdo al b.Total si es 4500 carro si es 3300 moto y si es 600 bici

-- hay q sentarse hacer en qlik la tabla por tipos_vehiculo, convenios y pergnotados




SELECT * from CI_ControlAccessDb_New.dbo.Tb_Zone tz
;

SELECT DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) as fecha_ingreso, b.*
from CI_ControlAccessDb_New.dbo.Tb_Billing b 
where 1=1
and b.Id_Billing in (191706232230)
--and b.IdInvoice in (491468)
--and b.SubTotal = 600
;



SELECT * from CI_ControlAccessDb_New.dbo.Tb_Transaction where Id_Transaction = '4-757514592'
;

SELECT * from CI_ControlAccessDb_New.dbo.Tb_TransactionValues where Id_Transaction = '2-757494233' order by 1
;


SELECT * from CI_ControlAccessDb_New.dbo.Tb_BillingItem
where Id_Billing = 211704219462
;

SELECT * from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied
where Id_Billing = 211704219462
;


SELECT * from CI_ControlAccessDb.dbo.Tb_Agreement;

SELECT * from CI_ControlAccessDb.dbo.Tb_Zone tz;
SELECT * from CI_ControlAccessDb_New.dbo.Tb_Zone tz;



SELECT DISTINCT tv.Value from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv