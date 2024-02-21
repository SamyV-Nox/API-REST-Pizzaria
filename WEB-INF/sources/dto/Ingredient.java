package dto;

public class Ingredient {
    private int id;
    private String name;
    private int prix;
    private static final int DEFAULT_PRICE = 0; 

    public Ingredient(int id, String name, int prix) {
        this.id = id;
        this.name = name;
        this.prix = prix;
    }

    public Ingredient(int id, String name) {
        this(id, name, DEFAULT_PRICE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}