package controleurs;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOPizza;
import dao.PizzaDAODatabase;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizza/*")
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
                    getPizzaName(id, res);
                } else {
                    getPizzaById(id, res);
                }

            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void getAllPizzas(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            List<Pizza> pizza = pizzaDAO.findAll();
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
}