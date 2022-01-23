import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    String homeDir;
    ArrayList<Task> taskList;

    Storage() {
        this.homeDir = System.getProperty("user.dir");
        this.taskList = new ArrayList<>();
    }

    void load() {
        boolean directoryExists = new java.io.File(this.homeDir + "/data").exists();
        if (!directoryExists) {
            try {
                Files.createDirectories(Paths.get(this.homeDir + "/data"));
                File myObj = new File(this.homeDir + "/data/storage.txt");
                myObj.createNewFile();

            } catch (IOException e) {
                System.out.println("Load failed.");
            }
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
}