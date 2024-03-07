package dao;

import java.util.Collection;
import dto.Pizza;

/**
 * Interface pour le DAO (Data Access Object) des pizzas.
 * Définit les opérations de base pour accéder aux données des pizzas.
 * 
 * @author samy.vancalster.etu@univ-lille.fr
 */
public interface DAOPizza {

    /**
     * Récupère toutes les pizzas.
     *
     * @return Une liste contenant toutes les pizzas.
     */
    public Collection<Pizza> findAll();

    /**
     * Récupère une pizza par son identifiant.
     *
     * @param id L'identifiant de la pizza.
     * @return La pizza correspondant à l'identifiant, ou null si elle n'est pas trouvée.
     */
    public Pizza findById(int id);

    /**
     * Enregistre une nouvelle pizza.
     *
     * @param pizza La pizza à enregistrer.
     * @return La validation de l'enregistrement.
     */
    public boolean save(Pizza pizza);

    /**
     * Supprime une pizza.
     *
     * @param ingredient La pizza à supprimer.
     */
    public boolean delete(Pizza pizza);

    /**
     * Met a jour une pizza.
     *
     * @param ingredient La pizza à mettre a jour.
     */
    public void update(Pizza pizza);

    /**
     * Ferme la ressource associée à ce DAO.
     */ 
    public void close();    
}
