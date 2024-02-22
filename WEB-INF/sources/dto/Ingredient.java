package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Représente un ingrédient utilisé dans une pizza.
 */
public class Ingredient {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private int price;


    private static final int DEFAULT_PRICE = 0; 

    /**
     * Constructeur par défaut de la classe Ingredient.
     * Ce constructeur est requis pour la désérialisation JSON.
     */
    public Ingredient() {
    }

    /**
     * Constructeur de la classe Ingredient.
     *
     * @param id   L'identifiant de l'ingrédient.
     * @param name Le nom de l'ingrédient.
     * @param price Le prix de l'ingrédient.
     */
    public Ingredient(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Constructeur de la classe Ingredient avec un prix par défaut.
     *
     * @param id   L'identifiant de l'ingrédient.
     * @param name Le nom de l'ingrédient.
     */
    public Ingredient(int id, String name) {
        this(id, name, DEFAULT_PRICE);
    }

    /**
     * Retourne l'identifiant de l'ingrédient.
     *
     * @return L'identifiant de l'ingrédient.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de l'ingrédient.
     *
     * @param id Le nouvel identifiant de l'ingrédient.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de l'ingrédient.
     *
     * @return Le nom de l'ingrédient.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de l'ingrédient.
     *
     * @param name Le nouveau nom de l'ingrédient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le prix de l'ingrédient.
     *
     * @return Le prix de l'ingrédient.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Modifie le prix de l'ingrédient.
     *
     * @param price Le nouveau prix de l'ingrédient.
     */
    public void setPrice(int price) {
        this.price = price;
    }
}