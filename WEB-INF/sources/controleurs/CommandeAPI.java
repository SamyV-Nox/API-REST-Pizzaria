package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import dao.CommandeDao;
import dao.PizzaDao;
import dto.TOKEN;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandeAPI extends API {
    private static final CommandeDao DAO = new CommandeDao();
    private static final PizzaDao PIZZA_DAO = new PizzaDao();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] parameter = getParametre(req);
        formatResponse(res);

        final int NOMBRE_DE_PARAMTRE = parameter.length;
        if (NOMBRE_DE_PARAMTRE == 0) {
            send(res, getAll(res));
        } else if (1 <= NOMBRE_DE_PARAMTRE) {
            int id = isNumber(parameter[1]);
            if (id == -1)
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            else if (NOMBRE_DE_PARAMTRE == 2)
                send(res, getById(res, id));
            else if (NOMBRE_DE_PARAMTRE == 3) {
                get3parametre(res, parameter, id);
            } else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private List<TOKEN> getAll(HttpServletResponse res) {
        try {
            List<TOKEN> commande = DAO.findAll();
            if (!commande.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_OK);
                return commande;
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ArrayList<>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ArrayList<>();
        }
    }

    private void get3parametre(HttpServletResponse res, String[] parameter, int id) {
        TOKEN ingredient = getById(res, id);
        if (ingredient != null) {
            String attribut = parameter[2];
            if ("id".equals(attribut))
                send(res, ingredient.getId());
            else if ("nom".equals(attribut))
                send(res, ingredient.getName());
            else if ("prixfinal".equals(attribut))
                send(res, ingredient.getFinalPrice());
            else if ("date".equals(attribut))
                send(res, ingredient.getDate());
            else if ("panier".equals(attribut))
                send(res, ingredient.getPanier());
            else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

     private TOKEN getById(HttpServletResponse res, int id) {
        try {
            TOKEN ingredient = DAO.findById(id);
            if (ingredient != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                return ingredient;
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // Lecture du corps de la requête
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining());

            // Conversion du corps de la requête en objet JSON
            JsonNode json = OBJECT_MAPPER.readTree(requestBody);

            // Vérification si le JSON contient tous les attributs requis
            if (json.has("name") && json.has("date") && json.has("panier")) {
                // Extraction des données du JSON
                String name = json.get("name").asText();
                LocalDate date = LocalDate.parse(json.get("date").asText());

                // Extraction des pizzas de la commande
                List<Pizza> orderedPizzas = new ArrayList<>();
                JsonNode pizzasNode = json.get("panier");
                if (pizzasNode.getNodeType() == JsonNodeType.ARRAY) {
                    for (JsonNode pizzaNode : pizzasNode) {
                        int pno = pizzaNode.get("id").asInt(); // Supposons que l'identifiant de la pizza est "id"
                        Pizza pizza = PIZZA_DAO.findById(pno); 
                        if (pizza != null) {
                            orderedPizzas.add(pizza);
                        } else {
                            // Si une pizza n'est pas trouvée, retourner une erreur
                            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            return;
                        }
                    }
                } else {
                    // Si la liste de pizzas n'est pas un tableau JSON, retourner une erreur
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                // Création de la commande
                TOKEN commande = new TOKEN(0, name, date, orderedPizzas);

                // Sauvegarde de la commande dans la base de données
                DAO.saveCommande(commande);

                // Réponse avec le statut 201 (Created)
                res.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                // Si certains attributs sont manquants dans le JSON, retourner une erreur
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            // En cas d'erreur lors de l'accès à la base de données, retourner une erreur 500 (Internal Server Error)
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    
}
