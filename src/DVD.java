/**
 * A DVD is a LibraryItem that can be borrowed for 3 days.
 */
public class DVD extends LibraryItem {

    private int runtimeMinutes;

    public DVD(String title, int runtimeMinutes) {
        super(title);
        setRuntimeMinutes(runtimeMinutes);
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(int runtimeMinutes) {
        if (runtimeMinutes <= 0) {
            throw new IllegalArgumentException("Runtime minutes must be greater than zero.");
        }
        this.runtimeMinutes = runtimeMinutes;
    }

    @Override
    public int getLoanPeriodDays() {
        return 3;
    }

    @Override
    public String getType() {
        return "DVD";
    }
}
