package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String at;

    public Event (String description, String at) {
        super(description);
        this.at = at;
        try {
            date = LocalDate.parse(at);
        } catch (DateTimeParseException e) {
           date = null;
        }
    }

    public String getAt() {
        return at;
    }

    @Override
    public String toString() {
        if (date != null) {
            String dateAt =  date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
            return "[E]" + "[" + this.getStatusIcon() + "] " + description + " (at: " + dateAt + ")";
        } else {
            return "[E]" + "[" + this.getStatusIcon() + "] " + description + " (at: " + at + ")";
        }
    }
}
