package task.solve.library;

import org.apache.log4j.Logger;
import task.solve.basket.App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AppLib {
    private static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) throws SQLException {

        Library lib = new LibraryImpl("jdbc:h2:mem:basket", "", "");
        //Basket basket = new BasketImpl("jdbc:derby:memory:basket;create=true", "", "");

        //init db
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:basket");
        //Connection connection = DriverManager.getConnection("jdbc:derby:memory:basket;create=true");
        try (Statement stm = connection.createStatement();) {
            String tableSql = "CREATE TABLE ABONENTS(\n" +
                    "    student_id int,\n" +
                    "    student_name varchar(255)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE BOOKS(\n" +
                    "    book_id int,\n" +
                    "    book_title varchar(255),\n" +
                    "    student_id int\n" +
                    ");";
            stm.execute(tableSql);
        }

        for(int i = 0; i < 10; i++) {
            lib.addAbonent(new Student(i, "stud " + i));
            lib.addNewBook(new Book(i, "book " + i));
        }

        List<Book> books = lib.findAvailableBooks();
//        books.forEach(e->System.out.println(e.toString()));
        List<Student> students = lib.getAllStudents();
//        students.forEach(e->System.out.println(e.toString()));

        lib.borrowBook(new Book(1, "book 1"), new Student(2, "stud 2"));
        List<Book> books_take = lib.findAvailableBooks();
        books_take.forEach(e->System.out.println(e.toString()));

        System.out.println("-------------------------");
        lib.returnBook(new Book(1, "book 1"), new Student(2, "stud 2"));
        List<Book> books_return = lib.findAvailableBooks();
        books_return.forEach(e->System.out.println(e.toString()));
    }
}
