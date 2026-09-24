package actionsqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single recorded action (e.g. "Added", "Updated", "Deleted")
 * performed on a student record. Instances are pushed onto the
 * ActionStack so recent actions / history can be displayed, satisfying
 * requirement 3 (recent actions / deleted records / undo history).
 *
 * Owned by: Member 2
 */
public class ActionRecord {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String actionType;   // e.g. "ADD", "UPDATE", "DELETE"
    private final String studentId;
    private final String details;      // short human-readable description
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
