import java.util.List;

public class GenerateHtml {

    public String generateBooksHtml(List<Author> authors) {
        StringBuilder html = new StringBuilder();

        for (Author author : authors) {
            html.append("<div class=\"author\" onclick=\"toggleBooks('").append(author.name).append("')\">\n")
                    .append("\t").append(author.name).append("\n</div>\n")
                    .append("<div id=\"").append(author.name).append("\" class=\"books\">\n")
                    .append("\t<ul>\n");

            for (String book : author.getBooks()) {
                html.append("\t\t<li>").append(book).append("</li>\n");
            }

            html.append("\t</ul>\n</div>\n\n");
        }

        return html.toString();
    }
}
