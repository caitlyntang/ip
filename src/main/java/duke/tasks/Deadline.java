package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline task template.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Deadline constructor.
     *
     * @param description   Task name or description of task.
     * @param by            Date and time task has to be completed by.
     */
    public Deadline (String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Deadline constructor.
     *
     * @param description   Task name or description of task.
     * @param done          Marks task as completed/uncompleted. [True: complete, False: uncompleted]
     * @param by            Date and time task has to be completed by.
     */
    public Deadline (String description, boolean done, String by) {
        super(description);
        super.updateIsDone(done);
        this.by = parseDateTime(by);
    }

    /**
     * Formats Deadline as a string to be saved to file.
     *
     * @return saveTask     Returns the task as a string in the format compatible with file.
     */
    @Override
    public String getSaveTask() {
        return "D | " + super.getSaveTask() + " | " + by.toString().replace("T", " ");
    }

    @Override
    public String toString() {
        return "[D][" + this.getStatusIcon() + "] " + this.description + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a")) + ")";
    }
}