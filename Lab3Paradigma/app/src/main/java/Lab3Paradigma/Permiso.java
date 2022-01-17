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
    private ArrayList<String> usuariosPermitidos;
    private ArrayList<String> permisosOtorgados;
    
    // Constructor
    public Permiso(ArrayList<String> uP,ArrayList<String> pO){
        this.usuariosPermitidos = uP;
        this.permisosOtorgados = pO;
    }
   
    // getters y setters
    public ArrayList<String> getUsuariosPermitidos(){
        return this.usuariosPermitidos;
    }
    public ArrayList<String> getPermisosOtorgados(){
        return this.permisosOtorgados;
    }
    
    public void setUsuariosPermitidos(ArrayList<String> nuevaLista){
        this.usuariosPermitidos = nuevaLista;
    }
    public void setPermisosOtorgados(ArrayList<String> nuevaLista){
        this.permisosOtorgados = nuevaLista;
    }
}
