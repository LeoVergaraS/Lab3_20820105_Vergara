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
    
    // Constructor
    public Editor(String n,Date fC){
        this.nombre = n;
        this.fechaCreacion = fC;
        this.sesionActiva = -1;
        this.usuarios = new ArrayList<>();
    }
    
    // setters y getters
    public int getSesionActiva(){
        return this.sesionActiva;
    }
    public ArrayList<Usuario> getListaUsuarios(){
        return this.usuarios;
    }
    
    public void setListaUsuarios(ArrayList<Usuario> nuevaLista){
        this.usuarios = nuevaLista;
    }
    
    
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo authentication y auxiliares
    //////////////////////////////////////////////////////////////////////
    public static boolean existe(String u, String p, ArrayList<Usuario> l){
        int n = l.size();
        for(int i=0;i<n;i++){
            if (l.get(i).getUsername().equals(u) && l.get(i).getPassword().equals(p)){
                return true;
            }
        }
        return false;
    }
    
    public static Usuario buscarUsuario(String u, String p, ArrayList<Usuario> l){
        int n = l.size();
        for(int i=0;i<n;i++){
            if (l.get(i).getUsername().equals(u) && l.get(i).getPassword().equals(p)){
                return l.get(i);
            }
        }
        return null;
    }
    
    public static boolean conectado(int SA){
        return -1 != SA;
    }
    
    // Sin parametros, se desconecta el usuario
    public void authentication(){
        this.sesionActiva = -1;
    }
    
    // Con el username y el password, se conecta el usuario
    public void authentication(String user, String pass){
        if(existe(user,pass,this.usuarios) && !conectado(this.sesionActiva)){
            Usuario s = buscarUsuario(user,pass,this.usuarios);
            this.sesionActiva = s.getIdUsuario();
        }
    }
    
    // Con el username, el password y fecha, se registra el usuario
    public void authentication(String username, String password, Date fecha){
        if(!existe(username,password,this.usuarios)){
            int id = this.usuarios.size()+1;
            Usuario s = new Usuario(id,username,password);
            this.usuarios.add(s);
        }
    }
}
