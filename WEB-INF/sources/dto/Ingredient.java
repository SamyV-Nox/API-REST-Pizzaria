package dto;

/**
 * Représente un ingrédient utilisé dans une pizza.
 */
public class Ingredient {
    private int id;
    private String name;
    private int prix;
    private static final int DEFAULT_PRICE = 0; 

    /**
     * Constructeur de la classe Ingredient.
     *
     * @param id   L'identifiant de l'ingrédient.
     * @param name Le nom de l'ingrédient.
     * @param prix Le prix de l'ingrédient.
     */
    public Ingredient(int id, String name, int prix) {
        this.id = id;
        this.name = name;
        this.prix = prix;
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
    public int getPrix() {
        return prix;
    }

    /**
     * Modifie le prix de l'ingrédient.
     *
     * @param prix Le nouveau prix de l'ingrédient.
     */
    public void setPrix(int prix) {
        this.prix = prix;
    }
}