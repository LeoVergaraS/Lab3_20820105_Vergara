/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

import java.util.ArrayList;

/**
 * Una clase que representa un Documento en la plataforma colaborativa.
 * Un documento queda determinado por el id del documento, el nombre del
 * documento,el autor del documento, fecha de creacion del docmento, lista de
 * versiones, lista de permisos y lista de comentarios.
 * @author leoiv
 */
public class Documento {
    // Atributos
    private int id;
    private String nombre;
    private String contenido;
    private String autor;
    private String fechaCreacion;
    private ArrayList<Version> historial;
    private ArrayList<Permiso> accesos;
    private ArrayList<Comentario> comentarios;
    
    /*Crea un Documento en el editor,
    @param id = id del documento, mayor o igual a 1,
    @param n = nombre del documento, string
    @param c = contenido del documento, string
    @param a = autor del documento, string
    @param fC fecha de creacion del documento, stirng.
    */
    public Documento(int i,String n,String c,String a,String fC){
        this.id = i;
        this.nombre = n;
        this.contenido = c;
        this.autor = a;
        this.fechaCreacion = fC;
        this.historial = new ArrayList<>();
        this.accesos = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }
    
    /*Metodos que retorna un elemento del comentario, getters.*/
    public int getId(){return this.id;}
    public String getNombreDocumento(){return this.nombre;}
    public String getContenidoDocumento(){return this.contenido;}
    public String getAutor(){return this.autor;}
    public String fechaCreacionDocumento(){return this.fechaCreacion;}
    public ArrayList<Permiso> getAccesos(){return this.accesos;}
    public ArrayList<Version> getHistorial(){return this.historial;}
    public ArrayList<Comentario> getComentarios(){return this.comentarios;}
    
    /*Metodos que establecen algun dato, setters.*/
    public void setContenido(String contenidoNuevo){
        this.contenido = contenidoNuevo;
    }
    public void setHistorial(ArrayList<Version> HistorialNuevo){
        this.historial = HistorialNuevo;
    }
    public void setAccesos(ArrayList<Permiso> AccesosNuevos){
        this.accesos = AccesosNuevos;
    }
    public void setComentarios(ArrayList<Comentario> nuevosC){
        this.comentarios = nuevosC;
    }
    
    /*Metodo que imprime los datos de un documento.*/
    public void imprimirDocumento(){
        String a = this.id + ".- Nombre documento: "+this.nombre+". Contenido: ";
        a = a + this.contenido + ". Autor: "+this.autor+"\n";
        System.out.println(a);
    }
}
