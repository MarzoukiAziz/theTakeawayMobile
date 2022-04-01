
package Entities;

import java.sql.Date;
import java.util.ArrayList;

public class Commande {
    private int id;
    private int client_id;
    private int restaurant_id;
    private Float total;
    private String statut;
    private Date date;
    private String methode; 
    private ArrayList<CommandeElement> elements;

    public Commande() {
        elements = new  ArrayList<CommandeElement>();
    }
    public void addElement(CommandeElement ce){
        elements.add(ce);
    }
    public ArrayList<CommandeElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<CommandeElement> elements) {
        this.elements = elements;
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", client_id=" + client_id + ", restaurant_id=" + restaurant_id + ", total=" + total + ", statut=" + statut + ", date=" + date + ", methode=" + methode + '}';
    }
    
    
}
