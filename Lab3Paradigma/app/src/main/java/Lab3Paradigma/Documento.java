/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

import java.util.ArrayList;

/**
 *
 * @author leoiv
 */
public class Documento {
    // Atributos
    private int id;
    private String nombre;
    private String contenido;
    private String autor;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private ArrayList<Version> historial;
    private Permiso accesos;
    
    // Constructor
    public Documento(int i,String n,String c,String a,Date fC){
        this.id = i;
        this.nombre = n;
        this.contenido = c;
        this.autor = a;
        this.fechaCreacion = fC;
        this.fechaModificacion = fC;
        this.historial = new ArrayList<>();
        this.accesos = null;
    }
    
    // getters y setters
    public int getId(){return this.id;}
    public String getNombreDocumento(){return this.nombre;}
    public String getContenidoDocumento(){return this.contenido;}
    public String getAutor(){return this.autor;}
    public Permiso getAccesos(){return this.accesos;}
    public ArrayList<Version> getHistorial(){return this.historial;}
    
    public void setContenido(String contenidoNuevo){
        this.contenido = contenidoNuevo;
    }
    public void setFechaModificacion(Date fM){
        this.fechaModificacion = fM;
    }
    public void setHistorial(ArrayList<Version> HistorialNuevo){
        this.historial = HistorialNuevo;
    }
    public void setAccesos(Permiso AccesosNuevos){
        this.accesos = AccesosNuevos;
    }
}
