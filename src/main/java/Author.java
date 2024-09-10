import java.util.ArrayList;
import java.util.List;

public class Author {
    String name;
    List<String> books;

    public Author(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public void addBook(String book) {
        books.add(book);
    }

    public String getName() {
        return name;
    }

    public List<String> getBooks() {
        return books;
    }
}