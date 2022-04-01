package Entities;

import java.sql.Date;
import java.util.ArrayList;

public class Blog {

    private int id;
    private String title;
    private Date date;
    private String Contenu;
    private int author_id;
    private String author_name;
    private String Image;
    private ArrayList<Commentaire> comments;
    private String statut;

    public Blog() {
        comments = new ArrayList<Commentaire>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String Contenu) {
        this.Contenu = Contenu;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void addComment(Commentaire c) {
        comments.add(c);
    }

    public ArrayList<Commentaire> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Commentaire> comments) {
        this.comments = comments;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Blog(int id, String title, Date date, String Contenu, int author_id, String author_name, String Image, ArrayList<Commentaire> comments, String statut) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.Contenu = Contenu;
        this.author_id = author_id;
        this.author_name = author_name;
        this.Image = Image;
        this.comments = comments;
        this.statut = statut;
    }

}
