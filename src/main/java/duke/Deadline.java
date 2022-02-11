package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        try {
            date = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            date = null;
        }
    }

    public String getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        if (date != null) {
            String dateBy =  date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
            return "[D]" + "[" + this.getStatusIcon() + "] " + description + " (by: " + dateBy + ")";
        } else {
            return "[D]" + "[" + this.getStatusIcon() + "] " + description + " (by: " + by + ")";
        }
    }
}