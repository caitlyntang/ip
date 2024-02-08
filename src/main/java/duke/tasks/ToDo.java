package duke.tasks;

public class ToDo extends Task {
    public ToDo (String description) {
        super(description);
    }

    public ToDo (String description, boolean done) {
        super(description);
        super.updateIsDone(done);
    }

    @Override
    public String saveFileString() {
        return "T | " + super.saveFileString();
    }

    @Override
    public String toString() {
        return "[T][" + this.printDoneStatus() + "] " + this.description;
    }
}