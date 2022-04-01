
package Entities;

import com.codename1.ui.Image;
import java.util.ArrayList;

public class Reservation {
    private int id;
    private int client_id;
    private int restaurant_id;
    private java.sql.Date date;
    private java.sql.Time heure_arrive;
    private java.sql.Time heure_depart;
    private int nb_personne;
    private String statut;
    private ArrayList<Table> tables;

    public Reservation() {
        tables = new ArrayList<Table>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public java.sql.Time getHeure_arrive() {
        return heure_arrive;
    }

    public void setHeure_arrive(java.sql.Time heure_arrive) {
        this.heure_arrive = heure_arrive;
    }

    public java.sql.Time getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(java.sql.Time heure_depart) {
        this.heure_depart = heure_depart;
    }

    public int getNb_personne() {
        return nb_personne;
    }

    public void setNb_personne(int nb_personne) {
        this.nb_personne = nb_personne;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
    
    public void addTable(Table x){
        tables.add(x);
    }
    
     public void removeTable(Table x){
        tables.remove(x);
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", client_id=" + client_id + ", restaurant_id=" + restaurant_id + ", date=" + date + ", heure_arrive=" + heure_arrive + ", heure_depart=" + heure_depart + ", nb_personne=" + nb_personne + ", statut=" + statut + ", tables=" + tables + '}';
    }
    
    
}
