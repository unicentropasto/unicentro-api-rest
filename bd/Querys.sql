select * from appmall.stores
order by name
;

delete from appmall.stores;



select * from appmall.promotions p;

delete from appmall.promotions;


--yyyy-mm-dd
select *
FROM sisbol.cliente
--where nrod_cli = '13072207'
order by 1 
;


https://drive.google.com/file/d/1i1Zg7quDGjDN0uqH8g9tzVxxMkLL_60t/view?usp=share_link
https://drive.google.com/uc?id=1i1Zg7quDGjDN0uqH8g9tzVxxMkLL_60t

select * 
--count(1)
from appmall.stores s
where 1=1
--and s.name like '%GIROS%' 
--or s.name like '%CAF%'
--and s.name like '%POLL%'
--and s.store_number like (select '%'||(STRING_TO_ARRAY(t.desc_tip,' '))[1]||'%' FROM sisbol.tipo t where 1=1 and t.codi_gru = '03' and t.codi_tip = '03002')
order by s."name"
;





--delete from appmall.stores;

select *
from appmall.menu_options_role
;

select *
from appmall.menu_options
;

mailto:publicidadymercadeo@ccunicentropasto.com

, 



select *
from appmall.categories c
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

DATABASE_URL	postgres://mdbsvljjjfhiri:6636d895f0ec9fc782b771f75f6a72ca1568adf7c74efe5f8bdc96b2673db617@ec2-52-5-110-35.compute-1.amazonaws.com:5432/d1nlhajk5a952t
