package duke;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a class that is responsible for storing the tasks into Duke.
 */
public class Storage {
    private final String homeDir;
    public static ArrayList<Task> taskList;

    Storage() {
        this.homeDir = System.getProperty("user.dir");
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
    }

    /**
     * Creates a new directory with the storage file that will store the tasks.
     */
    void createNewStorageFile() {
        try {
            Files.createDirectories(Paths.get(this.homeDir + "/data"));
            File myObj = new File(this.homeDir + "/data/storage.txt");
            myObj.createNewFile();

        } catch (IOException e) {
            System.out.println("Load failed.");
        }
    }

    /**
     * Loads the tasks stored in the storage file to the application.
     * If storage file not found, a new one will be created by {@code createNewStorageFile()}
     */
    void load() {
        boolean directoryExists = new java.io.File(this.homeDir + "/data").exists();
        if (!directoryExists) {
            createNewStorageFile();
        } else {
            // parse the content in the file and add tasks into the arraylist.
            try {
                FileInputStream fis = new FileInputStream(this.homeDir + "/data/storage.txt");
                Scanner sc = new Scanner(fis);
                while(sc.hasNextLine()) {
                    String[] parsedTaskFromFile = sc.nextLine().split(",");
                    if (parsedTaskFromFile.length > 0) {
                        String taskType = parsedTaskFromFile[0];
                        switch (taskType) {
                            case "T":
                                ToDos td = new ToDos(parsedTaskFromFile[2]);
                                if (Integer.parseInt(parsedTaskFromFile[1]) == 0) {
                                   td.markAsDone();
                                } else {
                                    td.markAsUndone();
                                }
                                taskList.add(td);
                                break;

                            case "E":
                                Event ev = new Event(parsedTaskFromFile[2], parsedTaskFromFile[3]);
                                if (Integer.parseInt(parsedTaskFromFile[1]) == 0) {
                                    ev.markAsDone();
                                } else {
                                    ev.markAsUndone();
                                }
                                taskList.add(ev);
                                break;

                            case "D":
                                Deadline dl = new Deadline(parsedTaskFromFile[2], parsedTaskFromFile[3]);
                                if (Integer.parseInt(parsedTaskFromFile[1]) == 0) {
                                    dl.markAsDone();
                                } else {
                                    dl.markAsUndone();
                                }
                                taskList.add(dl);
                                break;
                        }
                    }
                }
                sc.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
        }
    }


    /**
     * Saves the task to the storage file each time a task is entered by the user.
     */
    void save() {
        File myObj = new File(this.homeDir + "/data/storage.txt");
        myObj.delete();
        String filePath = this.homeDir + "/data/storage.txt";

        try {
            File file = new File(filePath);
            file.createNewFile();
            for (Task t : Storage.taskList) {
                StringBuilder sb = new StringBuilder();
                if (t instanceof Event) {
                    sb.append("E,");
                    if (t.isDone()) {
                        sb.append("0,");
                    } else {
                        sb.append("1,");
                    }
                    sb.append(t.getDescription()).append(",").append(((Event) t).getAt()).append("\n");
                } else if (t instanceof Deadline) {
                    sb.append("D,");
                    if (t.isDone()) {
                        sb.append("0,");
                    } else {
                        sb.append("1,");
                    }
                    sb.append(t.getDescription()).append(",").append(((Deadline) t).getBy()).append("\n");
                } else {
                    sb.append("T,");
                    if (t.isDone()) {
                        sb.append("0,");
                    } else {
                        sb.append("1,");
                    }
                    sb.append(t.getDescription()).append("\n");
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.write(sb.toString());
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
