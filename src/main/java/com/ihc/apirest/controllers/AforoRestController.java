package com.ihc.apirest.controllers;

import java.util.Date;
import java.util.Optional;

import com.ihc.apirest.models.AforoIngreso;
import com.ihc.apirest.models.AforoSalida;
import com.ihc.apirest.models.Parametro;
import com.ihc.apirest.repository.AforoIngresoRepository;
import com.ihc.apirest.repository.AforoSalidaRepository;
import com.ihc.apirest.repository.ParametroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/aforos")
@CrossOrigin("*")
public class AforoRestController 
{
    @Autowired
    AforoIngresoRepository aforoIngresoRepository;

    @Autowired
    AforoSalidaRepository aforoSalidaRepository;

    @Autowired
    ParametroRepository parametroRepository;



    /**
     * Método que permite el aforo maximo configurado
     * @return Aforo Máximo
     */
    @GetMapping(value = "/maximos")
    public ResponseEntity<Integer> getAforoMaximo() 
    {
        //Valor limite por default para el aforo máximo
        Integer parametroAforoMaximo = 4600;

        try
        {
            //Este es el id con el que está configurado el parametro de aforo máximo
            Long parametroAforoMaximoBD = (long)1;

            //Obteniendo el parametro de aforo máximo configurado en BD
            Optional<Parametro> parametro = parametroRepository.findById(parametroAforoMaximoBD);

            if(parametro.isPresent())
            {
                parametroAforoMaximo = Integer.parseInt(parametro.get().getValor());
            }

            return new ResponseEntity<Integer>(parametroAforoMaximo, HttpStatus.OK);
        }
        catch (Exception e) 
        {
            return new ResponseEntity<Integer>(parametroAforoMaximo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Método que permite registra el ingreso de un cliente en BD
     * @return Total clientes ingreso menos total clientes salida
     */
    @PostMapping(value="/ingresos")
    public ResponseEntity<Long> registrarIngreso()
    {
        Long aforoTotalClientes = null;

        try 
        {
            AforoIngreso aforoIngreso = new AforoIngreso();
            aforoIngreso.setFechaIngreso(new Date());
            
            //Registando ingreso de cliente
            aforoIngresoRepository.saveAndFlush(aforoIngreso);

            aforoTotalClientes = aforoTotalClientes();

            return new ResponseEntity<Long>(aforoTotalClientes, HttpStatus.CREATED);
		} 
        catch (Exception e) 
    {
            return new ResponseEntity<Long>(aforoTotalClientes, HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }



    /**
     * Método que permite registra la salida de un cliente en BD
     * @return Total clientes ingreso menos total clientes salida
     */
    @PostMapping(value="/salidas")
    public ResponseEntity<Long> registrarSalida()
    {
        Long aforoTotalClientes = null;

        try 
        {
            AforoSalida aforoSalida = new AforoSalida();
            aforoSalida.setFechaSalida(new Date());

            aforoTotalClientes = aforoTotalClientes();
            aforoTotalClientes -= 1;

            if(aforoTotalClientes >= 0)
            {
                //Registando salida de cliente
                aforoSalidaRepository.saveAndFlush(aforoSalida);
            }
            
            return new ResponseEntity<Long>(aforoTotalClientes, HttpStatus.CREATED);
		} 
        catch (Exception e) 
        {
            return new ResponseEntity<Long>(aforoTotalClientes, HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }



     /**
      * Método que permite calcular el total de aforo de clientes
      * @return
      */
     private Long aforoTotalClientes()
     {
        Long aforoTotalClientes = null;

        try 
        {
            long aforoClientesIngreso = aforoIngresoRepository.aforoClientesIngreso();

            Long aforoClientesSalida = aforoSalidaRepository.aforoClientesSalida();

            if (null == aforoClientesSalida)
            {
                aforoClientesSalida = (long)0;
            }

            aforoTotalClientes = aforoClientesIngreso - aforoClientesSalida.longValue();

            if(null == aforoTotalClientes || 0 > aforoTotalClientes.longValue())
            {
                aforoTotalClientes = (long) 0;
            }
            
            return aforoTotalClientes;
		} 
        catch (Exception e) 
        {
            return (long)0;
		}
     }
}