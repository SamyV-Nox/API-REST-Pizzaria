package dao;

import java.util.ArrayList;
import java.util.List;
import dto.Ingredient;

public class IngredientDAOList implements DAOIngredient {
    private List<Ingredient> ingredients;

    public IngredientDAOList() {
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "poivrons", 10));
        ingredients.add(new Ingredient(2, "chorizo", 15));
        ingredients.add(new Ingredient(3, "lardons", 12));
    }

    public List<Ingredient> findAll() {
        return ingredients;
    }

    public Ingredient findById(int id) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == id) {
                return ingredient;
            }
        }
        return null;
    }

    public boolean save(Ingredient ingredient) {
        return ingredients.add(ingredient);
    }

    @Override
    public boolean delete(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }
}