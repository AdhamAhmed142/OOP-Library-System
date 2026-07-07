/**
 * A Magazine is a LibraryItem that can be borrowed for 7 days.
 */
public class Magazine extends LibraryItem {

    private int issueNumber;

    public Magazine(String title, int issueNumber) {
        super(title);
        setIssueNumber(issueNumber);
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be greater than zero.");
        }
        this.issueNumber = issueNumber;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}
