package controleurs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.PateDao;
import dto.Pate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pates/*")
public class PateAPI extends API {

    private static final PateDao PATE_DAO = new PateDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] parameter = getParametre(req);

        if (parameter.length == 0)
            getAll(res);
        else if (1 <= parameter.length) {
            int id = isNumber(parameter[1]);
            if (id == -1)
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            else if (parameter.length == 2)
                getById(res, id);
            else if (parameter.length == 3 && parameter[2].equals("id"))
                getIdById(res, id);
            else if (parameter.length == 3 && parameter[2].equals("name"))
                getNamebyId(res, id);
            else
                res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);

        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getIdById(HttpServletResponse res, int id) {
        try {
            Pate pate = PATE_DAO.findById(id);
            if (pate != null) {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pate.getId()));
                res.setStatus(HttpServletResponse.SC_OK);
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException | SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getNamebyId(HttpServletResponse res, int id) {
        try {
            Pate pate = PATE_DAO.findById(id);
            if (pate != null) {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pate.getName()));
                res.setStatus(HttpServletResponse.SC_OK);
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException | SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getAll(HttpServletResponse res) {
        try {
            List<Pate> pates = PATE_DAO.findAll();
            if (!pates.isEmpty()) {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pates));
                res.setStatus(HttpServletResponse.SC_OK);
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException | SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void getById(HttpServletResponse res, int id) {
        try {
            Pate pate = PATE_DAO.findById(id);
            if (pate != null) {
                res.getWriter().write(OBJECT_MAPPER.writeValueAsString(pate));
                res.setStatus(HttpServletResponse.SC_OK);
            } else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException | SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String requestBody = req.getReader().lines().reduce("", String::concat);
            Pate pate = OBJECT_MAPPER.readValue(requestBody, Pate.class);
            PATE_DAO.save(pate);
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
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
            Pate pate = PATE_DAO.findById(id);
            String requestBody = req.getReader().lines().reduce("", String::concat);
            String newName = OBJECT_MAPPER.readTree(requestBody).get("name").asText();

            if (!newName.isEmpty()) {
                pate.setName(newName);
                
                int code = PATE_DAO.update(pate);
                if (code == 0)
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                else if (code == -1)
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                else
                    res.setStatus(HttpServletResponse.SC_OK);
            } else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        String[] parameter = getParametre(req);
        if (2 == parameter.length) {
            int id = isNumber(parameter[1]);

            if (id != -1) {
                delete(res, id);
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void delete(HttpServletResponse res, int id) {
        try {
            int code = PATE_DAO.delete(id);
            if (code == 0)
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            else if (code == -1)
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            else
                res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) {
        super.service(req, res);
        PATE_DAO.close();
    }
}