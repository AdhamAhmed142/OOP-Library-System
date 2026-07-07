import java.util.ArrayList;
import java.util.List;

/**
 * A library member who can borrow up to maxAllowed items at once.
 * Note that borrowedItems is typed as List<LibraryItem> (the base type),
 * so a member can hold a mix of Books, Magazines, and DVDs together.
 */
public class Member {

    private String memberId;
    private String name;
    private int maxAllowed;
    private List<LibraryItem> borrowedItems;

    public Member(String memberId, String name, int maxAllowed) {
        setMemberId(memberId);
        setName(name);
        setMaxAllowed(maxAllowed);
        this.borrowedItems = new ArrayList<>();
    }

    // ---- getters / setters with validation ----

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member id cannot be null or empty.");
        }
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be empty.");
        }
        this.name = name;
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(int maxAllowed) {
        if (maxAllowed <= 0) {
            throw new IllegalArgumentException("maxAllowed must be a positive number.");
        }
        this.maxAllowed = maxAllowed;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    // ---- behaviour ----

    public int getBorrowedCount() {
        return borrowedItems.size();
    }

    public boolean canBorrowMore() {
        return borrowedItems.size() < maxAllowed;
    }

    /** Helper used by Library to record that this member now holds an item. */
    public void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    /** Helper used by Library to remove an item the member is returning. */
    public void removeBorrowedItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    /** Helper used by Library to check whether this member holds a given item. */
    public boolean hasBorrowed(LibraryItem item) {
        return borrowedItems.contains(item);
    }
}
