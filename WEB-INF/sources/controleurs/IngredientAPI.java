package controleurs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.IngredientDao;
import dto.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ingredients/*")
public class IngredientAPI extends API {

    private static final IngredientDao DAO = new IngredientDao();

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

    private void get3parametre(HttpServletResponse res, String[] parameter, int id) {
        Ingredient ingredient = getById(res, id);
        if (ingredient != null) {
            String attribut = parameter[2];
            if ("name".equals(attribut))
                send(res, ingredient.getName());
            if ("price".equals(attribut))
                send(res, ingredient.getPrice());
            if ("id".equals(attribut))
                send(res, ingredient.getId());
            else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private List<Ingredient> getAll(HttpServletResponse res) {
        try {
            List<Ingredient> ingredients = DAO.findAll();
            if (!ingredients.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_OK);
                return ingredients;
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

    private Ingredient getById(HttpServletResponse res, int id) {
        try {
            Ingredient ingredient = DAO.findById(id);
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
    public void doPatch(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) {
        super.service(req, res);
        DAO.close();
    }
}