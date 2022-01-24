/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

import java.util.ArrayList;

/**
 * Una clase que crea la plataforma colaborativa.
 * Un editor queda determinado por el nombre de la plataforma, la fecha de 
 * creacion, la sesion activa de el, una lista de documentos y
 * una lista de usuarios.
 * @author leoiv
 */
public class Editor {
    // Atributos
    private String nombre;
    private String fechaCreacion;
    private int sesionActiva;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Documento> documentos;
    
    /*Crea un editor colaborativo,
    @param n = nombre del editor
    @param fC = fecha de creacion*/
    public Editor(String n,String fC){
        this.nombre = n;
        this.fechaCreacion = fC;
        this.sesionActiva = -1;
        this.usuarios = new ArrayList<>();
        this.documentos = new ArrayList<>();
    }
    
    /*Metodo que retorna un dato del editor, getters*/
    public int getSesionActiva(){
        return this.sesionActiva;
    }
    public ArrayList<Usuario> getListaUsuarios(){
        return this.usuarios;
    }
    public ArrayList<Documento> getListaDocumentos(){
        return this.documentos;
    }
    
    /*Metodos que establecen un nuevo dato en el editor, setters.*/
    public void setListaUsuarios(ArrayList<Usuario> nuevaLista){
        this.usuarios = nuevaLista;
    }
    public void setListaDocumentos(ArrayList<Documento> nuevaLista){
        this.documentos = nuevaLista;
    }
    
    
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo authentication y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Verifica si el usuario u esta en la lista de usuarios del editor
    @param u = nombre de usuairo
    @return true si esta, false si no esta*/
    public boolean esta(String u){
        int n = this.usuarios.size();
        for(int i=0;i<n;i++){
            if (this.usuarios.get(i).getUsername().equals(u)){
                return true;
            }
        }
        return false;
    }
    
    /*Se extrae el usuario que coincide con el nombre de usuario y la
    contrasenia entregada,
    @param u = nombre de usuario,
    @param p = contrasenia del usuario
    @return si se encuentra Username, si no un null*/
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
    
    /*Verifica si hay un usuario conectado en la sesion acticva del editor
    @return true, si hay un usuario conectado, en caso contrario un false*/
    public boolean conectado(){
        return -1 != this.sesionActiva;
    }
    
    /*Authenticacion logea, registra o deslogea segun la 
    cantidad de variables que entran*/
    
    /*Deslogea al usuario conectado en la sesion activa*/
    public void authentication(){
        this.sesionActiva = -1;
        System.out.println("Se deslogeo correctamente.\n");
    }
    
    /*Logea al usuario en el editor de documetos,
    @param user = nombre de usuario
    @param pass = contrasenia del usuario*/
    public void authentication(String user, String pass){
        Usuario s = buscarUsuario(user,pass);
        if(s!=null && !conectado()){
            this.sesionActiva = s.getIdUsuario();
            System.out.println("Se logeo correctamente.\n");
        }else{
            System.out.println("No se puedo logear correctamente.\n");
        }
    }
    
    /*Registra al usuario en el editor de documentos,
    @param username = nombre de usuario
    @param password = contrasenia
    @param fecha = fecha de registro*/
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
    
    /*Filtra la lista de documentos del editor si el usuario entregado 
    es propietario del documento o tiene permiso en el
    @param user = nombre de usuario
    @return lista de documentos filtrada*/
    public ArrayList<Documento> filtrar(Usuario user){
        ArrayList<Documento> listaFiltrada = new ArrayList<>();
        ArrayList<Documento> lista = this.documentos;
        int n = lista.size();
        
        // se busca en la lista de documentos,
        for(int i=0;i<n;i++){
            // Si es propietario o tiene permisos retorna un true
            if(lista.get(i).getAutor().equals(user.getUsername()) 
                    || user.tienePermisos(user.getUsername(),lista.get(i))){
                listaFiltrada.add(lista.get(i));
            }
        }
        // No se encontro se retorna un false
        return listaFiltrada;
    }
    
    /*Pasa un permiso con el formato W, R o C a Escritura, Lectura o Comentario
    @param permiso = string con W o R o C
    @return string con escritura, lectura o comentario*/ 
    public String lecturaPermiso(String permiso){
        if(permiso.equals("W")){
            return "Escritura";
        }else if(permiso.equals("R")){
            return "Lectura";
        }else{
            return "Comentar";
        }
    }
    
    /*pasa una lista de permisos a string,
    @param p = lista de permisos,
    @return string con el contenido de la lista*/
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
    
    /*Pasa una lista de versiones, o historial, a string,
    @param h = lista de versiones,
    @return string.*/
    public String versionToString(ArrayList<Version> h){
        if(h.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        
        int  n = h.size();
        String a = "";
        
        for(int i=0;i<n;i++){
            a = a+"   " + i + ".- Contenido: " + h.get(i).getContenidoVersion();
            a = a + ". Modificacion hecha por: " + h.get(i).getUsuarioModificador();
            a = a + ". Fecha modificacion: "+ h.get(i).getFechaModificacion()+"\n";
        }
        return a;
    }
    
    /*Pasa una lista de comentarios, a string,
    @param c = lista de comentarios,
    @param d = documento,
    @return un string.*/
    public String commentToString(ArrayList<Comentario> c,Documento d){
        int id = d.getHistorial().size();
        if(c.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        
        int n = c.size();
        String a = "";
        
        for(int i=n-1;i>=0;i--){
            if(c.get(i).getVersionContenido()==id){
                a = a+"   "+c.get(i).getIdComentario()+".- Fecha del comentario: ";
                a = a+c.get(i).getFechaEmision()+". Comentador: "+c.get(i).getComentador();
                a = a+". Comentario: "+c.get(i).getMensaje()+". Seccion del texto: ";
                a = a+c.get(i).getSeccionDelContenido()+"\n\n";
            }else{
                break;
            }
            
        }
        return a;
    }
    
    /*Para una lista de documentos a string,
    @param d = lista de documentos
    @return un string;*/
    public String documentsToString(ArrayList<Documento> d){
        if(d.isEmpty()){
            return "   Esta lista no tiene informacion.\n";
        }
        
        int n = d.size();
        String a = "";
        
        if(conectado()){
            for(int i=0;i<n;i++){
                a = a + d.get(i).getId()+".- Fecha de creacion: ";
                a = a + d.get(i).fechaCreacionDocumento()+". Nombre del documento: ";
                a = a + d.get(i).getNombreDocumento()+". Contenido del documento: ";
                a = a + d.get(i).getContenidoDocumento()+ ". Numero de versiones: ";
                a = a + d.get(i).getHistorial().size()+".\n\n   Permisos:\n"; 
                a = a + accessToString(d.get(i).getAccesos())+"\n\n   Comentarios:\n";
                a = a + commentToString(d.get(i).getComentarios(),d.get(i))+"\n\n";
            }
        }else{
            for(int i=0;i<n;i++){
                a = a + d.get(i).getId()+".- Autor: "+d.get(i).getAutor()+". Nombre del documento: ";
                a = a + d.get(i).getNombreDocumento()+". Fecha de creacion: ";
                a = a + d.get(i).fechaCreacionDocumento()+". Contenido del documento: ";
                a = a + d.get(i).getContenidoDocumento() + ". Numero de versiones: ";
                a = a + d.get(i).getHistorial().size()+".\n\n";
            }
        }
        return a;
    }
    
    /*Pasa una editor a string,
    @param d = lista de documentos
    @return un string;*/
    public String EditorToString(){
        String s;
        if(conectado()){
            Usuario u = this.usuarios.get(this.sesionActiva-1);
            ArrayList<Documento> df = filtrar(u);
            
            s = "Bienvenido a la plataforma " + this.nombre + ", ";
            s = s + u.getUsername() + ". Los documentos en que tiene permisos";
            s = s + "o es propietario";
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
    
    /*Imprime el editor,
    @param s = contenido del editor en string*/
    public void PrintEditor(String s){
        System.out.println(s);
    }
    
    /*Edita e imprime el editor,
    @param s = contenido del editor en string*/
    public void visualize(){
        String s = EditorToString();
        PrintEditor(s);
    }
}
