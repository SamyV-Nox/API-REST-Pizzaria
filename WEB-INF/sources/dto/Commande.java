package dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente une commande de pizzas.
 * 
 * @author samy.vancalster.etu@unvi-lille.fr
 */
public class Commande {

    private int cno;

    private String name;
    
    private LocalDateTime orderDate;

    private List<Pizza> orderedPizzas;

    private static final String DEFAULT_USER_NAME = "NO_NAME";

    /**
     * Constructeur de la classe Commande.
     * 
     * @param id L'identifiant de la commande.
     * @param userName Le nom de l'utilisateur qui a passé la commande.
     * @param orderDate La date et l'heure de la commande.
     * @param orderedPizzas La liste des pizzas commandées.
     */
    public Commande(int id, String userName, LocalDateTime orderDate, List<Pizza> orderedPizzas) {
        this.cno = id;
        this.name = userName;
        this.orderDate = orderDate;
        this.orderedPizzas = orderedPizzas;
    }

    /**
     * Constructeur de la classe Commande.
     * Crée une nouvelle commande avec l'identifiant spécifié, le nom de l'utilisateur, la liste des pizzas commandées,
     * et la date de commande actuelle.
     * 
     * @param id L'identifiant de la commande.
     * @param nomUtilisateur Le nom de l'utilisateur qui a passé la commande.
     * @param pizzasCommandees La liste des pizzas commandées.
     */
    public Commande(int id, String userName, List<Pizza> orderedPizzas) {
        this(id, userName, LocalDateTime.now(), orderedPizzas);
    }

    /**
     * Constructeur de la classe Commande.
     * Crée une nouvelle commande avec l'identifiant spécifié, un nom d'utilisateur par défaut, la liste des pizzas commandées,
     * et la date de commande actuelle.
     * 
     * @param id L'identifiant de la commande.
     * @param pizzasCommandees La liste des pizzas commandées.
     */
    public Commande(int id, List<Pizza> orderedPizzas) {
        this(id, DEFAULT_USER_NAME, orderedPizzas);
    }

    /**
     * Constructeur de la classe Commande.
     * Crée une nouvelle commande avec l'identifiant spécifié, un nom d'utilisateur par défaut, la liste des pizzas commandées,
     * et la date de commande actuelle.
     * 
     * @param id L'identifiant de la commande.
     */
    public Commande(int id) {
        this(id, DEFAULT_USER_NAME, new ArrayList<>());
    }


    /**
     * Retourne l'identifiant de la commande.
     * 
     * @return L'identifiant de la commande.
     */
    public int getId() {
        return cno;
    }

    /**
     * Définit l'identifiant de la commande.
     * 
     * @param id L'identifiant de la commande.
     */
    public void setCno(int id) {
        this.cno = id;
    }

    /**
     * Retourne le nom de l'utilisateur qui a passé la commande.
     * 
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'utilisateur qui a passé la commande.
     * 
     * @param userName Le nom de l'utilisateur.
     */
    public void setName(String userName) {
        this.name = userName;
    }

    /**
     * Retourne la date et l'heure de la commande.
     * 
     * @return La date et l'heure de la commande.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Définit la date et l'heure de la commande.
     * 
     * @param orderDate La date et l'heure de la commande.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Retourne la liste des pizzas commandées.
     * 
     * @return La liste des pizzas commandées.
     */
    public List<Pizza> getOrderedPizzas() {
        return orderedPizzas;
    }

    /**
     * Définit la liste des pizzas commandées.
     * 
     * @param orderedPizzas La liste des pizzas commandées.
     */
    public void setOrderedPizzas(List<Pizza> orderedPizzas) {
        this.orderedPizzas = orderedPizzas;
    }

    /**
     * Calcule le prix total de la commande.
     * 
     * @return Le prix total de la commande.
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Pizza pizza : orderedPizzas) {
            totalPrice += pizza.getFinalPrice();
        }
        return totalPrice;
    }

    /**
     * Retourne une représentation textuelle des détails de la commande.
     * 
     * @return Une chaîne de caractères représentant les détails de la commande.
     */
    @Override
    public String toString() {
        return "Command{" +
                "id=" + cno +
                ", userName='" + name + '\'' +
                ", orderDate=" + orderDate +
                ", orderedPizzas=" + orderedPizzas +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cno;
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
        Commande other = (Commande) obj;
        if (cno != other.cno)
            return false;
        return true;
    }

    
}
