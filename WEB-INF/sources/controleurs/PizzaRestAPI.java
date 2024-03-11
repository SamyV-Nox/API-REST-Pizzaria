package controleurs;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PizzaDAO;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends HttpServlet {

    private static PizzaDAO pizzaDAO = new PizzaDAO();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        formatResponse(res);

        if (isPathOfAll(req)) {
            getAllPizzas(res);
        } else {
            if (isLongPath(req)) {
                String[] pathParts = req.getPathInfo().split("/");
                final int ID = getId(pathParts);
                if (ID != -1 && pathParts.length == 3 && !pathParts[2].isEmpty()) {
                    if (isPathOfName(pathParts)) {
                        getPizzaName(ID, res);
                    } else if (isPathOfDough(pathParts)) {
                        getPizzaDough(ID, res);
                    } else if (isPathOfPrice(pathParts)) {
                        getPizzaPrice(ID, res);
                    } else if (isPathOfIngredients(pathParts)) {
                        getPizzaIngredients(ID, res);
                    } else if (isPathOfFinalPrice(pathParts)) {
                        getPizzaFinalPrice(ID, res);
                    } else {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else if (ID != -1 && pathParts.length == 2 && !pathParts[1].isEmpty()) {
                    getPizzaById(ID, res);
                } else {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/")) {
            addPizza(req, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
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
    public void service(HttpServletRequest req, HttpServletResponse res) {
        try {
            if (req.getMethod().equalsIgnoreCase("PATCH")) {
                doPatch(req, res);
            } else {
                super.service(req, res);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURI();
        String idString = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
        int id = Integer.parseInt(idString);

        Pizza existingPizza = pizzaDAO.findById(id);
        if (existingPizza == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Récupération des paramètres de la requête
        String paramName = request.getParameter("name");
        String paramDough = request.getParameter("dough");
        String paramPrice = request.getParameter("price");

        // Modification des attributs de la pizza
        if (paramName != null) {
            existingPizza.setName(paramName);
        }
        if (paramDough != null) {
            existingPizza.setDough(paramDough);
        }
        if (paramPrice != null) {
            try {
                double newPrice = Double.parseDouble(paramPrice);
                existingPizza.setPrice(newPrice);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        // Mise à jour de la pizza dans la base de données
        pizzaDAO.update(existingPizza);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void formatResponse(HttpServletResponse res) {
        res.setContentType(CONTENT_TYPE);
        res.setCharacterEncoding(CHARACTER_ENCODING);
    }

    private boolean isPathOfAll(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return pathInfo == null || pathInfo.equals("/");
    }

    private boolean isPathOfFinalPrice(String[] pathParts) {
        return pathParts[2].equals("finalPrice");
    }

    private boolean isPathOfName(String[] pathParts) {
        return pathParts[2].equals("name");
    }

    private boolean isPathOfDough(String[] pathParts) {
        return pathParts[2].equals("dough");
    }

    private boolean isPathOfPrice(String[] pathParts) {
        return pathParts[2].equals("price");
    }

    private boolean isPathOfIngredients(String[] pathParts) {
        return pathParts[2].equals("ingredients");
    }

    private int getId(String[] pathParts) {
        int id = -1;
        try {
            id = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            
        }
        return id;
    }

    private boolean isLongPath(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            return false;
        }
        String[] pathParts = pathInfo.split("/");
        if (pathParts != null) {
            return pathParts.length > 0;
        }
        return false;
    }

    private void getPizzaDough(int id, HttpServletResponse res) {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza.getDough()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaPrice(int id, HttpServletResponse res) {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza.getPrice()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getPizzaIngredients(int id, HttpServletResponse res) {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            try {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza.getIngredients()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deletePizza(int id, HttpServletResponse res) {
        Pizza pizza = pizzaDAO.findById(id);
        if (pizza != null) {
            pizzaDAO.delete(pizza);
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getAllPizzas(HttpServletResponse res) {
        try {
            Collection<Pizza> pizza = pizzaDAO.findAll();
            res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pizza));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getPizzaById(int id, HttpServletResponse res) {
        Pizza pizza = pizzaDAO.findById(id);
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
        Pizza pizza = pizzaDAO.findById(id);
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
        Pizza pizza = pizzaDAO.findById(id);
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
            existingPizza = pizzaDAO.findById(pizza.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (existingPizza != null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            pizzaDAO.save(pizza);
            res.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}