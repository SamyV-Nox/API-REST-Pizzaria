package dao;

import java.util.Collection;
import dto.Ingredient;

/**
 * Interface pour le DAO (Data Access Object) des ingrédients.
 * Définit les opérations de base pour accéder aux données des ingrédients.
 * @author samy.vancalster@univ-lille.fr
 */
public interface DAOIngredient {

    /**
     * Récupère tous les ingrédients.
     *
     * @return Une liste contenant tous les ingrédients.
     */
    public Collection<Ingredient> findAll();

    /**
     * Récupère un ingrédient par son identifiant.
     *
     * @param id L'identifiant de l'ingrédient.
     * @return L'ingrédient correspondant à l'identifiant, ou null s'il n'est pas trouvé.
     */
    public Ingredient findById(int id);

    /**
     * Enregistre un nouvel ingrédient.
     *
     * @param ingredient L'ingrédient à enregistrer.
     */
    public boolean save(Ingredient ingredient);
    
    /**
     * Supprime un nouvel ingrédient.
     *
     * @param ingredient L'ingrédient à supprimer.
     */
    public boolean delete(Ingredient ingredient);
}
