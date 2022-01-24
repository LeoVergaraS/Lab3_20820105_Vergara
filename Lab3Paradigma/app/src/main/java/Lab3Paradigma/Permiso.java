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
public class Permiso {
    // Atributos
    private String usuarioPermitido;
    private String permisoOtorgado;
    
    // Constructor
    public Permiso(String uP,String pO){
        this.usuarioPermitido = uP;
        this.permisoOtorgado = pO;
    }
   
    // getters y setters
    public String getUsuarioPermitido(){
        return this.usuarioPermitido;
    }
    public String getPermisoOtorgado(){
        return this.permisoOtorgado;
    }
    
    public void setUsuarioPermitido(String u){
        this.usuarioPermitido = u;
    }
    public void setPermisoOtorgado(String p){
        this.permisoOtorgado = p;
    }
}
