package duke;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * This class contains operations to manipulate the list stored by Duke.
 */

public class TaskList {
    static void assertTaskListNotNull() {
        assert Storage.taskList != null : "The list does not exist in the first place.";
    }

    static void add(Task task) {
        assertTaskListNotNull();
        Storage.taskList.add(task);
    }

    static String updateDescription(int idx, String desc) {
        assertTaskListNotNull();
        int index = idx - 1;
        try {
            Task selectedTask = Storage.taskList.get(index);
            selectedTask.setDescription(desc);
            Storage.taskList.remove(index);
            Storage.taskList.add(index, selectedTask);
            Storage s = new Storage();
            s.save();
            return "Successfully updated task.";
        } catch (IndexOutOfBoundsException e) {
            return "Item not found in list.";
        }
    }

    static String updateDate(int idx, String date) {
        int index = idx - 1;
        try {
            LocalDate updatedDate = LocalDate.parse(date.replaceAll("\\s+",""));
            Task selectedTask = Storage.taskList.get(index);
            selectedTask.setDate(updatedDate);
            Storage.taskList.remove(index);
            Storage.taskList.add(index, selectedTask);
            Storage s = new Storage();
            s.save();
            return "Successfully updated task.";
        } catch (IndexOutOfBoundsException e) {
            return "Item not found in list.";
        } catch (DateTimeParseException e) {
            return "Not a valid date.";
        }
    }

    static String delete(int idx) {
        assertTaskListNotNull();
        int index = idx - 1;
        try {
            Storage.taskList.remove(index);
            Storage s = new Storage();
            s.save();
            return "Successfully deleted task.";
        } catch (IndexOutOfBoundsException e) {
            return "Item not found in list.";
        }
    }

    static String list() {
        assertTaskListNotNull();
        ArrayList<Task> taskArrayList = Storage.taskList;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here are the tasks in your list: " + "\n");
        for (int i = 0; i < taskArrayList.size(); i++) {
            stringBuilder.append(i + 1).append(". ").append(taskArrayList.get(i)).append("\n");
        }
        return stringBuilder.toString();
    }

    static String find(String term){
        assertTaskListNotNull();
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasResults = false;
        for (Task t : Storage.taskList) {
            if (t.getDescription().contains(term)) {
                hasResults = true;
                stringBuilder.append(t).append('\n');
            }
        }
        if (!hasResults) {
            return "Sorry, we didn't find anything related to your search terms.";
        } else {
            return stringBuilder.toString();
        }
    }
}
