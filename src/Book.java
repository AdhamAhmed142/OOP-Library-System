/**
 * A Book is a LibraryItem that can be borrowed for 21 days.
 */
public class Book extends LibraryItem {

    private String author;
    private int pages;

    public Book(String title, String author, int pages) {
        super(title);
        setAuthor(author);
        setPages(pages);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty.");
        }
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Pages must be greater than zero.");
        }
        this.pages = pages;
    }

    @Override
    public int getLoanPeriodDays() {
        return 21;
    }

    @Override
    public String getType() {
        return "Book";
    }
}
