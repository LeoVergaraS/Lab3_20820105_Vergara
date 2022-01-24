/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Una clase que extrae el valor actual del tiempo.
 * @author leoiv
 */
public class Fecha {
    /*Metodo que pasa la hora y fecha actual del computador a string
    @return la hora y fecha actual en string*/
    public String obtenerFechaActual(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fecha = new Date();
        return df.format(fecha);
    }
}
