package task.solve.library;

import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LibraryImplTest {

    private LibraryImpl lib;

    @Before
    public void setUp() {
        this.lib = new LibraryImpl("jdbc:h2:mem:basket", "", "");
//        Basket basket = new BasketImpl("jdbc:derby:memory:basket;create=true", "", "");

        //init db
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:basket");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @After
    public void clear() {
        this.lib = null;
    }

    @Test
    public void addNewBook() {
        for(int i = 0; i < 10; i++) {
            this.lib.addNewBook(new Book(i, "book " + i));
        }
        for(int i = 0; i < 10; i++) {
            assertEquals(this.lib.findAvailableBooks().get(i).getId(), i);
//            assertThat(this.lib.findAvailableBooks(), hasItems(new Book(i, "book " + i)));
        }
        this.lib.addNewBook(new Book(0, "book_0"));
        assertNotEquals(this.lib.findAvailableBooks().get(0).getTitle(), "book_0");
    }

    @Test
    public void addAbonent() {
        for(int i = 0; i < 10; i++) {
            this.lib.addAbonent(new Student(i, "stud " + i));
        }
        for(int i = 0; i < 10; i++) {
            assertEquals(this.lib.getAllStudents().get(i).getId(), i);
//            assertThat(this.lib.findAvailableBooks(), hasItems(new Book(i, "book " + i)));
        }
        this.lib.addAbonent(new Student(0, "stud_0"));
        assertNotEquals(this.lib.getAllStudents().get(0).getName(), "stud_0");
    }

    @Test
    public void borrowBook() {
        for(int i = 0; i < 10; i++) {
            this.lib.addNewBook(new Book(i, "book " + i));
        }

        this.lib.borrowBook(new Book(0, "book 0"), new Student(2, "stud 2"));
        for(int i = 0; i < 9; i++) {
            assertEquals(this.lib.findAvailableBooks().get(i).getId(), i + 1);
//            assertTrue(this.lib.findAvailableBooks().contains(new Book(i, "book " + i)));
//            assertThat(this.lib.findAvailableBooks(), hasItems(new Book(i, "book " + i)));
        }
        assertFalse(this.lib.findAvailableBooks().contains(new Book(0, "book 0")));
    }

    @Test
    public void returnBook() {
        for(int i = 0; i < 10; i++) {
            this.lib.addNewBook(new Book(i, "book " + i));
        }
        this.lib.borrowBook(new Book(0, "book 0"), new Student(2, "stud 2"));
        for(int i = 0; i < 9; i++) {
            assertEquals(this.lib.findAvailableBooks().get(i).getId(), i + 1);
        }
        this.lib.returnBook(new Book(0, "book 0"), new Student(2, "stud 2"));
        assertEquals(this.lib.findAvailableBooks().get(9).getId(), 0);

    }

    @Test
    public void findAvailableBooks() {
        assertEquals(this.lib.findAvailableBooks(), new ArrayList<>());
    }

    @Test
    public void getAllStudents() {
        assertEquals(this.lib.getAllStudents(), new ArrayList<>());
    }
}