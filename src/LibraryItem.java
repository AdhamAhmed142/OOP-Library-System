/**
 * Abstract base class representing any item the library can lend out.
 * Holds everything that is common to Books, Magazines, and DVDs so that
 * the subclasses only need to add what makes them different.
 */
public abstract class LibraryItem {

    // ---- instance fields (private -> encapsulation) ----
    private String id;
    private String title;
    private boolean borrowed;

    // ---- static fields shared by ALL items ----
    private static int totalItemsCreated = 0;
    private static int nextNumber = 1;

    /**
     * Every subclass must call this constructor via super(title).
     * It validates the title, auto-generates a unique id, and updates
     * the static counters.
     */
    public LibraryItem(String title) {
        setTitle(title);
        this.id = "ITEM-" + nextNumber;
        nextNumber++;
        this.borrowed = false;
        totalItemsCreated++;
    }

    // ---- getters / setters ----

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        this.title = title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    // borrowed is intentionally NOT exposed through a public setter.
    // It can only change through markBorrowed() / markReturned().

    public static int getTotalItemsCreated() {
        return totalItemsCreated;
    }

    // ---- concrete methods shared by every item ----

    public void markBorrowed() {
        this.borrowed = true;
    }

    public void markReturned() {
        this.borrowed = false;
    }

    public void displayInfo() {
        System.out.println(getId() + " | " + getTitle()
                + " | " + getType()
                + " | loan: " + getLoanPeriodDays() + " days"
                + " | " + (isBorrowed() ? "OUT" : "available"));
    }

    // ---- abstract methods: each subclass supplies its own behaviour ----

    public abstract int getLoanPeriodDays();

    public abstract String getType();
}
