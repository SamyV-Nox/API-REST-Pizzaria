package controleurs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import dao.PizzaDao;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaAPI extends API {

    private static final PizzaDao DAO = new PizzaDao();

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
        Pizza pizza = getById(res, id);
        if (pizza != null) {
            String attribut = parameter[2];
            if ("id".equals(attribut))
                send(res, pizza.getId());
            else if ("nom".equals(attribut))
                send(res, pizza.getName());
            else if ("pate".equals(attribut))
                send(res, pizza.getName());
            else if ("prix".equals(attribut))
                send(res, pizza.getId());
            else if ("ingredients".equals(attribut))
                send(res, pizza.getIngredients());
            else if ("prixFinal".equals(attribut))
                send(res, pizza.getPrice());
            else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private List<Pizza> getAll(HttpServletResponse res) {
        try {
            List<Pizza> pizza = DAO.findAll();
            if (!pizza.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_OK);
                return pizza;
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

    private Pizza getById(HttpServletResponse res, int id) {
        try {
            Pizza pizza = DAO.findById(id);
            if (pizza != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                return pizza;
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) {
        String[] parameter = getParametre(req);

        if (2 == parameter.length) {
            int id = isNumber(parameter[1]);

            if (id != -1) {
                patch(req, res, id);
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    public void patch(HttpServletRequest req, HttpServletResponse res, int id) {
        try {
            Pizza pizza = DAO.findById(id);

            String requestBody = req.getReader().lines().reduce("", String::concat);
            JsonNode json = OBJECT_MAPPER.readTree(requestBody);

            JsonNode jsonName = json.get("name");
            JsonNode jsonPrice = json.get("price");

            String newName = (jsonName != null) ? jsonName.asText() : null;
            Double newPrice = (jsonPrice != null) ? jsonPrice.asDouble() : null;

            if (newName != null)
                pizza.setName(newName);
            if (newPrice != null)
                pizza.setPrice(newPrice);

            int code = DAO.update(pizza);
            if (code == 0)
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            else if (code == -1 || (newName == null && newPrice == null))
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            else
                res.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String requestBody = req.getReader().lines().reduce("", String::concat);
            Pizza ingredient = OBJECT_MAPPER.readValue(requestBody, Pizza.class);
            DAO.save(ingredient);
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        String[] parameter = getParametre(req);
        if (parameter.length == 2) {
            int pno = isNumber(parameter[1]);

            if (pno != -1) {
                delete(res, pno);
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (parameter.length == 3) {

            int pno = isNumber(parameter[1]);
            int ino = isNumber(parameter[2]);

            if (pno != -1 && ino != -1) {
                deleteIngredient(res, pno, ino);
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void deleteIngredient(HttpServletResponse res, int pno, int ino) {
        try {
            int code = DAO.deleteIngredient(pno, ino);
            if (0 < code)
                res.setStatus(HttpServletResponse.SC_OK);
            else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private void delete(HttpServletResponse res, int id) {
        try {
            int code = DAO.delete(id);
            if (0 < code)
                res.setStatus(HttpServletResponse.SC_OK);
            else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}