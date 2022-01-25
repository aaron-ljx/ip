import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ParsedAnswerHandler {
    ParsedAnswer pa;

    ParsedAnswerHandler(ParsedAnswer pa) {
        this.pa = pa;
    }

    void execute() {
        String filePath = System.getProperty("user.dir")  + "/data/storage.txt";
        switch (pa.getCommand()) {
            case "bye":
                System.out.println("Have a nice day.");
                System.exit(0);
                break;

            case "list":
                ArrayList<Task> taskArrayList = Storage.taskList;
                System.out.println("Here are the tasks in your list: " + "\n");
                for (int i = 0; i < taskArrayList.size(); i++) {
                    System.out.println( (i + 1) + ". "  + taskArrayList.get(i) + "\n");
                }
                break;

            case "todo":
                ToDos td = new ToDos(pa.getDesc());
                Storage.taskList.add(td);
                try {
                    String sb = "T," +
                            "1," +
                            td.getDescription() + '\n';
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                    writer.append(sb);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error saving task to file.");
                }
                break;

            case "deadline":
                Deadline dl = new Deadline(pa.getDesc(), pa.getDate());
                Storage.taskList.add(dl);
                try {
                    String sb = "D," +
                            "1," +
                            dl.getDescription() +
                            "," +
                            dl.getBy() + '\n';
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                    writer.append(sb);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error saving task to file.");
                }
                break;

            case "event":
                Event ev = new Event(pa.getDesc(), pa.getDate());
                Storage.taskList.add(ev);
                try {
                    String sb = "E," +
                            "1," +
                            ev.getDescription() +
                            "," +
                            ev.getAt() + '\n';
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                    writer.append(sb);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error saving task to file.");
                }
                break;

            case "mark":
                try {
                    Task t = Storage.taskList.get(pa.getIndex() - 1);
                    t.markAsDone();
                    Storage s = new Storage();
                    s.save();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Item not found. Please try again.");
                }
                break;

            case "unmark":
               try {
                   Task t = Storage.taskList.get(pa.getIndex() - 1);
                   t.markAsUndone();
                   Storage s = new Storage();
                   s.save();
               } catch (IndexOutOfBoundsException e) {
                   System.out.println("Item not found. Please try again.");
               }
               break;

            case "error":
                System.out.println(pa.getDesc());
                break;

        }
    }
}
