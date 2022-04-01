
package Entities;

public class Table {
    private int id;
    private int restaurant_id;
    private int pos_x;
    private int pos_y;
    private int numero;
    private int nbPlaces;
    public Table() {
    }

    public int getId() {
        return id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Table{" + "id=" + id + ", restaurant_id=" + restaurant_id + ", pos_x=" + pos_x + ", pos_y=" + pos_y + ", numero=" + numero + '}';
    }
    
    
    
}
