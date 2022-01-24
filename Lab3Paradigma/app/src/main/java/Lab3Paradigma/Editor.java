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
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo visualize y auxiliares
    //////////////////////////////////////////////////////////////////////
    public ArrayList<Documento> filtrar(Usuario user){
        ArrayList<Documento> listaFiltrada = new ArrayList<>();
        ArrayList<Documento> lista = this.documentos;
        int n = lista.size();
        
        for(int i=0;i<n;i++){
            if(lista.get(i).getAutor().equals(user.getUsername()) 
                    || user.tienePermisos(user.getUsername(),lista.get(i))){
                listaFiltrada.add(lista.get(i));
            }
        }
        return listaFiltrada;
    }
    
    public String accessToString(Permiso p){
        return "   Aqui va los permisos";
    }
    
    public String versionToString(ArrayList<Version> h){
        if(h.isEmpty()){
            return "  Esta lista no tiene informacion.\n";
        }
        
        int  n = h.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            a = i + ".- Contenido: " + h.get(i).getContenidoVersion();
            a = a + ". Modificacion hecha por: " + h.get(i).getUsuarioModificador();
            a = a + ". Fecha modificacion: "+ "Aqui va la fecha";
        }
        return a;
    }
    
    public String documentsToString(ArrayList<Documento> d){
        if(d.isEmpty()){
            return "  Esta lista no tiene informacion.\n";
        }
        
        int n = d.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            int id = i + 1;
            a = id + ".- Autor: "+d.get(i).getAutor()+". Nombre del documento: ";
            a = a + d.get(i).getNombreDocumento()+". Contenido del documento: ";
            a = a + d.get(i).getContenidoDocumento()+".\n\n   Permisos:";
            a = a + accessToString(d.get(i).getAccesos())+"\n\n   Historial: ";
            a = a + versionToString(d.get(i).getHistorial())+"\n\n";
        }
        return a;
    }
    
    public String EditorToString(){
        String s;
        if(conectado()){
            Usuario u = this.usuarios.get(this.sesionActiva-1);
            ArrayList<Documento> df = filtrar(u);
            
            s = "Bienvenido a la plataforma " + this.nombre + ", ";
            s = s + u.getUsername() + ". Los documentos en que tiene permisos";
            s = s + "son:\n\n" + documentsToString(df) + "\n\n Datos usuario ";
            s = s + "conectado: " + u.getUsername() +", Aqui va la fecha.";
            s = s + "Plataforma creada por Leo Vergara Sepulveda.";
        }else{
            s = "Bienvenido a la plataforma " + this.nombre + ". Los";
            s = s + " documentos presente en la plataforma son:\n\n";
            s = s + documentsToString(this.documentos) + "\n\n";
            s = s + "Plataforma creada por Leo Vergara Sepulveda.";
        }
        return s;
    }
    
    public void PrintEditor(String s){
        System.out.println(s);
    }
    public void visualize(){
        String s = EditorToString();
        PrintEditor(s);
    }
}
