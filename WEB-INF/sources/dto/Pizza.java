package dto;

import java.util.List;

/**
 * Représente une pizza.
 */
public class Pizza {
    
    private final String NAME;
    private String pate;
    private double prixBase;
    private List<Ingredient> ingredients;

    /**
     * Constructeur de la classe Pizza.
     *
     * @param nAME       Le nom de la pizza.
     * @param pate       Le type de pâte de la pizza.
     * @param prixBase   Le prix de base de la pizza.
     * @param ingredients La liste des ingrédients de la pizza.
     */
    public Pizza(String nAME, String pate, double prixBase, List<Ingredient> ingredients) {
        NAME = nAME;
        this.pate = pate;
        this.prixBase = prixBase;
        this.ingredients = ingredients;
    }

    /**
     * Retourne le nom de la pizza.
     *
     * @return Le nom de la pizza.
     */
    public String getNAME() {
        return NAME;
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