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
public class Editor {
    // Atributos
    private String nombre;
    private Date fechaCreacion;
    private ArrayList<String> sesionActiva;
    private ArrayList<Usuario> usuarios;
    
    // Constructor
    public Editor(String n,Date fC){
        this.nombre = n;
        this.fechaCreacion = fC;
        this.sesionActiva = new ArrayList<>(1);
        this.usuarios = new ArrayList<>();
    }
    
    // setters y getters
    public ArrayList<String> getSesionActiva(){
        return this.sesionActiva;
    }
    public ArrayList<Usuario> getListaUsuarios(){
        return this.usuarios;
    }
    
    public void setListaUsuarios(ArrayList<Usuario> nuevaLista){
        this.usuarios = nuevaLista;
    }
}
