/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;
/**
 *
 * @author leoiv
 */
public class Version {
    // Atributos
    private int id;
    private String contenido;
    private String usuario;
    private Date fechaModificacion;
    
    // Constructor
    public Version(int i,String c,String u,Date fM){
        this.id = i;
        this.contenido = c;
        this.usuario = u;
        this.fechaModificacion = fM;
    }
    
    // Getters y setters
    public String getContenidoVersion(){return this.contenido;}
}