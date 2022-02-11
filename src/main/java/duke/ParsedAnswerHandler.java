package duke;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * This class receives a ParsedAnswer object and execute commands based on information
 * from the object.
 */

public class ParsedAnswerHandler {
    ParsedAnswer pa;

    ParsedAnswerHandler(ParsedAnswer pa) {
        this.pa = pa;
    }

    void assertCommandNotEmpty() {
        assert !pa.getCommand().isEmpty() : "Each ParsedAnswer object should contain a command.";
    }

    String writeTaskToFile(String task, String result) {
        String filePath = System.getProperty("user.dir")  + "/data/storage.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.append(task);
            writer.close();
            return result;
        } catch (IOException e) {
            return "Error saving task to file.";
        }

    }

    String changeMarkStatus(String action) {
        if (action.equalsIgnoreCase("mark")) {
            try {
                Task t = Storage.taskList.get(pa.getIndex() - 1);
                t.markAsDone();
                Storage s = new Storage();
                s.save();
                return "Successfully marked " + pa.getIndex();
            } catch (IndexOutOfBoundsException e) {
                return "Item not found. Please try again.";
            }
        } else {
            try {
                Task t = Storage.taskList.get(pa.getIndex() - 1);
                t.markAsUndone();
                Storage s = new Storage();
                s.save();
                return "Successfully ummarked " + pa.getIndex();
            } catch (IndexOutOfBoundsException e) {
                return "Item not found. Please try again.";
            }
        }
    }

    String execute() {
        assertCommandNotEmpty();
        switch (pa.getCommand()) {
            case "bye":
                Duke.isGoodbye = true;
                return "Have a nice day.";

            case "list":
                return TaskList.list();

            case "todo":
                ToDos td = new ToDos(pa.getDesc());
                TaskList.add(td);
                String todoTask = "T," +
                        "1," +
                        td.getDescription() + '\n';
                String todoResult = "Successfully added todo task to list.";
                return writeTaskToFile(todoTask, todoResult);

            case "deadline":
                Deadline dl = new Deadline(pa.getDesc(), pa.getDate());
                TaskList.add(dl);
                String deadlineTask = "D," +
                        "1," +
                        dl.getDescription() +
                        "," +
                        dl.getBy() + '\n';

                String deadlineResult = "Successfully added deadline to list.";
                return writeTaskToFile(deadlineTask, deadlineResult);

            case "event":
                Event ev = new Event(pa.getDesc(), pa.getDate());
                TaskList.add(ev);
                String eventTask = "E," +
                        "1," +
                        ev.getDescription() +
                        "," +
                        ev.getAt() + '\n';
                String eventResult = "Successfully added event to list.";
                return writeTaskToFile(eventTask, eventResult);

            case "mark":
               return changeMarkStatus("mark");

            case "unmark":
              return changeMarkStatus("unmark");

            case "error":
                return pa.getDesc();

            case "delete":
                return TaskList.delete(pa.getIndex());

            case "find":
                return TaskList.find(pa.getDesc());

            case "update todo":

            case "update deadline":

            case "update event":
                if (pa.getDate() != null) {
                    TaskList.updateDate(pa.getIndex(), pa.getDate());
                }
                return TaskList.updateDescription(pa.getIndex(), pa.getDesc());
        }

        return "-1";
    }
}
