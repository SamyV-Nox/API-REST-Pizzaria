package controleurs;

import java.io.IOException;
import java.sql.SQLException;

import dao.CommandeDao;
import dto.Commande;
import dto.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandeAPI extends API {
    private static final CommandeDao DAO = new CommandeDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] parameter = getParametre(req);
        formatResponse(res);

        final int NOMBRE_DE_PARAMTRE = parameter.length;
        if (NOMBRE_DE_PARAMTRE == 0) {
            // send(res, getAll(res));
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

    private void get3parametre(HttpServletResponse res, String[] parameter, int id) {
        Commande ingredient = getById(res, id);
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

     private Commande getById(HttpServletResponse res, int id) {
        try {
            Commande ingredient = DAO.findById(id);
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
}
