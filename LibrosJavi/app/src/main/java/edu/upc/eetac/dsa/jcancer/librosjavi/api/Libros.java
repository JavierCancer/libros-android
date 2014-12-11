package edu.upc.eetac.dsa.jcancer.librosjavi.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrador on 10/12/2014.
 */
public class Libros {

    private int libroid;
    private String title;
    private int idautor;
    private String autor;
    private String language;
    private String edition;
    private long dateCreation;
    private long dateImpresion;
    private String Editorial;
    private String username;
    private String subject;
    private String content;

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    private String ETag;
    private Map<String,Link> links = new HashMap<String, Link>();

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
    public int getLibroid() {
        return libroid;
    }
    public void setLibroid(int libroid) {
        this.libroid = libroid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getIdautor() {
        return idautor;
    }
    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getEdition() {
        return edition;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }
    public long getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }
    public long getDateImpresion() {
        return dateImpresion;
    }
    public void setDateImpresion(long dateImpresion) {
        this.dateImpresion = dateImpresion;
    }
    public String getEditorial() {
        return Editorial;
    }
    public void setEditorial(String editorial) {
        Editorial = editorial;
    }
    public Map<String, Link> getLinks() {
        return links;
    }


}