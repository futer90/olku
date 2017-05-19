import sun.plugin2.message.Message;

import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TodoList {
    private int id = -1;
    private final ArrayList<Task> todoList = new ArrayList();

    public void add(String taskName) throws SQLException {
//        id++;
//        todoList.add(new Task(id, taskName));
//        return id;
        try (Connection c = DriverManager.getConnection("jdbc:h2:~/test")) {
            try (PreparedStatement ps = c.prepareStatement("insert into todo (text) values(?)")) {
                ps.setString(1, taskName);
                ps.executeUpdate();
            }
        }
    }

    List<Task> View() throws SQLException {
        List<Task> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection("jdbc:h2:~/test")) {
            try (PreparedStatement ps = c.prepareStatement("select id, text from todo order by id")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String text = rs.getString(2);
                        list.add(new Task(id, text));
                    }
                }
                //ps.executeUpdate();
            }
        }
        return list;
    }

    public void delete(int id)  {
        try (Connection c = DriverManager.getConnection("jdbc:h2:~/test")) {
            try (PreparedStatement ps = c.prepareStatement("delete from todo where id=?")) {
                ps.setInt(1,id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}