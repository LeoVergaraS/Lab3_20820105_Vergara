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
    private int sesionActiva;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Documento> documentos;
    
    // Constructor
    public Editor(String n,Date fC){
        this.nombre = n;
        this.fechaCreacion = fC;
        this.sesionActiva = -1;
        this.usuarios = new ArrayList<>();
        this.documentos = new ArrayList<>();
    }
    
    // setters y getters
    public int getSesionActiva(){
        return this.sesionActiva;
    }
    public ArrayList<Usuario> getListaUsuarios(){
        return this.usuarios;
    }
    public ArrayList<Documento> getListaDocumentos(){
        return this.documentos;
    }
    
    public void setListaUsuarios(ArrayList<Usuario> nuevaLista){
        this.usuarios = nuevaLista;
    }
    
    public void setListaDocumentos(ArrayList<Documento> nuevaLista){
        this.documentos = nuevaLista;
    }
    
    
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo authentication y auxiliares
    //////////////////////////////////////////////////////////////////////
    public boolean existe(String u, String p){
        int n = this.usuarios.size();
        for(int i=0;i<n;i++){
            if (this.usuarios.get(i).getUsername().equals(u) 
                    && this.usuarios.get(i).getPassword().equals(p)){
                
                return true;
            }
        }
        return false;
    }
    
    public Usuario buscarUsuario(String u, String p){
        int n = this.usuarios.size();
        for(int i=0;i<n;i++){
            if (this.usuarios.get(i).getUsername().equals(u) 
                    && this.usuarios.get(i).getPassword().equals(p)){
                
                return this.usuarios.get(i);
            }
        }
        return null;
    }
    
    public boolean conectado(){
        return -1 != this.sesionActiva;
    }
    
    // Sin parametros, se desconecta el usuario
    public void authentication(){
        this.sesionActiva = -1;
    }
    
    // Con el username y el password, se conecta el usuario
    public void authentication(String user, String pass){
        if(existe(user,pass) && !conectado()){
            Usuario s = buscarUsuario(user,pass);
            this.sesionActiva = s.getIdUsuario();
        }
    }
    
    // Con el username, el password y fecha, se registra el usuario
    public void authentication(String username, String password, Date fecha){
        if(!existe(username,password)){
            int id = this.usuarios.size()+1;
            Usuario s = new Usuario(id,username,password);
            this.usuarios.add(s);
        }
    }
}
