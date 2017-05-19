import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by admin on 19.05.2017.
 */
public class RestServlet extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private TodoList list = new TodoList();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            String uri = req.getRequestURI();
            if (uri.startsWith("/rest/add")) {
                String txt = req.getParameter("text");
                list.add(txt);
                gson.toJson(list.View(), resp.getWriter());
            } else if (uri.startsWith("/rest/delete")) {
                String told = req.getParameter("id");
                int id = Integer.parseInt(told);
                list.delete(id);
                gson.toJson(list.View(), resp.getWriter());
            } else if (uri.startsWith("/rest/view")) {

                gson.toJson(list.View(), resp.getWriter());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


