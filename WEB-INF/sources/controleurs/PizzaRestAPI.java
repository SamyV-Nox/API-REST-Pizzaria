package controleurs;

import java.io.IOException;
import java.util.Collection;

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
    private static final DAOPizza PIZZA_DAO = new PizzaDAODatabase();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    private void formatResponse(HttpServletResponse res) {
        res.setContentType(CONTENT_TYPE);
        res.setCharacterEncoding(CHARACTER_ENCODING);
    }

    private boolean isPathOfAll(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return pathInfo == null || pathInfo.equals("/");
    }

    private boolean isPathOfFinalPrice(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        return pathParts.length == 3 && pathParts[2].equals("prixfinal");
    }

    private boolean isPathOfName(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        return pathParts.length == 3 && pathParts[2].equals("name");
    }

    private int getId(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        int id = -1;
        try {
            id = Integer.parseInt(pathParts[1]);
            return id;
        } catch (NumberFormatException e) {
            return id;
        }
    }

    private boolean isLongPath(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        return pathParts.length > 0;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        formatResponse(res);
        
        if (isPathOfAll(req)) {
            getAllPizzas(res);
        } else {
            if (isLongPath(req)) {
                final int ID = getId(req);
                if (ID != -1) {
                    if (isPathOfName(req)) {
                        getPizzaName(ID, res);
                    } else if (isPathOfFinalPrice(req)) {
                        getPizzaFinalPrice(ID, res);
                    } else {
                        getPizzaById(ID, res);
                    }
                }
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        PIZZA_DAO.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/")) {
            try {
                addPizza(req, res);
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && isValidId(pathInfo)) {
            int id = 0;
            try {
                id = Integer.parseInt(pathInfo.substring(1));
                deletePizza(id, res);
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && isValidId(pathInfo)) {
            int id = Integer.parseInt(pathInfo.substring(1));

            Pizza pizza = PIZZA_DAO.findById(id);
            if (pizza != null) {
                String requestBody = req.getReader().lines().reduce("", String::concat);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                double newPrice = jsonNode.get("price").asDouble();

                pizza.setPrice(newPrice);
                PIZZA_DAO.update(pizza);

                res.getWriter().write(objectMapper.writeValueAsString(pizza));
                res.setStatus(HttpServletResponse.SC_OK);
            }
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deletePizza(int id, HttpServletResponse res) {
        Pizza pizza = PIZZA_DAO.findById(id);
        if (pizza != null) {
            PIZZA_DAO.delete(pizza);
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAllPizzas(HttpServletResponse res) {
        try {
            Collection<Pizza> pizza = PIZZA_DAO.findAll();
            res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getPizzaById(int id, HttpServletResponse res) {
        Pizza pizza = PIZZA_DAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaName(int id, HttpServletResponse res) {
        Pizza pizza = PIZZA_DAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write(pizza.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaFinalPrice(int id, HttpServletResponse res) {
        Pizza pizza = PIZZA_DAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write("" + pizza.getFinalPrice());
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void addPizza(HttpServletRequest req, HttpServletResponse res) {
        Pizza existingPizza = null;
        Pizza pizza = null;
        try {
            String requestBody = req.getReader().lines().reduce("", String::concat);
            pizza = OBJECT_MAPPER.readValue(requestBody, Pizza.class);
            existingPizza = PIZZA_DAO.findById(pizza.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (existingPizza != null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            PIZZA_DAO.save(pizza);
            res.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}