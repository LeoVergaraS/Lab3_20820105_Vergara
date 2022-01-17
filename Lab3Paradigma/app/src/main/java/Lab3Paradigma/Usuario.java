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
    private ArrayList<Documento> documentos;
       
    // Constructor
    public Usuario(int i, String u, String p){
        this.id = i;
        this.username = u;
        this.password = p;
        this.documentos = new ArrayList<>();
    }
    
    // getters y setters
    public int getIdUsuario(){return this.id;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public ArrayList<Documento> getListaDocumentos(){return this.documentos;}
    
    public void setListaDocumentos(ArrayList<Documento> listaNueva){
        this.documentos = listaNueva;
    }
}
