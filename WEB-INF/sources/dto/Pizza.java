package dto;

import java.util.List;

public class Pizza {
    
    private final String NAME;
    private String pate;
    private double prixBase;
    private List<Ingredient> ingredients;

    public Pizza(String nAME, String pate, double prixBase, List<Ingredient> ingredients) {
        NAME = nAME;
        this.pate = pate;
        this.prixBase = prixBase;
        this.ingredients = ingredients;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPate() {
        return pate;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setPate(String pate) {
        this.pate = pate;
    }

    public void setPrixBase(double prixBase) {
        this.prixBase = prixBase;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }    
}