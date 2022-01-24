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
    private String fechaCreacion;
    private int sesionActiva;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Documento> documentos;
    
    // Constructor
    public Editor(String n,String fC){
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
    public boolean esta(String u){
        int n = this.usuarios.size();
        for(int i=0;i<n;i++){
            if (this.usuarios.get(i).getUsername().equals(u)){
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
        System.out.println("Se deslogeo correctamente.\n");
    }
    
    // Con el username y el password, se conecta el usuario
    public void authentication(String user, String pass){
        Usuario s = buscarUsuario(user,pass);
        if(s!=null && !conectado()){
            this.sesionActiva = s.getIdUsuario();
            System.out.println("Se logeo correctamente.\n");
        }else{
            System.out.println("No se puedo logear correctamente.\n");
        }
    }
    
    // Con el username, el password y fecha, se registra el usuario
    public void authentication(String username, String password, String fecha){
        if(!esta(username)){
            int id = this.usuarios.size()+1;
            Usuario s = new Usuario(id,username,password,fecha);
            this.usuarios.add(s);
            System.out.println("Se registro correctamente.\n");
        }else{
            System.out.println("Ya existe ese nombre de usuario.\n");
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
    
    public String lecturaPermiso(String permiso){
        if(permiso.equals("W")){
            return "Escritura";
        }else if(permiso.equals("R")){
            return "Lectura";
        }else{
            return "Comentar";
        }
    }
    
    public String accessToString(ArrayList<Permiso> p){
        if(p.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        int n = p.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            String permiso = p.get(i).getPermisoOtorgado();
            String usuario = p.get(i).getUsuarioPermitido();
            a = a +"   "+usuario+" - "+lecturaPermiso(permiso)+"\n";
        }
        return a;
    }
    
    public String versionToString(ArrayList<Version> h){
        if(h.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        
        int  n = h.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            a = a + i + ".- Contenido: " + h.get(i).getContenidoVersion();
            a = a + ". Modificacion hecha por: " + h.get(i).getUsuarioModificador();
            a = a + ". Fecha modificacion: "+ h.get(i).getFechaModificacion()+"\n";
        }
        return a;
    }
    
    public String documentsToString(ArrayList<Documento> d){
        if(d.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        
        int n = d.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            a = a + d.get(i).getId()+".- Autor: "+d.get(i).getAutor()+". Nombre del documento: ";
            a = a + d.get(i).getNombreDocumento()+". Fecha de creacion: ";
            a = a + d.get(i).fechaCreacionDocumento()+". Contenido del documento: ";
            a = a + d.get(i).getContenidoDocumento()+".\n\n   Permisos:\n";
            a = a + accessToString(d.get(i).getAccesos())+"\n\n   Historial:\n";
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
            s = s + " son:\n\n" + documentsToString(df) + "\n\nCuenta ";
            s = s + "conectada: " + u.getUsername() +", creada el "+ u.getFechaRegistro();
            s = s + ". Plataforma creada por Leo Vergara Sepulveda, el "+ this.fechaCreacion;
        }else{
            s = "Bienvenido a la plataforma " + this.nombre + ". Los";
            s = s + " documentos presente en la plataforma son:\n\n";
            s = s + documentsToString(this.documentos) + "\n\n";
            s = s + "Plataforma creada por Leo Vergara Sepulveda, el "+ this.fechaCreacion;
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
