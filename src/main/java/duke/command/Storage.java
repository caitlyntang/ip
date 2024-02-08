package duke.command;

import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Loads tasks from the file and saves tasks in file.
 */
public class Storage {
    private File file;
    private String filePath;

    /**
     * Storage constructor.
     *
     * @param filePath      Location of save file.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
        this.filePath = filePath;
    }

    /**
     * Load tasks from save file.
     *
     * @return taskList     List of tasks retrieved from save file.
     * @throws IOException  If scanner cannot read next line.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        if (!file.exists()) {
            handleFileAccessErrors();
        }

        Scanner scanner = new Scanner(file);
        ArrayList<Task> tasks = new ArrayList<Task>();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            StringTokenizer st = new StringTokenizer(s, "|");

            String t = st.nextToken().strip();
            boolean done = st.nextToken().strip().equalsIgnoreCase("1");
            String description = st.nextToken().strip();

            if (t.equalsIgnoreCase("t")) {
                tasks.add(new ToDo(description, done));

            } else if (t.equalsIgnoreCase("e")) {
                String from = st.nextToken().strip();
                tasks.add(new Event(description, done, from, st.nextToken().strip()));

            } else if (t.equalsIgnoreCase("d")) {
                tasks.add(new Deadline(description, done, st.nextToken().strip()));

            } else {
                System.out.println("File error, cannot read list");
            }
        }
        scanner.close();

        return tasks;
    }

    /**
     * Add new task to file.
     *
     * @param task          New task to save to file.
     * @throws IOException  If FileWriter cannot access/write to file.
     */
    public void addNewTask(Task task) throws IOException {
        if (!file.exists()) {
            handleFileAccessErrors();
        }

        FileWriter fw = new FileWriter(file, true);

        if (file.length() == 0) {
            fw.write(task.getSaveTask());
        } else {
            fw.write(System.lineSeparator() + task.getSaveTask());
        }

        fw.close();
    }

    /**
     * Deletes task from file.
     *
     * @param index         Index of task to be deleted.
     * @param numOfTasks    Total number of tasks in task list.
     * @throws IOException  If unable to access/read/write to file.
     */
    public void deleteTask(int index, int numOfTasks) throws IOException{
        File oldFile = file;
        File temp = new File("./data/temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp, true));

        for (int i = 0; i < numOfTasks; ++i) {
            String currentLine = br.readLine();

            if (i != index) {
                System.out.println(currentLine);

                if (i < numOfTasks - 1) {
                    bw.write(currentLine + System.lineSeparator());
                } else {
                    bw.write(currentLine);
                }
            }

        }

        bw.close();
        br.close();

        oldFile.delete();
        temp.renameTo(new File(filePath));
    }

    /**
     * Updated task saved in file.
     *
     * @param task          Updated task.
     * @param index         Index of task to be deleted.
     * @param numOfTasks    Total number of tasks in task list.
     * @throws IOException  If unable to access/read/write to file.
     */
    public void updateTask(Task task, int index, int numOfTasks) throws IOException {
        if (!file.exists()) {
            handleFileAccessErrors();
        }

        String updated = task.getSaveTask();
        File oldFile = file;
        File temp = new File("./data/temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp, true));

        for (int i = 0; i < numOfTasks; ++i) {
            String currentLine = br.readLine();

            if (i == (index - 1)) {
                bw.write(updated + System.lineSeparator());
            } else if (i != numOfTasks - 1) {
                bw.write(currentLine + System.lineSeparator());
            } else {
                bw.write(currentLine);
            }
        }

        bw.close();
        br.close();

        oldFile.delete();
        temp.renameTo(new File("./data/duke.txt"));
    }

    /**
     * Handles missing directory and file creating.
     *
     * @throws IOException  If unable to create file.
     */
    // Move to error handling class?
    private void handleFileAccessErrors() throws IOException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            new File("./data").mkdirs();
        } finally {
            file.createNewFile();
        }
    }
}