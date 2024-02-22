package controleurs;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOIngredient;
import dao.IngredientDAODatabase;
import dto.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
    private DAOIngredient ingredientDAO;
    private ObjectMapper objectMapper;

    public IngredientRestAPI() {
        ingredientDAO = new IngredientDAODatabase();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String stringId = extractIdFromURI(req.getPathInfo());
        String json;

        if (stringId != null) {
            json = getJsonIngredientByID(stringId, res);
        } else {
            json = getJsonAllIngredients(res);
        }

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);
    }

    private String getJsonAllIngredients(HttpServletResponse res) throws IOException {
        try {
            List<Ingredient> ingredients = ingredientDAO.findAll();
            return objectMapper.writeValueAsString(ingredients);
        } catch (JsonProcessingException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private String getJsonIngredientByID(String stringId, HttpServletResponse res) throws IOException {
        try {
            int id = Integer.parseInt(stringId);
            Ingredient ingredient = ingredientDAO.findById(id);
            if (ingredient != null) {
                return objectMapper.writeValueAsString(ingredient);
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.equals("/ingredients")) {
            String requestBody = req.getReader().lines().reduce("", String::concat);
            Ingredient ingredient = objectMapper.readValue(requestBody, Ingredient.class);
            Ingredient existingIngredient = ingredientDAO.findById(ingredient.getId());
            if (existingIngredient != null) {
                res.getWriter().write("0");
                res.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                ingredientDAO.save(ingredient);
                res.getWriter().write("1");
                res.setStatus(HttpServletResponse.SC_CREATED);
            }
        } else {
            res.getWriter().write("404");
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String extractIdFromURI(String uri) {
        if (uri != null) {
            String[] parts = uri.split("/");
            return parts[parts.length - 1];
        }
        return null;
    }
}