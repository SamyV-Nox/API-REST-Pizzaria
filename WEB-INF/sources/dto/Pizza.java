package dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Représente une pizza.
 */
public class Pizza {
    
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("dough")
    private String dough;

    @JsonProperty("price")
    private double price;

    @JsonProperty("ingredients")
    private List<Ingredient> ingredients;

    /**
     * Constructeur de la classe Pizza.
     *  
     * @param id          L'ID de la pizza.
     * @param name        Le nom de la pizza.
     * @param dough       Le type de pâte de la pizza.
     * @param price       Le prix de base de la pizza.
     * @param ingredients La liste des ingrédients de la pizza.
     */
    public Pizza(int id, String name, String dough, double price, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.dough = dough;
        this.price = price;
        this.ingredients = ingredients;
    }

    /**
     * Constructeur de la classe Pizza.
     * Avec une liste vide d'Ingredients
     *  
     * @param id          L'ID de la pizza.
     * @param name        Le nom de la pizza.
     * @param dough       Le type de pâte de la pizza.
     * @param price       Le prix de base de la pizza.
     */
    public Pizza(int id, String name, String dough, double price) {
        this(id, name, dough, price, new ArrayList<>());
    }

    /**
     * Constructeur par défaut de la classe Pizza.
     * Ce constructeur est requis pour la désérialisation JSON.
     */
    public Pizza() {
    }


    /**
     * Retourne l'ID de la pizza.
     *
     * @return L'ID de la pizza.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le nom de la pizza.
     *
     * @return Le nom de la pizza.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le type de pâte de la pizza.
     *
     * @return Le type de pâte de la pizza.
     */
    public String getDough() {
        return dough;
    }

    /**
     * Retourne le prix de base de la pizza.
     *
     * @return Le prix de base de la pizza.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retourne le prix final de la pizza.
     *
     * @return Le prix final de la pizza.
     */
    public double getFinalPrice() {
        double price = getPrice();
        for (Ingredient ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        return price;
    }

    /**
     * Retourne la liste des ingrédients de la pizza.
     *
     * @return La liste des ingrédients de la pizza.
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    /**
     * Modifie le nom de la pizza.
     *
     * @param name Le nouveau nom de la pizza.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Modifie le type de pâte de la pizza.
     *
     * @param dough Le nouveau type de pâte de la pizza.
     */
    public void setDough(String dough) {
        this.dough = dough;
    }

    /**
     * Modifie le prix de base de la pizza.
     *
     * @param price Le nouveau prix de base de la pizza.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Modifie la liste des ingrédients de la pizza.
     *
     * @param ingredients La nouvelle liste des ingrédients de la pizza.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pizza other = (Pizza) obj;
        if (id != other.id)
            return false;
        return true;
    }    

    @Override
    public String toString() {
        return "{id" + id + ",name=" + name + ",dough=" + dough + ",price=" + price + ",ingredients="
                + ingredients + "}\n";
    }    
}