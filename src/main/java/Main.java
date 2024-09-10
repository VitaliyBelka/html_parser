import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Author> authors = new BookListProvider().getAuthors();
        String books = new GenerateHtml().generateBooksHtml(authors);
        String result;

        try {
            String existingHtml = Files.readString(Paths.get("src/main/resources/blank.html"));
            int insertionIndex = existingHtml.indexOf("<script>");
            result = existingHtml.substring(0, insertionIndex) + books + existingHtml.substring(insertionIndex);
            Files.writeString(Paths.get("books.html"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
