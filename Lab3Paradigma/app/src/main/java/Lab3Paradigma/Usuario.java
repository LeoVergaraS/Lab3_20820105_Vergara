/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab3Paradigma;

import java.util.ArrayList;

/**
 * Una clase que crea un usuario en la plataforma. Un usuario
 * queda determinado por el id, su nombre de usuario, la contrasenia 
 * t la fecha de registro.
 * @author leoiv
 */
public class Usuario {
    // Atributos
    private int id;
    private String username;
    private String password;
    private String fechaRegistro;
       
    /*Crea un usuario nuevo en el editor, pasandole el id, nombre de usuario,
    contrasenia y fecha,
    @param i = id del usuario, mayor o igual 0
    @param u = nombre de usuario
    @param p = contrasenia
    @param fecha de registro*/
    public Usuario(int i, String u, String p,String f){
        this.id = i;
        this.username = u;
        this.password = p;
        this.fechaRegistro = f;
    }
    
    /*Metodo que retorna un dato del editor, getters*/
    public int getIdUsuario(){return this.id;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getFechaRegistro(){return this.fechaRegistro;}
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo create y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Agrega un nuevo documento al final de la lista de documentos del editor,
    @param ld = lista de documentos,
    @param d = documento para agregar,
    @return una lista nueva de documentos.*/
    public ArrayList<Documento> agregarDocumento(ArrayList<Documento> ld, Documento d){
        /*Se crea una nueva lista,*/
        ArrayList<Documento> nld = new ArrayList<>();
        /*Se copia toda la lista en la nueva*/
        nld.addAll(ld);
        /*Se agrega al final el nuevo documento*/
        nld.add(d);
        return nld;
    }
    
    /*Crea un documento en la lista de documentos del editor,
    @param docs = plataforma de documentos,
    @param nombre = nombre del documento nuevo,
    @param contenido = contenido del documento nuevo*/
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
            Fecha fecha = new Fecha();
            Documento d = new Documento(idDoc,nombre,contenido,autor,fecha.obtenerFechaActual());
            /*Se agrega el documento a la lista de documento del editor.*/
            docs.setListaDocumentos(agregarDocumento(docs.getListaDocumentos(),d));
            System.out.println("Se creo correctamente el documento.\n");
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo share y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Cambia el documento entregado en la lista de documentos del 
    editor segun su posicion,
    @param l = lista de documentos para actualizar
    @param d = documento actualizado
    @param idDoc = id del documento
    @return una lista de documentos*/
    public ArrayList<Documento> actualizarListaDocumentos(ArrayList<Documento> l,Documento d, int idDoc){
        ArrayList<Documento> nld = new ArrayList<>();
        nld.addAll(l);
        nld.set(idDoc,d);
        return nld;
    }
    
    /*Verifica si a un usuario ya se le otorgaron permisos,
    @param u = nombre de usuario,
    @param p = lista de permisos
    @return booleano, true si ya esta o falso si no.*/
    public boolean yaEsta(String u, ArrayList<Permiso> p){
        for(int i=0;i<p.size();i++){
            if(p.get(i).getUsuarioPermitido().equals(u)){
                return true;
            }
        }
        return false;
    }
     
    /*Agrega los permisos nuevos otorgados, si habia uno antes para el mismo 
    usuario lo reemplaza, si no, lo mantiene/
    @param up = lista de string con los usuarios permitidos
    @param po = permiso otorgado
    @param docs = plataforma de documentos,
    @return lista de permisos*/
    public ArrayList<Permiso> agregarPermisos(ArrayList<String> up,String po, Documento d,Editor docs){ 
        ArrayList<Permiso> nuevaLista = new ArrayList<>();
        ArrayList<Permiso> lista = d.getAccesos();
       
        Permiso p;
        int m = up.size();
        int i,j;
        
        for(i=0;i<m;i++){
            // Si existe el usuario en la lista de usuarios del editor y
            // no es autor del documento, se agrega ese usaurio con el permiso
            // a la lista de permisos.
            if(docs.esta(up.get(i)) && !d.getAutor().equals(up.get(i))){
                p = new Permiso(up.get(i),po);
                nuevaLista.add(p);
            }
        }
        
        // Si la lista original del documento es vacia se devuelve la
        // nueva lista.
        if(lista.isEmpty()){
            return nuevaLista;
            
        }
        
        for(i=0;i<lista.size();i++){
            String usuario = lista.get(i).getUsuarioPermitido();
            // Se verifica si tiene permisos en la nueva lista de permisoss
            if(!yaEsta(usuario,nuevaLista)){
               nuevaLista.add(lista.get(i));
            }
        }
     
        return nuevaLista;
    }
    
     /*Comparte un documento con otros usuarios,
    @param docs = plataforma de documentos,
    @param up = usuarios a que se le otorgan permisos
    @param po = permiso otorgado,
    @param idDoc = id del documento que se comparte*/
    public void share(Editor docs,ArrayList<String> up,String po, int idDoc){
        /*Se verifica si hay un usuario conectado,*/
        if(docs.conectado()){
            /*Si es asi se verifica si el id entregado no sobre pase
            los limites.*/
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /* Se verifica si el usuario conectado es propietario
                del documento o tiene permiso de escritura*/
                int id = docs.getSesionActiva()-1;
                String usuario = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(usuario) || tienePermisosDe(usuario,d,"W")){
                    /* Se comparte el documento */
                    d.setAccesos(agregarPermisos(up,po,d,docs));
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                    System.out.println("Se compartio correctamente el archivo.\n");
                }else{
                    System.out.println("No tienes permiso para acceder a este documento.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo add y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Verifica que un usuario tenga un permiso en especifico en un documento,
    @param usuario = nombre de usuario
    @param d = documento en que se verifica
    @param permiso = permiso que se busca
    @return true si es que tiene ese permiso, si no false*/
    public boolean tienePermisosDe(String usuario,Documento d,String permiso){
        ArrayList<Permiso> p = d.getAccesos();
        
        /*Se verifica si el usuario esta en los permisos del documento*/
        for(int i=0;i<p.size();i++){
            if(p.get(i).getUsuarioPermitido().equals(usuario) &&
                    p.get(i).getPermisoOtorgado().equals(permiso)){
                return true;
            }
        }
        /*No esta el usuario*/
        return false;
    }
    
    /*agrega al final un nuevo historial,
    @param lv = lista de versiones;
    @param h = nueva version para ser agregada
    @return la nueva lista de versiones*/
    public ArrayList<Version> agregarHistorial(ArrayList<Version> lv,Version h){
        ArrayList<Version> nlv = new ArrayList<>();
        // Se copia todo
        nlv.addAll(lv);
        // se agrega al final la nueva version
        nlv.add(h);
        return nlv;
    }
    
    /*agrega contenido al final de un documento,
    @param docs = plataforma de documentos
    @param idDoc = id del documento que se le agregara contenido
    @param textAdd = texto para agregar*/
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
                    Fecha fecha = new Fecha();
                    Version h = new Version(idV,contenido,u,fecha.obtenerFechaActual());
                    d.setHistorial(agregarHistorial(d.getHistorial(),h));
                    
                    /*Se crea el nuevo contenido del documento y se agrega al
                    documento*/
                    String cn = contenido + textAdd;
                    d.setContenido(cn);
                    
                    /*Se actualiza la lista de documentos*/
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                    System.out.println("Se añadio correctamente el texto.\n");
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo rollback y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Restaura una version anterior de un documento,
    @param docs = plataforma de documentos
    @param idDoc = id del documento que se quiere restaurar la version
    @param idV = id de la version que se quiere restaurar*/
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
                        Fecha fecha = new Fecha();
                        Version h = new Version(iDnV,c,u,fecha.obtenerFechaActual());
                        d.setHistorial(agregarHistorial(d.getHistorial(),h));
                        
                        /*Se restaura el contenido de la version*/
                        d.setContenido(d.getHistorial().get(idV).getContenidoVersion());
                        
                        /*se actualiza la lista de documentos*/
                        docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                        System.out.println("Se restauro correctamente la version.\n");
                    }else{
                        System.out.println("No existe esa version del documento.\n");
                    }
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo revokeAccess y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Revoca los accesos otorgados en un documento en especifico,
    @param docs = plataforma de documentos
    @param idDoc = id del documento que se le quiere revocar los accesos*/
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
                    d.setAccesos(new ArrayList<>());
                    
                    /*Se actualiza la lista de documentos*/ 
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                    System.out.println("Se revocaron correctamente los accesos.\n");
                }else{
                    System.out.println("No eres propietario del documento.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo search y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*Verifica si el usuario tiene permisos en el documento,
    de cualquier tipo
    @param u = nombre de usuario
    @param d = documento
    @return true si tiene permisos, false si no*/
    public boolean tienePermisos(String u, Documento d){
        ArrayList<Permiso> permisos = d.getAccesos();
        for(int i=0;i<permisos.size();i++){
            if(permisos.get(i).getUsuarioPermitido().equals(u)){
                return true;
            }
        }
        return false;
    }
    
    /*Filtra la lista de documentos del editor, con el criterio de si es
    propietario del documento o tiene algun permiso y si contiene el texto
    buscado
    @param docs = plataforma de documentos
    @param sT = cadena de texto buscada
    @return lista de documentos con los criterios*/
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
        return new ArrayList<>();
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo delete y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*calcula los caracteres a eliminar en el contenido de un documento,
    @param tamanioContenido = tamanio del contenido del documento
    @param n = n caracteres a eliminar, mayor o igual a 1
    @return 0 si n>tc, si no la resta entre estos 2 valores*/
    public int caracteresAEliminar(int tamanioContenido,int n){
        if(n>=tamanioContenido){
            return 0;
        }else{
            return tamanioContenido-n;
        }
    }
    
    /*Elimina los ultimos n caracteres del contenido de un documento,
    @param docs = plataforma de documentos
    @param idDoc = id del documento al que se le quiere eliminar los caracteres
    @param nC = cantiadad de caracteres a elminar*/
    public void delete(Editor docs,int idDoc,int nC){
         /*Se verifica si hay un usuario conectado*/
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
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
                    Fecha fecha = new Fecha();
                    Version h = new Version(idV,contenido,u,fecha.obtenerFechaActual());
                    d.setHistorial(agregarHistorial(d.getHistorial(),h));
                    
                    /*Se eliminan los ultimos n caracteres del contenido y 
                    se actualiza la lista de documentos*/
                    String cn = contenido.substring(0,caracteresAEliminar(contenido.length(),nC));
                    d.setContenido(cn);
                    
                    /*Se actualiza la lista de documentos*/
                    docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                    System.out.println("Se eliminaron los ultimos caracteres.\n");
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo searchAndReplace y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*busca y reemplaza todas las coincidencias de un texto buscado en el,
    contenido de un documento,
    @param docs = plataforma de documentos
    @param idDoc = id del documento que se quiere buscar y reemplazar
    @param searchText = texto biscado en el contenido del documento.
    @param replaceText = texto de reemplazo*/
    public void searchAndReplace(Editor docs, int idDoc, String searchText, String replaceText){
        /*Se verifica si hay un usuario conectado*/
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario
                o tiene permisos de esctritura en el documento.*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u) || tienePermisosDe(u,d,"W")){
                    /*Se verifica que el documento contenga el texto buscado*/
                    if(d.getContenidoDocumento().contains(searchText)){
                        /*Se agrega el contenido actual al historial*/
                        String contenido = d.getContenidoDocumento();
                        int idV = d.getHistorial().size();
                        Fecha fecha = new Fecha();
                        Version h = new Version(idV,contenido,u,fecha.obtenerFechaActual());
                        d.setHistorial(agregarHistorial(d.getHistorial(),h));
                        
                        /*Se busca el texto y se reemplaza*/
                        String cn = contenido.replaceAll(searchText,replaceText);
                        d.setContenido(cn);
                    
                        /*Se actualiza la lista de documentos*/
                        docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                        System.out.println("Se reemplaron todas las coincidencias.\n");
                    }else{
                        System.out.println("El documento no contiene el texto buscado.\n");
                    }
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo applyStyles y auxiliares
    //////////////////////////////////////////////////////////////////////
    
    /*Aplica todos los estilos en un texto entregado,
    @param texto = texto al que se le aplican los estilos
    @param estilos = estilos que se pueden aplicar, #t, #i o # u
    @return el texto con los estilos aplicados*/
    public String aplicarEstilo(String texto,ArrayList<String> estilos){
        String textoConEstilo = texto;
        for(int i=0;i<estilos.size();i++){
            textoConEstilo=estilos.get(i)+" "+textoConEstilo+" "+estilos.get(i);
        }
        return textoConEstilo;
    }
    
    /*Busca un texto en el contenido de un documento y le aplica los estilos,
    @param docs = plataforma de documentos
    @param idDoc = id del documento que se aplicaran los estilos
    @param estilos = lista de string con los estilos
    @param searchText = texto buscado que se le aplicaran los estilos*/
    public void applyStyles(Editor docs,int idDoc,ArrayList<String> estilos,String searchText){
        /*Se verifica si hay un usuario conectado*/
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario
                o tiene permisos de esctritura en el documento.*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u) || tienePermisosDe(u,d,"W")){
                    /*Se verifica que el documento contenga el texto buscado*/
                    if(d.getContenidoDocumento().contains(searchText)){
                        /*Se agrega el contenido actual al historial*/
                        String contenido = d.getContenidoDocumento();
                        int idV = d.getHistorial().size();
                        Fecha fecha = new Fecha();
                        Version h = new Version(idV,contenido,u,fecha.obtenerFechaActual());
                        d.setHistorial(agregarHistorial(d.getHistorial(),h));
                        
                        /*Se busca el texto y se reemplaza*/
                        String cn = contenido.replaceAll(searchText,aplicarEstilo(searchText,estilos));
                        d.setContenido(cn);
                    
                        /*Se actualiza la lista de documentos*/
                        docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                        System.out.println("Se aplicaron los estilos al texto del documento.\n");
                    }else{
                        System.out.println("El documento no contiene el texto buscado.\n");
                    }
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
    
    //////////////////////////////////////////////////////////////////////
    ///               Metodo comment y auxiliares
    //////////////////////////////////////////////////////////////////////
    /*agrega el comentario al final de una lista de comentarios*/
    public ArrayList<Comentario> agregarComentario(ArrayList<Comentario> lc, Comentario c){
        ArrayList<Comentario> nlc = new ArrayList<>();
        // Se copia toda la lista en la nueva
        nlc.addAll(lc);
        // se agrega al final el nuevo comentario
        nlc.add(c);
        return nlc;
    }
    
    /*agrega un comentario a un documento en especifico,
    @param docs = plataforma de documentos
    @param idDoc = el id del documento que se agregara un comentario
    @param tc = el contenido del comentario,
    @param sc = el texto del documento que se le aplicara un comentario*/
    public void comment(Editor docs,int idDoc,String tc,String sc){
        /*Se verifica si hay un usuario conectado*/
        if(docs.conectado()){
            /* Si es asi se verifica si el id del documento entregado
            no sobre pase los limites */ 
            int n = docs.getListaDocumentos().size();
            if(1<=idDoc && idDoc<= n){
                /*Se verifica si el usuario es propietario
                o tiene permisos de esctritura en el documento.*/
                int id = docs.getSesionActiva()-1;
                String u = docs.getListaUsuarios().get(id).username;
                
                /* El documento */
                Documento d = docs.getListaDocumentos().get(idDoc-1);
                if(d.getAutor().equals(u) || tienePermisosDe(u,d,"W") || tienePermisosDe(u,d,"C")){
                    /*Se verifica que el documento contenga el texto buscado*/
                    if(d.getContenidoDocumento().contains(sc)){
                        /*Se crea el comentario*/
                        int idC = d.getComentarios().size()+1;
                        int idV = d.getHistorial().size();
                        
                        Fecha fecha = new Fecha();
                        Comentario c = new Comentario(idC,fecha.obtenerFechaActual(),u,tc,sc,idV);
                        d.setComentarios(agregarComentario(d.getComentarios(),c));
                        
                        /*Se actualiza la lista de documentos*/
                        docs.setListaDocumentos(actualizarListaDocumentos(docs.getListaDocumentos(),d,idDoc-1));
                        System.out.println("Se comento correctamente el documento.\n");
                        }else{
                            System.out.println("El documento no contiene esa seccion.\n");
                        }
                }else{
                    System.out.println("No puedes acceder a este archivo.\n");
                }
            }else{
                System.out.println("No existe ese documento.\n");
            }
        }else{
            System.out.println("No hay un usuario conectado.\n");
        }
    }
}
