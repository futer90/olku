import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestServlet extends HttpServlet {
    private TodoList list = new TodoList();
    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);

    {
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(new File(".")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Template t=cfg.getTemplate("todo.html");

        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write("<html>\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <title>To-Do List</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<form method=\"post\" action=\"add\">\n" +
//                "    Add task:<input name=\"task\">\n" +
//                "    <input type=\"submit\" value=\"add\">\n" +
//                "</form>\n" +
//                "<form method=\"post\" action=\"remove\">\n" +
//                "    Remove task:<input name=\"taskID\">\n" +
//                "    <input type=\"submit\" value=\"remove\">\n" +
//                "</form>\n" +
//                "<ol>\n" +
//                buf +
//                "</ol>\n" +
//                "</body>\n" +
//                "</html>");
        try {
            Template t = cfg.getTemplate("todo.html");
            Map<String, Object> map = new HashMap<>();
            map.put("tasks", list.View());
            t.process(map, resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        try {
            if (uri.equals("/delete")) {
                String told = req.getParameter("id");
                int id = Integer.parseInt(told);
                list.delete(id);
            } else {
                String what = req.getParameter("task");
                list.add(what);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  if (whatID!=null && whatID.length()>0)
        //       list.delete(Integer.parseInt(whatID));
        resp.sendRedirect("/");
    }
}