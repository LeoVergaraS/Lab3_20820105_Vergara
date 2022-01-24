/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;
/**
 * Una clase que representa una version del texto de un documento.
 * Una Version queda determinado por el id de la version, el contenido de la
 * version, el usuario que modifico el texto del documento y la fecha de 
 * modificacion;
 * @author leoiv
 */
public class Version {
    // Atributos
    private int id;
    private String contenido;
    private String usuario;
    private String fechaModificacion;
    
    /*Crea una version del documento,
    @param i = id de la version, mayor o igual a 0
    @param c = contenido del documento, string.
    @param u = usuario quie modifico la version
    @param fM = fecha de modificacion.*/
    public Version(int i,String c,String u,String fM){
        this.id = i;
        this.contenido = c;
        this.usuario = u;
        this.fechaModificacion = fM;
    }
    
     /*Metodos que retorna un elemento del comentario, getters.*/
    public String getContenidoVersion(){return this.contenido;}
    public String getUsuarioModificador(){return this.usuario;}
    public String getFechaModificacion(){return this.fechaModificacion;}
}
