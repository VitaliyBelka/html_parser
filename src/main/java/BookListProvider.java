import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

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
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "none";
        open("https://studyenglishwords.com/browse");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        sleep(4000);
        $("#inner_header > ul > li.option_type > div > a:nth-child(3)").shouldBe(visible).click(); // срабатывает не всегда
        sleep(800);
        $("#inner_header > ul > li.option_type > div > a:nth-child(3)").click();
        sleep(3000); // ждать пока изменится селектор и значение
        SelenideElement element = $("#searchResultsTitle");
        int totalBooks = Integer.parseInt(element.getText().replaceAll("\\D+", ""));
        int indexBook = 1;

        here:
        while (true) {
            for (int olValue = 1; olValue <= 25; olValue++) {
                SelenideElement bookAndAuthor = $("#inner_header > div.searchResults > ol > li:nth-child(" + olValue + ") > a");
                books.add(bookAndAuthor.getText());

                if (totalBooks == indexBook++) {
                    break here;
                }
            }

            $("#inner_header > div.searchResults > p.paginator.paginatorNormalMode > span.next > a").click();
            sleep(3000);
        }

        closeWebDriver();

        return books;
    }
}
