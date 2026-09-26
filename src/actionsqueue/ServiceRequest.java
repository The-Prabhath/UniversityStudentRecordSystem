package actionsqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single student service request (e.g. "Transcript Request",
 * "Grade Appeal", "ID Card Replacement") waiting to be processed in
 * order of arrival.
 *
 * Owned by: Member 2
 */
public class ServiceRequest {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String studentId;
    private final String requestType;
    private final LocalDateTime timeSubmitted;

    public ServiceRequest(String studentId, String requestType) {
        this.studentId = studentId;
        this.requestType = requestType;
        this.timeSubmitted = LocalDateTime.now();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getRequestType() {
        return requestType;
    }

    public LocalDateTime getTimeSubmitted() {
        return timeSubmitted;
    }

    @Override
    public String toString() {
        return String.format("Student ID: %s | Request: %s | Submitted: %s",
                studentId, requestType, timeSubmitted.format(TIME_FORMAT));
    }
}
