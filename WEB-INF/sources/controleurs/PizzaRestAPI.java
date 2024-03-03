package controleurs;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends HttpServlet {
    private DAOPizza pizzaDAO;
    private ObjectMapper objectMapper;

    public PizzaRestAPI() {
        pizzaDAO = new PizzaDAODatabase();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getAllPizzas(req, res);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length > 0) {
                int id;
                try {
                    id = Integer.parseInt(pathParts[1]);
                } catch (NumberFormatException e) {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                if (pathParts.length == 3 && pathParts[2].equals("name")) {
                    getPizzaName(id, res);
                } else if (pathParts.length == 3 && pathParts[2].equals("prixfinal")) {
                    getPizzaFinalPrice(id, res);
                } else {
                    getPizzaById(id, res);
                }

            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/")) {
            addPizza(req, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && isValidId(pathInfo)) {
            int id = Integer.parseInt(pathInfo.substring(1));

            deletePizza(id, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println(req.getMethod());
        super.service(req, res);
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && isValidId(pathInfo)) {
            int id = Integer.parseInt(pathInfo.substring(1));

            Pizza pizza = pizzaDAO.findById(id);
            if (pizza != null) {
                String requestBody = req.getReader().lines().reduce("", String::concat);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                double newPrice = jsonNode.get("price").asDouble();

                pizza.setPrice(newPrice);
                pizzaDAO.update(pizza);

                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write(objectMapper.writeValueAsString(pizza));

                res.setStatus(HttpServletResponse.SC_OK);
            }  
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void deletePizza(int id, HttpServletResponse res) throws IOException {
        Pizza pizza = pizzaDAO.findById(id);
        System.out.println(pizza);
        if (pizza != null) {
            pizzaDAO.delete(pizza);
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAllPizzas(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Collection<Pizza> pizza = pizzaDAO.findAll();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(objectMapper.writeValueAsString(pizza));
        } catch (JsonProcessingException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getPizzaById(int id, HttpServletResponse res) throws IOException {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(objectMapper.writeValueAsString(pizza));
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaName(int id, HttpServletResponse res) throws IOException {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            res.setContentType("text/plain");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(pizza.getName());
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaFinalPrice(int id, HttpServletResponse res) throws IOException {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            res.setContentType("text/plain");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write("" + pizza.getFinalPrice());
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

    private void addPizza(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String requestBody = req.getReader().lines().reduce("", String::concat);
        Pizza ingredient = objectMapper.readValue(requestBody, Pizza.class);
        Pizza existingIngredient = pizzaDAO.findById(ingredient.getId());
        if (existingIngredient != null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            pizzaDAO.save(ingredient);
            res.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}