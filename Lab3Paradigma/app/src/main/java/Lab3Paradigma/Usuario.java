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
        nld.addAll(ld);
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
        nld.addAll(l);
        nld.set(idDoc,d);
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
    
    public ArrayList<Version> agregarHistorial(ArrayList<Version> lv,Version h){
        ArrayList<Version> nlv = new ArrayList<>();
        nlv.addAll(lv);
        nlv.add(h);
        return nlv;
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
                    /*Se agrega el contenido actual al historial*/
                    String contenido = d.getContenidoDocumento();
                    int idV = d.getHistorial().size();
                    Version h = new Version(idV,contenido,u,new Date(12,12,12));
                    d.setHistorial(agregarHistorial(d.getHistorial(),h));
                    
                    /*Se crea el nuevo contenido del documento y se agrega al
                    documento*/
                    String cn = contenido + textAdd;
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
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo rollback y auxiliares
    //////////////////////////////////////////////////////////////////////
    public void rollback(Editor docs,int idDoc, int idV){
        /* Se verifica si hay un usuario conectado */
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u)){
                    /*Se verifica que la version exista*/
                    int m = d.getHistorial().size();
                    if(0<=idV && idV<=m){
                        /*Se agrega el contenido actual al historial*/
                        String c = d.getContenidoDocumento();
                        int iDnV = d.getHistorial().size();
                        Version h = new Version(iDnV,c,u,new Date(12,12,12));
                        d.setHistorial(agregarHistorial(d.getHistorial(),h));
                        
                        /*Se restaura el contenido de la version*/
                        d.setContenido(d.getHistorial().get(idV).getContenidoVersion());
                        
                        /*se actualiza la lista de documentos*/
                        docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                    }else{
                        System.out.println("No existe esa version del documento");
                    }
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
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo revokeAccess y auxiliares
    //////////////////////////////////////////////////////////////////////
    
    public void revokeAccess(Editor docs,int idDoc){
        /* Se verifica si hay un usuario conectado */
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u)){
                    /*se revocan los accesos*/
                    d.setAccesos(null);
                    
                    /*Se actualiza la lista de documentos*/ 
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
    ///               Metodo search y auxiliares
    //////////////////////////////////////////////////////////////////////
    public boolean tienePermisos(String u, Documento d){
        ArrayList<String> up = d.getAccesos().getUsuariosPermitidos();
        for(int i=0;i<up.size();i++){
            if(up.get(i).equals(u)){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Documento> search(Editor docs,String sT){
        /*Se verifica si hay un usuario conectado*/
        if(docs.conectado()){
            /*Usuario conectado*/
            int id = docs.getSesionActiva()-1;
            String u = docs.getListaUsuarios().get(id).username;
            
            /*lista filtrada y lista para filtrar*/
            ArrayList<Documento> listaFiltrada = new ArrayList<>();
            ArrayList<Documento> lista = docs.getListaDocumentos();
            
            /*Se filtra la lista de documentos*/
            int n = lista.size();
            for(int i = 0;i<n;i++){
                /*Se verifica si es propietario o tiene permisos
                en el documento*/
                if(lista.get(i).getAutor().equals(u) || tienePermisos(u,lista.get(i))){
                    /*se verifica si el contenido del texto contiene
                    el texto buscado*/
                    if(lista.get(i).getContenidoDocumento().contains(sT)){
                        listaFiltrada.add(lista.get(i));
                    }
                }
            }
            return listaFiltrada;
        }else{
            System.out.println("No hay un usuario conectado.");
        }
        return null;
    }
}
