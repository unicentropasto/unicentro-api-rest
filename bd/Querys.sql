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

https:drive.google.com/uc?id=1OgpNidwbdCDZB3d4yGL7oQgyRdsOL3VH

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


--https:asset.cloudinary.com/hrqvk3zwl/6650081ff266343188184a006f946b3b


update appmall.stores set url_store_logo = 'https:res.cloudinary.com/hrqvk3zwl/image/upload/v1680374988/image-repository/1-01_cafetto_logo_2022_yrkkbg.jpg', url_store_image = 'https:res.cloudinary.com/hrqvk3zwl/image/upload/v1680374981/image-repository/1-01_cafetto_fachada_2022_uflh2l.jpg';

https:res.cloudinary.com/hrqvk3zwl/image/upload/v1680379727/image-repository/icon-tiendas-home_pihde4.png

--https:medium.com/pic-s-curso-básico-de-react-native/flatlist-infinite-scroll-clase-4-b7703a8c8fcb ejemplo de paginacion


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











select CAST(59000 / 50000 as INT) as p;

select * from CI_ControlAccessDb_New.dbo.Tb_Billing b where b.IdInvoice in (12601);



/******************************************************* QLIK ******************************************************************/

--Hechos Parking 2025 SC
--SELECT
--MONTH(debug.fecha_ingreso) mes, DAY(debug.fecha_ingreso) dia, debug.tipos_vehiculo, sum(debug.valor) valor, sum(debug.cantidad_registros) cantidad_registros
--from 
--(
	select  top 1
	parking.cantidad, parking.punto_pago, parking.horas, parking.minutos, parking.valor, parking.tipos_vehiculo, parking.vehiculo, parking.rango, parking.concesiones, parking.convenio, parking.IdInvoice, parking.IdToken,
	case parking.tipos_vehiculo
		when 'Carros' then CAST(parking.valor / 50000 as INT)
		when 'Motos' then CAST(parking.valor / 30000 as INT)
		when 'Bicicletas' then CAST(parking.valor / 10000 as INT)
		when 'Tarjeta Perdida' then 0
	end as pernoctado,
	case parking.tipos_vehiculo
		when 'Carros' then case when (CAST(parking.valor / 50000 as INT) = 0) then 1 else CAST((parking.valor - 50000) / 4500 as INT) end
		when 'Motos' then case when (CAST(parking.valor / 30000 as INT) = 0) then 1 else CAST((parking.valor - 30000) / 3300 as INT) end
		when 'Bicicletas' then case when (CAST(parking.valor / 10000 as INT) = 0) then 1 else CAST((parking.valor - 10000) / 600 as INT) end
		when 'Tarjeta Perdida' then 1
	end as cantidad_registros,
	case when 0 != (case parking.tipos_vehiculo
						when 'Carros' then CAST(parking.valor / 50000 as INT)
						when 'Motos' then CAST(parking.valor / 30000 as INT)
						when 'Bicicletas' then CAST(parking.valor / 10000 as INT)
						when 'Tarjeta Perdida' then 0
						end
					) 
	then 
		parking.fecha_egreso 
	else 
		parking.fecha_ingreso 
	end as fecha_ingreso, 
	parking.fecha_egreso
	from 
	(
		SELECT 
		b.Id_Billing as cantidad,
		b.Prefix as punto_pago, 
		DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) as fecha_ingreso,
		b.InvoiceDate as fecha_egreso,
		case when 0 != b.MinutesBilled then (b.MinutesBilled / 60) else 0 end as horas,
		b.MinutesBilled as minutos,
		b.Total as valor, 
		case 
			when b.Total = 20000 then 'Tarjeta Perdida'
			when ((b.Total % 10600) in (0, 600, 1200, 10000))  then 'Bicicletas'
			when ((b.Total % 33300) in (0, 3300)) then 'Motos'
			when ((b.Total % 54500) in (0, 4500)) then 'Carros'
		else tz.Name 
		end as tipos_vehiculo,
		case when '' != b.Receipt and 0 != charindex('id device', LOWER(b.Receipt)) then
			case when '' != TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7)) then 
				TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7))
			else
				case when tv.Value IN ('1','2','4') then 
					'' 
				else 
					tv.Value 
				end 
			end
		else 
			case when tv.Value IN ('1','2','4') then 
				'' 
			else 
				tv.Value 
			end 
		end as vehiculo,
		case when b.MinutesBilled <= 60 then 1 when (b.MinutesBilled > 60 and b.MinutesBilled <= 120) then 2 when (b.MinutesBilled > 120 and b.MinutesBilled <= 180) then 3 when (b.MinutesBilled > 180 and b.MinutesBilled <= 240) then 4 else 5 end as rango,
		'N/A Convenio' as concesiones,
		'No' as convenio,
		 b.IdInvoice,
		 b.IdToken
--		, b.InvoiceDate, b.Receipt, tv.Value, b.Total, b.TotalAgreements, b.TotalAdjustments, tz.Name, b.IdInvoice, b.LastInsert, b.IdToken, (SELECT top 1 ag.Id_Agreement from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied ag where ag.Id_Billing = b.Id_Billing order by ag.Id_AgreementsApplied desc) as Id_Agreement
		from CI_ControlAccessDb_New.dbo.Tb_Billing b WITH (NOLOCK)
		left join CI_ControlAccessDb_New.dbo.Tb_Zone tz WITH (NOLOCK) on tz.Id_Zone = b.Id_Zone
		left join CI_ControlAccessDb_New.dbo.Tb_Transaction t WITH (NOLOCK) on t.Id_Transaction = b.Id_Transaction
		left join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = t.Id_TransactionParent 
		and tv.Id_TransactionValue = 
		(
			SELECT max(tv_temp.Id_TransactionValue)
			from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
			where tv_temp.Id_Transaction = tv.Id_Transaction and tv_temp.ValueName = 'plate'
		)
		where YEAR(b.InvoiceDate) = 2025
		and b.Prefix <> 'OAV1' --Se omiten registros de pruebas
		and b.Total <> 0
	) as parking
--) as debug
--where 1=1
--and debug.IdInvoice in (12198) --quitar
--and MONTH(debug.fecha_ingreso) in (11,12)
--and DAY(debug.fecha_ingreso) = 16
--and debug.tipos_vehiculo = 'Carros' --'Carros' 'Motos' 'Bicicletas' 'Tarjeta Perdida' --quitar
--and debug.convenio = 'No'
--and debug.cantidad in (951721886271) --quitar
--and debug.tipos_vehiculo is null
--and debug.pernoctado = 1
--GROUP by MONTH(debug.fecha_ingreso), DAY(debug.fecha_ingreso), debug.tipos_vehiculo order by 1, 2
--order by debug.IdInvoice 
	UNION
-- Hechos Parking 2025 CC
--SELECT *
--MONTH(debug.fecha_ingreso) mes, DAY(debug.fecha_ingreso) dia, debug.tipos_vehiculo, debug.convenio, debug.concesiones, sum(debug.valor) valor, sum(debug.cantidad_registros) cantidad_registros
--from 
--(
	select top 1
	parking.cantidad, parking.punto_pago, parking.horas, parking.minutos, parking.valor, parking.tipos_vehiculo, parking.vehiculo, parking.rango, parking.concesiones, parking.convenio, parking.IdInvoice, parking.IdToken,	
	case parking.tipos_vehiculo
		when 'Carros' then CAST(parking.valor / 50000 as INT)
		when 'Motos' then CAST(parking.valor / 30000 as INT)
		when 'Bicicletas' then CAST(parking.valor / 10000 as INT)
		when 'Tarjeta Perdida' then 0
	end as pernoctado,
	case parking.tipos_vehiculo
		when 'Carros' then case when (CAST(parking.valor / 50000 as INT) = 0) then 1 else CAST((parking.valor - 50000) / 4500 as INT) end
		when 'Motos' then case when (CAST(parking.valor / 30000 as INT) = 0) then 1 else CAST((parking.valor - 30000) / 3300 as INT) end
		when 'Bicicletas' then case when (CAST(parking.valor / 10000 as INT) = 0) then 1 else CAST((parking.valor - 10000) / 600 as INT) end
		when 'Tarjeta Perdida' then 1
	end as cantidad_registros,
	case when 0 != (case parking.tipos_vehiculo
						when 'Carros' then CAST(parking.valor / 50000 as INT)
						when 'Motos' then CAST(parking.valor / 30000 as INT)
						when 'Bicicletas' then CAST(parking.valor / 10000 as INT)
						when 'Tarjeta Perdida' then 0
						end
					) 
	then 
		parking.fecha_egreso 
	else 
		parking.fecha_ingreso 
	end as fecha_ingreso, 
	parking.fecha_egreso
	from 
	(
		SELECT 
		b.Id_Billing as cantidad,
		b.Prefix as punto_pago, 
		DATEADD(minute, -b.MinutesBilled, b.InvoiceDate) as fecha_ingreso,
		b.InvoiceDate as fecha_egreso,
		case when 0 != b.MinutesBilled then (b.MinutesBilled / 60) else 0 end as horas,
		b.MinutesBilled as minutos,
		b.Total as valor, 
		case 
			when (b.TotalAdjustments = 0 and tz.Name = 'Carros') then 'Carros'
			when (b.TotalAdjustments = 0 and tz.Name = 'Motos') then 'Motos'
			when b.Total = 20000 then 'Tarjeta Perdida'
		else tz.Name 
		end as tipos_vehiculo,
		case when '' != b.Receipt and 0 != charindex('id device', LOWER(b.Receipt)) then
			case when '' != TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7)) then 
				TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7))
			else
				case when tv.Value IN ('1','2','4') then 
					'' 
				else 
					tv.Value 
				end 
			end
		else 
			case when tv.Value IN ('1','2','4') then 
				'' 
			else 
				tv.Value 
			end 
		end as vehiculo,
		case when b.MinutesBilled <= 60 then 1 when (b.MinutesBilled > 60 and b.MinutesBilled <= 120) then 2 when (b.MinutesBilled > 120 and b.MinutesBilled <= 180) then 3 when (b.MinutesBilled > 180 and b.MinutesBilled <= 240) then 4 else 5 end as rango,
		case (SELECT top 1 ag.Id_Agreement from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied ag where ag.Id_Billing = b.Id_Billing order by ag.Id_AgreementsApplied desc)
			when 1 then case when (b.TotalAdjustments = 0) then 'Cinemark' else 'N/A Convenio' end
			when 2 then case when (b.TotalAdjustments = 0) then 'Jumbo' else 'N/A Convenio' end
			when 3 then case when (b.TotalAdjustments = 0) then 'Jumbo' else 'N/A Convenio' end
			when 4 then case when (b.TotalAdjustments = 0) then 'Empleados y Domiciliarios' else 'N/A Convenio' end
			when 7 then case when (b.TotalAdjustments = 0) then 'Empleados Jumbo' else 'N/A Convenio' end  
		end as concesiones,
		'Si' as convenio,
		b.IdInvoice,
		b.IdToken
		--, b.InvoiceDate, b.Receipt, tv.Value, b.Total, b.TotalAgreements, b.TotalAdjustments, tz.Name, b.IdInvoice, b.LastInsert, b.IdToken, (SELECT top 1 ag.Id_Agreement from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied ag where ag.Id_Billing = b.Id_Billing order by ag.Id_AgreementsApplied desc) as Id_Agreement
		from CI_ControlAccessDb_New.dbo.Tb_Billing b WITH (NOLOCK)
		left join CI_ControlAccessDb_New.dbo.Tb_Zone tz WITH (NOLOCK) on tz.Id_Zone = b.Id_Zone
		left join CI_ControlAccessDb_New.dbo.Tb_Transaction t WITH (NOLOCK) on t.Id_Transaction = b.Id_Transaction
		left join CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv WITH (NOLOCK) on tv.Id_Transaction = t.Id_TransactionParent 
		and tv.Id_TransactionValue = 
		(
			SELECT max(tv_temp.Id_TransactionValue)
			from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv_temp WITH (NOLOCK)
			where tv_temp.Id_Transaction = tv.Id_Transaction and tv_temp.ValueName = 'plate'
		)
		where YEAR(b.InvoiceDate) = 2025
		and b.Prefix <> 'OAV1' --Se omiten registros de pruebas
		and b.TotalAdjustments = 0
		and b.Total = 0
		and b.TotalAgreements in (0,3300,4500) --tal vez toque agregar xa q cuadre con el excel
	) as parking
	where parking.concesiones is not null 
	and parking.tipos_vehiculo is not null
--) as debug
--where 1=1
--and debug.IdInvoice in (159011) --quitar
--and MONTH(debug.fecha_ingreso) in (12)
--and DAY(debug.fecha_ingreso) = 5	
--and debug.tipos_vehiculo = 'Carros' --'Carros' 'Motos' 'Bicicletas' 'Tarjeta Perdida' --quitar
--and debug.concesiones = 'Cinemark' --'Cinemark', 'Jumbo', 'Empleados Jumbo', 'Empleados y Domiciliarios' 'N/A Convenio'
--and debug.IdToken = '252187063'
--and debug.cantidad in (931733869305,931733856131) --quitar
--and debug.pernoctado = 1
--GROUP by MONTH(debug.fecha_ingreso), DAY(debug.fecha_ingreso), debug.tipos_vehiculo, debug.convenio, debug.concesiones order by 2
--order by debug.IdInvoice
--order by DAY(debug.fecha_ingreso), debug.concesiones, debug.IdToken
;







SELECT 39+175+25;

--'803403668' >> 931733869305 (idbill)
--'806753588' >> 931733856131 (idbill)


SELECT *
from CI_ControlAccessDb_New.dbo.Tb_Billing b
where b.IdToken = '806753588'
and MONTH(b.InvoiceDate) = 12
and DAY(b.InvoiceDate) = 10
and b.Id_Billing in (931733856131)
; 


SELECT ag.* 
from CI_ControlAccessDb_New.dbo.Tb_AgreementsApplied ag 
where ag.Id_Billing = 931733856131 
order by ag.Id_AgreementsApplied desc
;
