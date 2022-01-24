/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

/**
 * Una clase que representa un comentario en la plataforma colaboratica.
 * Cada comentario queda determinado por el id del comentario,
 * la fecha de emision del comentario, el usuario quien emitio el comentario, 
 * el mensaje del comentario, la seccion del texto del documento donde se
 * inserta el comentario y a la version a que esta adjunto el comentario.
 * @author Leo Vergara
 */
public class Comentario {
    // Atributos
    private int id; // id del comentario, mayor o igual a 1
    private String fechaEmision; // fecha de emision
    private String autor; // autor del comentario
    private String mensaje; // mensaje del comentario
    private String SeccionDelContenido; // lugar en el documento.
    private int versionContenido; // version del documento.
    
    /*Crea un comentario a partir de los datos anteriores.
    @param i = id del comentari, debe ser mayor o igual a 0
    @param fE = fecha de emision del comentario, en string
    @param a = autor del comentario
    @param m = mensaje del comentario
    @param sC = Texto del documento donde se pone el comentario
    @param vC = version del contenido al que esta adjunto el comentario*/
    public Comentario(int i,String fE,String a,String m,String sC,int vC){
        this.id = i;
        this.fechaEmision = fE;
        this.autor = a;
        this.mensaje = m;
        this.SeccionDelContenido = sC;
        this.versionContenido = vC;
    }
    
    /*Metodos que retorna un elemento del comentario, getters.*/
    public int getIdComentario(){return this.id;}
    public String getFechaEmision(){return this.fechaEmision;}
    public String getComentador(){return this.autor;}
    public String getMensaje(){return this.mensaje;}
    public String getSeccionDelContenido(){return this.SeccionDelContenido;}
    public int getVersionContenido(){return this.versionContenido;}
    
}
