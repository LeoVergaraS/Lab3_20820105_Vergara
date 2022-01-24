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
public class Usuario {
    // Atributos
    private int id;
    private String username;
    private String password;
    private Date fechaRegistro;
       
    // Constructor
    public Usuario(int i, String u, String p){
        this.id = i;
        this.username = u;
        this.password = p;
    }
    
    // getters y setters
    public int getIdUsuario(){return this.id;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo create y auxiliares
    //////////////////////////////////////////////////////////////////////
    
    public ArrayList<Documento> agregarDocumento(ArrayList<Documento> ld, Documento d){
        /*Se crea una nueva lista,*/
        ArrayList<Documento> nld = new ArrayList<>();
        /*Se copia toda la lista en la nueva*/
        for(int i=0;i<ld.size();i++){
            nld.add(ld.get(i));
        }
        /*Se agrega al final el nuevo documento*/
        nld.add(d);
        return nld;
    }
    
    public void create(Editor docs,String nombre,String contenido){
        /*Si hay un usuario conectado, se crea el archivo*/
        if(docs.conectado()){
            /*el id del usuario esta en la sesion activa.*/
            int id = docs.getSesionActiva()-1;
            /*se toma el usuario con ese id.*/
            String autor = docs.getListaUsuarios().get(id).username;
             
            /*el id del documento es el ultimo de la lista de documento.*/
            int idDoc = docs.getListaDocumentos().size()+1;
            /*Se crea el nuevo documento.*/
            Documento d = new Documento(idDoc,nombre,contenido,autor,new Date(12,20,2021));
            /*Se agrega el documento a la lista de documento del editor.*/
            docs.setListaDocumentos(agregarDocumento(docs.getListaDocumentos(),d));
        }else{
            System.out.println("No hay un usuario conectado.");
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo share y auxiliares
    //////////////////////////////////////////////////////////////////////
    public ArrayList<Documento> actualizarListaDocumentos(ArrayList<Documento> l,Documento d, int idDoc){
        ArrayList<Documento> nld = new ArrayList<>();
        for(int i=0;i<l.size();i++){
            if(i == idDoc){
                nld.add(d);
            }else{
                nld.add(l.get(i));
            }
        }
        return nld;
    }
    
    public void share(Editor docs,ArrayList<String> up,ArrayList<String> po, int idDoc){
        /*Se verifica si hay un usuario conectado,*/
        if(docs.conectado()){
            /*Si es asi se verifica si el id entregado no sobre pase
            los limites.*/
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /* Se verifica si el usuario conectado es propietario
                del documento.*/
                int id = docs.getSesionActiva()-1;
                String propietario = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(propietario)){
                    /* Se comparte el documento */
                    Permiso p = new Permiso(up,po);
                    d.setAccesos(p);
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                }else{
                    System.out.println("No eres propietario del documento.");
                }
            }else{
                System.out.println("No existe ese documento.");
            }
        }else{
            System.out.println("No hay un usuario conectado.");
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo add y auxiliares
    //////////////////////////////////////////////////////////////////////
    public boolean tienePermisosDe(String usuario,Documento d,String permiso){
        int i,j;
        
        ArrayList<String> up = d.getAccesos().getUsuariosPermitidos();
        ArrayList<String> po = d.getAccesos().getPermisosOtorgados();
        
        /*Se verifica si el usuario esta en los permisos del documento*/
        for(i=0;i<up.size();i++){
            /*Si se encuentra al usuario, se ve si el permiso coincide con el
            entregado */
            if(up.get(i).equals(usuario)){
                for(j=0;j<po.size();j++){
                    /*Si se encuentra el permiso, se retorna true*/
                    if(po.get(j).equals(permiso)){
                        return true;
                    }
                }
                /*Esta el usuario pero no coincide el permiso*/
                return false;
            }
        }
        /*No esta el usuario*/
        return false;
    }
    
    public void add(Editor docs,int idDoc,String textAdd){
        /* Se verifica si hay un usuario conectado */
        if(docs.conectado()){
            /* Si es asi se verifica si el id entregado no sobre pase
            los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario
                o tiene permisos de esctritura en el documento.*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u) || tienePermisosDe(u,d,"W")){
                    /*Se crea el nuevo contenido del documento y se agrega al
                    documento*/
                    String cn = d.getContenidoDocumento() + textAdd;
                    d.setContenido(cn);
                    
                    /*Se actualiza la lista de documentos*/
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                }else{
                    System.out.println("No puedes acceder a este archivo.");
                }
            }else{
                System.out.println("No existe ese documento.");
            }
        }else{
            System.out.println("No hay un usuario conectado.");
        }
    }
    
}
