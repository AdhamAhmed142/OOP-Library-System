/**
 * A single custom checked exception used for every library "business rule"
 * violation (item already out, member at limit, returning an item not
 * held, unknown id, etc). Forcing everything through one type keeps the
 * calling code simple: one catch block handles all rule violations.
 */
public class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}
