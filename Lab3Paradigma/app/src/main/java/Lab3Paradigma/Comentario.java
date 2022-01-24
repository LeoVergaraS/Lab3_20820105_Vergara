/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

/**
 *
 * @author leoiv
 */
public class Comentario {
    // Atributos
    private int id;
    private String fechaEmision;
    private String autor;
    private String mensaje;
    private String SeccionDelContenido;
    private int versionContenido;
    
    // Constructor
    public Comentario(int i,String fE,String a,String m,String sC,int vC){
        this.id = i;
        this.fechaEmision = fE;
        this.autor = a;
        this.mensaje = m;
        this.SeccionDelContenido = sC;
        this.versionContenido = vC;
    }
}
