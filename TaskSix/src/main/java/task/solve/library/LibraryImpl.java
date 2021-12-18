package task.solve.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryImpl implements Library {

    private String jdbcUrl;
    private String user;
    private String password;

    public LibraryImpl(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public void addNewBook(Book book) {
        try(
                Connection con = getConnection();
                PreparedStatement stm = con.prepareStatement("insert into BOOKS values(?, ?, ?)");) {
            stm.setInt(1, book.getId());
            stm.setString(2, book.getTitle());
            stm.setInt(3, -1);
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addAbonent(Student student) {
        try(
                Connection con = getConnection();
                PreparedStatement stm = con.prepareStatement("insert into ABONENTS values(?, ?)");) {
            stm.setInt(1, student.getId());
            stm.setString(2, student.getName());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void borrowBook(Book book, Student student) {
        String updateSql = "update BOOKS set student_id = ? where book_id = ?";
        try (CallableStatement stm = getConnection().prepareCall(updateSql)) {
            stm.setInt(1, student.getId());
            stm.setInt(2, book.getId());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void returnBook(Book book, Student student) {
        String updateSql = "update BOOKS set student_id = ? where book_id = ?";
        try (CallableStatement stm = getConnection().prepareCall(updateSql)) {
            stm.setInt(1, -1);
            stm.setInt(2, book.getId());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Book> findAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        String sql = "select book_id, book_title, student_id from BOOKS";
        try (
                Statement stm = getConnection().createStatement();
                ResultSet rs = stm.executeQuery(sql);) {
            while (rs.next()) {
                Integer book_id = rs.getInt("book_id");
                String title = rs.getString("book_title");
                Integer stud_id = rs.getInt("student_id");
                if (stud_id == -1) {
                    availableBooks.add(new Book(book_id, title));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return availableBooks;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "select student_id, student_name from ABONENTS";
        try (
                Statement stm = getConnection().createStatement();
                ResultSet rs = stm.executeQuery(sql);) {
            while (rs.next()) {
                Integer stud_id = rs.getInt("student_id");
                String stud_name = rs.getString("student_name");
                students.add(new Student(stud_id, stud_name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }
}
