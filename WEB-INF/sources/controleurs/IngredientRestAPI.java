package controleurs;

import java.io.IOException;
import java.util.Collection;

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
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getAllIngredients(req, res);
        } else {
            String[] pathParts = pathInfo.split("/");
            System.out.println(pathInfo + " " + pathParts[1]);
            if (pathParts.length > 0 ) {
                int id;
                try {
                    id = Integer.parseInt(pathParts[1]);
                } catch (NumberFormatException e) {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                if (pathParts.length == 3 && pathParts[2].equals("name")) {
                    getIngredientName(id, res);
                } else {
                    getIngredientById(id, res);
                }
                
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println("ok");
        if (pathInfo != null && pathInfo.equals("/")) {
            addIngredient(req, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && isValidId(pathInfo)) {
            int id = Integer.parseInt(pathInfo.substring(1));
            deleteIngredient(id, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAllIngredients(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Collection<Ingredient> ingredients = ingredientDAO.findAll();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(objectMapper.writeValueAsString(ingredients));
        } catch (JsonProcessingException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getIngredientById(int id, HttpServletResponse res) throws IOException {
        Ingredient ingredient = ingredientDAO.findById(id);
        if (ingredient != null) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(objectMapper.writeValueAsString(ingredient));
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getIngredientName(int id, HttpServletResponse res) throws IOException {
        Ingredient ingredient = ingredientDAO.findById(id);
        if (ingredient != null) {
            res.setContentType("text/plain");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(ingredient.getName());
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void addIngredient(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String requestBody = req.getReader().lines().reduce("", String::concat);
        Ingredient ingredient = objectMapper.readValue(requestBody, Ingredient.class);
        Ingredient existingIngredient = ingredientDAO.findById(ingredient.getId());
        if (existingIngredient != null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            ingredientDAO.save(ingredient);
            res.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    private void deleteIngredient(int id, HttpServletResponse res) throws IOException {
        Ingredient ingredient = ingredientDAO.findById(id);
        if (ingredient != null) {
            ingredientDAO.delete(ingredient);
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isValidId(String pathInfo) {
        String idString = pathInfo.substring(1);
        try {
            int id = Integer.parseInt(idString);
            return id >= 0; 
        } catch (NumberFormatException e) {
            return false; 
        }
    }
}