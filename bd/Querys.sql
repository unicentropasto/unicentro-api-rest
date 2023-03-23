select * from appmall.stores
order by name
;

--delete from appmall.stores;



select * from appmall.promotions p;

--delete from appmall.promotions;



select * 
--emai_cli, count(1)
FROM sisbol.cliente
where 1=1
--and nrod_cli = '13072207'
and emai_cli = 'mario42004@gmail.com'
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
--and url_store_logo like '%1Qqwo1XvIuJYfhbiA85dbRsNES3pGksFJ%'
--and id_store = 4
--and id_category = 4
order by 1--,s."name"
;

update appmall.stores set url_store_logo = 'https://drive.google.com/uc?id=1OgpNidwbdCDZB3d4yGL7oQgyRdsOL3VH' where id_store = 104;

update appmall.stores set url_store_logo = 'https://drive.google.com/uc?export=view&id=1JxQiRNL9nallmYTjiQ0qXJghUa63O8gr', url_store_image = 'https://drive.google.com/uc?export=view&id=1JxQiRNL9nallmYTjiQ0qXJghUa63O8gr';


--https://medium.com/pic-s-curso-básico-de-react-native/flatlist-infinite-scroll-clase-4-b7703a8c8fcb ejemplo de paginacion


--delete from appmall.stores;

select *
from appmall.menu_options_role
;

select *
from appmall.menu_options
;



select *
from appmall.categories c
;


select * from appmall.vw_neighborhoods vn;

INSERT into

--analista.credito@coopcarvajal.com

--Carta para levantamiento de prenda 



select *
from appmall.configuration_images
order by 1
;



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
and b.codi_cli = '222'
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
and b.codi_cli = 33
group by t.desc_tip
;






select * 
from sisbol.cliente c
order by 1
;

select * 
from sisbol.boleta b
where b.codi_cli = '222'
;

select * 
from sisbol.detalle_bol db
where db.codi_bol in (select b.codi_bol from sisbol.boleta b where b.codi_cli = '222')
;

select * 
from sisbol.compra c
where c.codi_bol in (select b.codi_bol from sisbol.boleta b where b.codi_cli = '222')
;


/*
https://www.youtube.com/watch?v=i8bni7mUqXE

import React, { useState, useEffect } from 'react';
import { Platform, Text, View, StyleSheet } from 'react-native';
import Device from 'expo-device';
import * as Location from 'expo-location';

export default function App() {
  const [location, setLocation] = useState(null);
  const [errorMsg, setErrorMsg] = useState(null);

  useEffect(() => 
  {
    // console.log("useEffect Registration");

    const loadData = async () => 
    {
      if (Platform.OS === 'android') {
        console.log(Platform.OS)
      }

      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        setErrorMsg('Permission to access location was denied');
        return;
      }

      let location = await Location.getCurrentPositionAsync({});
      setLocation(location);
      console.log(JSON.stringify(location));
    }
    loadData();
  }, []);

  // useEffect(() => {
  //   (async () => {
  //     if (Platform.OS === 'android' && !Device.isDevice) {
  //       setErrorMsg(
  //         'Oops, this will not work on Snack in an Android Emulator. Try it on your device!'
  //       );
  //       return;
  //     }
      // let { status } = await Location.requestForegroundPermissionsAsync();
      // if (status !== 'granted') {
      //   setErrorMsg('Permission to access location was denied');
      //   return;
      // }

      // let location = await Location.getCurrentPositionAsync({});
      // console.log("entro")
      // setLocation(location);
  //   })();
  // }, []);

  let text = 'Waiting.....';

  if (errorMsg) {
    text = errorMsg;
  } else if (location) {
    console.log("entro..")
    text = JSON.stringify(location);
  }

  return (
    <View style={styles.container}>
      <Text style={styles.paragraph}>{text}</Text>
      <Text style={styles.paragraph}>errorMsg: {errorMsg}</Text>
      
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  paragraph: {
    fontSize: 18,
    textAlign: 'center',
  },
});

*/

select * from hechos.aforo_ingreso order by 1 desc;

select * from hechos.aforo_salida order by 1 desc;

select sum(conteo) from hechos.aforo_ingreso where fecha_ingreso >= CURRENT_DATE;

select sum(conteo) from hechos.aforo_salida where fecha_salida >= CURRENT_DATE;





