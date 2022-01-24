/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

import java.util.ArrayList;

/**
 * Una clase que representa un permiso de la lista de permisos de un 
 * documento. Un permiso queda determinado por el usuario al que se le quiere
 * dar permiso y el permiso otorgado.
 * @author leoiv
 */
public class Permiso {
    // Atributos
    private String usuarioPermitido; // Usuario permitido
    private String permisoOtorgado; // permido otorgado al usuario
    
    /*Crea un Permiso con el usuario permitido y el permiso que se le dio
    @param up, String con el nombre del usuario.
    @param op, String con el permiso para el usuario.
    */
    public Permiso(String uP,String pO){
        this.usuarioPermitido = uP;
        this.permisoOtorgado = pO;
    }
   
     /*Metodos que retorna un elemento del comentario, getters.*/
    public String getUsuarioPermitido(){
        return this.usuarioPermitido;
    }
    public String getPermisoOtorgado(){
        return this.permisoOtorgado;
    }
    
     /*Metodos que establecen un elemento del comentario, setters.*/
    public void setUsuarioPermitido(String u){
        this.usuarioPermitido = u;
    }
    public void setPermisoOtorgado(String p){
        this.permisoOtorgado = p;
    }
}
