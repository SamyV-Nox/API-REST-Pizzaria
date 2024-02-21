package dao;

import java.util.List;

import dto.Ingredient;

public interface DAOIngredient {

    public List<Ingredient> findAll();

    public Ingredient findById(int id);

    public void save(Ingredient ingredient);
    
}
