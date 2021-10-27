package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's status(decision given by the company) in InternSHIP.
 * Is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can be 'Pending', 'Accepted' or 'Rejected'."
            + "It will be 'Pending' by default for a new Application that is added.";

    /*
     * The first character of the status must not be
     * a whitespace.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status;
    }

    /**
     * Returns true if a given string is a valid status ("Pending" or "Accepted" or "Rejected").
     */
    public static boolean isValidStatus(String test) {
        return test.equals("Pending") || test.equals("Accepted") || test.equals("Rejected");
    }

    @Override
    public String toString() {
        return value;
    }

    public String toDisplayString() {
        if (value.equals("Pending")) {
            return "❓";
        } else if (value.equals("Accepted")) {
            return "😊";
        } else {
            assert (value.equals("Rejected"));
            return "😥";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
