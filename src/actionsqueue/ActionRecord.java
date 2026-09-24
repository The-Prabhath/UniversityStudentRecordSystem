package actionsqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ActionRecord {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String actionType;   
    private final String studentId;
    private final String details;      
    private final LocalDateTime timestamp;

    public ActionRecord(String actionType, String studentId, String details) {
        this.actionType = actionType;
        this.studentId = studentId;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getActionType() {
        return actionType;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Student ID: %s - %s",
                timestamp.format(TIME_FORMAT), actionType, studentId, details);
    }
}
