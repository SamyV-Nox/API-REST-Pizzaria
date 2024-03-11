package controleurs;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class API extends HttpServlet {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected static final String CONTENT_TYPE = "application/json";
    protected static final String CHARACTER_ENCODING = "UTF-8";

    public void formatResponse(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
    }

    public void send(HttpServletResponse res, Object obj) {
        try {
            res.getWriter().write(OBJECT_MAPPER.writeValueAsString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int isNumber(String stringNumber) {
        try {
            return Integer.parseInt(stringNumber);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String[] getParametre(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path == null) {
            path = "/";
        } else if (path.charAt(path.length() - 1) != '/') {
            path = path + '/';
        }
        return path.split("/");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) {
        formatResponse(res);
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

    protected void doPatch(HttpServletRequest req, HttpServletResponse res) {
    }

}
