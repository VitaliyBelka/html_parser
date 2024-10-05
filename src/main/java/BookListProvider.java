import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BookListProvider {

    public List<Author> getAuthors() {
        List<String> list = getBookData();
        List<Author> authors = new ArrayList<>();
        Map<String, Author> authorMap = new HashMap<>();
        String regex = "([А-Яа-яё\\s-]+)?([А-Я]\\.\\s?)?([А-Я]\\.\\s)?([А-Яа-яё-]+)$";
        Pattern pattern = Pattern.compile(regex);

        for(String data : list) {
            Matcher matcher = pattern.matcher(data);
            if (matcher.find()) {
                String authorName = matcher.group().trim();
                String title = data.substring(0, matcher.start());

                if (authorName.equals("аун")) {
                    authorName = "Фредерик Браун";
                    title = "Вежливость.";
                } else if (authorName.contains("Конан")) {
                    authorName = "Артур Конан-Дойл";
                } else if (authorName.contains("Элиот") && authorName.contains("Джордж")) {
                    authorName = "Джордж Элиот";
                } else if (authorName.contains("Лейнстер")) {
                    authorName = "Мюррей Лейнстер";
                } else if (authorName.contains("Ивлин")) {
                    authorName = "Ивлин Во";
                } else if (authorName.contains("Голсуорси")) {
                    authorName = "Джон Голсуорси";
                } else if (authorName.equals("Джейн Остен")) {
                    authorName = "Джейн Остин";
                } else if (authorName.contains("Стивенсон")) {
                    authorName = "Роберт Льюис Стивенсон";
                }

                Author author = authorMap.get(authorName);

                if (author == null) {
                    author = new Author(authorName);
                    authorMap.put(authorName, author);
                    authors.add(author);
                }

                author.addBook(title);

            }
        }

        return new ArrayList<>(authors);
    }

    private List<String> getBookData() {
        List<String> books = new ArrayList<>();
        Configuration.browser = "edge";
        Configuration.pageLoadStrategy = "none";
        open("https://studyenglishwords.com/browse");

        String previousCount = $("#searchResultsTitle").getText();
        $(By.xpath("//*[@id=\"inner_header\"]/ul/li[1]/div/a[2]")).click();
        $("#searchResultsTitle").shouldHave(not(text(previousCount)), Duration.ofSeconds(5));
        int totalBooks = Integer.parseInt($("#searchResultsTitle").getText().replaceAll("\\D+", ""));
        int indexBook = 1;
        String tempBook = "temp";

        here:
        while (true) {
            $("#inner_header > div.searchResults > ol > li:nth-child(1) > a").shouldHave(not(text(tempBook)), Duration.ofSeconds(5));

            for (int olValue = 1; olValue <= 25; olValue++) {
                String bookAndAuthor = $(By.xpath("//*[@id=\"inner_header\"]/div[3]/ol/li[" + olValue + "]/a")).getText();

                if (olValue == 1) {
                    tempBook = bookAndAuthor;
                }

                books.add(bookAndAuthor);

                if (totalBooks == indexBook++) {
                    break here;
                }
            }

            $("#inner_header > div.searchResults > p.paginator.paginatorNormalMode > span.next > a").click();
        }

        closeWebDriver();

        return books;
    }
}
