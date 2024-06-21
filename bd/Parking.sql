SELECT getdate();

SELECT DATEADD(month, 0, '20060830');

SELECT DATEADD(month, -1, getdate());

SELECT DATEPART(HOUR, GETDATE()), cast(GETDATE()-6 As Date);
--where cast(vp.[Fecha Factura] As Date) = cast(GETDATE()-6 As Date) --2021-10-23

SELECT DATEADD(yy,DATEDIFF(yy,0,GETDATE()),0);

CREATE UNIQUE CLUSTERED INDEX IDX_V1 ON Sales.vOrders (OrderDate, ProductID);

CREATE UNIQUE CLUSTERED INDEX IDX_View_Qlik_Parking ON CI_ControlAccessDb.dbo.View_Qlik_Parking ([Fecha Factura]);


--2021-11-14 20:10:35
SELECT DATEADD(minute, -15, GETDATE()); 



/********* con el ID_TRANSACTION_PARENT obtengo el ID_TRANSACTION que es el conector con todas las tablas ********/
SELECT *
FROM CI_ControlAccessDb_New.dbo.Tb_Transaction t 
where 1=1
and  t.Id_TransactionParent in ('27-670022689','4-670029962', '21-670029719','2-692532218') -- es el Id_Transaction del anterior query
or t.Id_Transaction in ('27-670022689','4-670029962', '21-670029719','27-692519328')
order by t.TransactionDate desc
;

/********* OBTENIENDO EL ID_TRANSACTION a traves de la PLACA ********/
SELECT tv.*
from CI_ControlAccessDb_New.dbo.Tb_TransactionValues tv
where 1=1
and tv.Id_Transaction in ('27-670022689','4-670029962', '21-670029719','2-692532218')
--and tv.Value = 'HKQ098' --'HKQ098' --1JEV539
--and cast(tv.LastInsert As Date) = DATEADD(month, 0, '20211028') --'2021-10-28'
;



/********* Obteniendo facturacion (billing) con ID_TRANSACTION ********/
SELECT * FROM CI_ControlAccessDb_New.dbo.Tb_Billing
where 1=1
and Id_Transaction in ('27-670022689','4-670029962', '21-670029719','2-692532218')
---and InvoiceDate >= '29/03/2021'
;


SELECT * FROM CI_ControlAccessDb_New.dbo.Tb_BillingItem b
where 1=1
--and b.Id_Billing in (1635049665122)
;


/********* Obteniendo la el punto de pago ********/
SELECT * from CI_ControlAccessDb_New.dbo.Tb_Device td
WHERE td.id device = 19
;

SELECT *
from CI_ControlAccessDb.dbo.Tb_Zone tz
--where tz.Id_Zone = 1
;


--'19-631202324', carro 1-631199693
--'19-670017927', bici rara 26-669983632
--'21-669996735', bici 27-669966794
--'21-670028149', moto 27-670018756
--'19-668289735'  moto 27-668261324 yo la estoy imprimiendo como bici





--FUSION FINAL
SELECT COUNT(1) from (
/*SELECT 
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
	where t_temp.Id_TransactionParent = t.Id_Transaction or (t_temp.Id_Transaction = t.Id_Transaction and t_temp.Id_TransactionParent is not null)
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
else tz.Name end 
as tipos_vehiculo,
case when '' != b.Receipt then TRIM(SUBSTRING(b.Receipt, charindex('Placa', b.Receipt)+6, (charindex('\nEntrada', b.Receipt)-charindex('Placa', b.Receipt))-6)) 
else 
case when tv.Value IN ('1','2') then '' ELSE tv.Value end end 
as vehiculo,
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
) 
tabla
where 1=1 
and cast(tabla.fecha_ingreso as Date) BETWEEN cast(GETDATE()-315 as Date) and cast(GETDATE()-288 as Date)
;





SELECT cast(GETDATE()-315 as Date), cast(GETDATE()-288 as Date);




/****************************** 	QUERYS QLIK  12 junio 2024     *******************************/

/*SELECT 
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
case when '' != b.Receipt and 0 != charindex('id device', LOWER(b.Receipt)) then
	case when '' != TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7)) then 
		TRIM(SUBSTRING(b.Receipt, charindex('placa', LOWER(b.Receipt))+6, (charindex('id device', LOWER(b.Receipt))-charindex('placa', LOWER(b.Receipt)))-7))
	else
		case when tv.Value IN ('1','2') then 
			'' 
		else 
			tv.Value 
		end 
	end
else 
	case when tv.Value IN ('1','2') then 
		'' 
	else 
		tv.Value 
	end 
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
where YEAR(b.InvoiceDate) = 2024
;




select 
b.Receipt,
charindex('Placa', b.Receipt) as idx_inicio,
charindex('id device', b.Receipt) as idx_fin,
TRIM(SUBSTRING(b.Receipt, charindex('Placa', b.Receipt)+6, (charindex('id device', b.Receipt)-charindex('Placa', b.Receipt))-7)) as placa2
from CI_ControlAccessDb_New.dbo.Tb_Billing b WITH (NOLOCK)
where b.Id_Billing = 221713652855
;



select distinct CAST(b.InvoiceDate AS DATE)
--max(b.InvoiceDate) min_invoiceDate, min(b.InvoiceDate) max_invoiceDate
from CI_ControlAccessDb_Newold.dbo.Tb_Billing b WITH (NOLOCK)
where b.InvoiceDate BETWEEN '2022-01-01 00:00:00.000' and '2022-12-31 00:00:00.000'
order by 1 desc
;



















