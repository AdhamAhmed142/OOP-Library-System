import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The Library owns the whole system: the catalog of items, the members,
 * and the set of ids currently on loan.
 *
 * Golden rule: this class never asks "what kind of item is this?" with
 * instanceof. It always calls the overridden LibraryItem methods
 * (getLoanPeriodDays, getType, displayInfo) and lets polymorphism decide
 * the behaviour.
 */
public class Library {

    private Map<String, LibraryItem> catalog = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private Set<String> borrowedIds = new HashSet<>();

    // ---- registration ----

    public void addItem(LibraryItem item) {
        catalog.put(item.getId(), item);
    }

    public void addMember(Member m) {
        members.put(m.getMemberId(), m);
    }

    // ---- core lending operations ----

    public void borrowItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("No member found with id " + memberId + ".");
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("No item found with id " + itemId + ".");
        }
        if (item.isBorrowed()) {
            throw new LibraryException("item " + itemId + " is already out.");
        }
        if (!member.canBorrowMore()) {
            throw new LibraryException("member " + memberId + " has reached their borrowing limit.");
        }

        item.markBorrowed();
        member.addBorrowedItem(item);
        borrowedIds.add(itemId);
    }

    public void returnItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("No member found with id " + memberId + ".");
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("No item found with id " + itemId + ".");
        }
        if (!member.hasBorrowed(item)) {
            throw new LibraryException("member " + memberId + " did not borrow item " + itemId + ".");
        }

        item.markReturned();
        member.removeBorrowedItem(item);
        borrowedIds.remove(itemId);
    }

    // ---- display ----

    public void listCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("The catalog is empty.");
            return;
        }
        for (LibraryItem item : catalog.values()) {
            item.displayInfo();
        }
    }

    public void printReport() {
        Map<String, Integer> countsByType = new TreeMap<>();
        for (LibraryItem item : catalog.values()) {
            countsByType.merge(item.getType(), 1, Integer::sum);
        }

        System.out.println("---------- REPORT ----------");
        System.out.println("Total items : " + catalog.size());
        System.out.println("Currently out : " + borrowedIds.size());
        System.out.println("Borrowed ids : " + borrowedIds);
        System.out.println("Items by type : " + countsByType);
        System.out.println("Total created : " + LibraryItem.getTotalItemsCreated());
        System.out.println("----------------------------");
    }

    // ---- bonus features ----

    /** Bonus: search for items whose title contains the given text (case-insensitive). */
    public List<LibraryItem> searchByTitle(String text) {
        List<LibraryItem> results = new ArrayList<>();
        String needle = text.toLowerCase();
        for (LibraryItem item : catalog.values()) {
            if (item.getTitle().toLowerCase().contains(needle)) {
                results.add(item);
            }
        }
        return results;
    }

    /** Bonus: list only items that are currently available (not borrowed). */
    public List<LibraryItem> listAvailableItems() {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : catalog.values()) {
            if (!item.isBorrowed()) {
                results.add(item);
            }
        }
        return results;
    }
}
