package duke.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import duke.command.DukeException;
import duke.handlers.TimeHandler;

/**
 * Event task template.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalTime to;

    /**
     * Event constructor.
     *
     * @param description   Task name or description of task.
     * @param from          Date and time the event starts.
     * @param to            Time the event ends.
     */
    public Event(String description, LocalDateTime from, LocalTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /**
     * Event constructor.
     *
     * @param description   Task name or description of task.
     * @param done          Marks task as completed/uncompleted. [True: complete, False: uncompleted]
     * @param from          Date and time the event starts.
     * @param to            Time the event ends.
     */
    public Event(String description, boolean done, String from, String to) {
        super(description);
        super.updateIsDone(done);

        try {
            TimeHandler th = new TimeHandler();
            this.from = th.parseDateTime(from);
            this.to = th.parseTime(to);
        } catch (DukeException de) {
            // Since reading in from file, will not have error
        }
    }

    public LocalDate getFromDate() {
        return from.toLocalDate();
    }

    /**
     * Formats Event as a string to be saved to file.
     * @return saveTask     Returns the task as a string in the format compatible with file.
     */
    @Override
    public String saveFileString() {
        return "E | " + super.saveFileString() + " | "
                + from.toString().replace("T", " ") + " | " + to;
    }

    @Override
    public String toString() {
        return "[E][" + (isDone ? "X" : " ") + "] "
                + this.description + " (from: "
                + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a")) + " to: "
                + this.to.format(DateTimeFormatter.ofPattern("hh:mm a")) + ")";
    }
}
