import java.util.List;

public class GenerateHtml {

    public String generateBooksHtml(List<Author> authors) {
        StringBuilder html = new StringBuilder();
        int countAuthor = 1;

        for (Author author : authors) {
            html.append("<div class=\"author\" onclick=\"toggleBooks('").append(author.name).append("')\">\n")
                    .append("\t&nbsp;").append(countAuthor++).append(". &nbsp;").append(author.name).append("\n")
                    .append("\t<div id=\"").append(author.name).append("\" class=\"books\">\n")
                    .append("\t\t<ul>\n");

            for (String book : author.getBooks()) {
                html.append("\t\t\t<li>").append(book).append("</li>\n");
            }

            html.append("\t\t</ul>\n\t</div>\n</div>\n\n");
        }

        return html.toString();
    }
}
