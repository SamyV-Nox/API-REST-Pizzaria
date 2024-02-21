package dto;

import java.util.List;

/**
 * Représente une pizza.
 */
public class Pizza {
    
    private final int ID;
    private String name;
    private String pate;
    private double prixBase;
    private List<Ingredient> ingredients;

    /**
     * Constructeur de la classe Pizza.
     *  
     * @param ID         L'ID de la pizza.
     * @param name       Le nom de la pizza.
     * @param pate       Le type de pâte de la pizza.
     * @param prixBase   Le prix de base de la pizza.
     * @param ingredients La liste des ingrédients de la pizza.
     */
    public Pizza(int ID, String name, String pate, double prixBase, List<Ingredient> ingredients) {
        this.ID = ID;
        this.name = name;
        this.pate = pate;
        this.prixBase = prixBase;
        this.ingredients = ingredients;
    }


    /**
     * Retourne l'ID de la pizza.
     *
     * @return L'ID de la pizza.
     */
    public int getID() {
        return ID;
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
    public String getPate() {
        return pate;
    }

    /**
     * Retourne le prix de base de la pizza.
     *
     * @return Le prix de base de la pizza.
     */
    public double getPrixBase() {
        return prixBase;
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
     * @param pate Le nouveau type de pâte de la pizza.
     */
    public void setPate(String pate) {
        this.pate = pate;
    }

    /**
     * Modifie le prix de base de la pizza.
     *
     * @param prixBase Le nouveau prix de base de la pizza.
     */
    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }

    /**
     * Modifie la liste des ingrédients de la pizza.
     *
     * @param ingredients La nouvelle liste des ingrédients de la pizza.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }    
}