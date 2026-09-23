package common;

/**
 * Shared data model representing a single student record.
 *
 * NOTE FOR THE TEAM:
 * This class is shared across ALL four modules (linked list, stack/queue,
 * BST/hashing, and the SystemManager integration layer). It is NOT owned
 * by any one member. Once the team agrees on these fields, do not modify
 * this file without discussing it with everyone first — changing it will
 * break other members' code.
 */
public class Student {

    private String studentId;
    private String name;
    private String programme;
    private double marks;

    public Student(String studentId, String name, String programme, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    /**
     * Two students are considered equal if they share the same Student ID.
     * Used when checking for duplicate IDs across modules.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return studentId != null && studentId.equals(other.studentId);
    }

    @Override
    public int hashCode() {
        return studentId == null ? 0 : studentId.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s Name: %-20s Programme: %-15s Marks: %.2f",
                studentId, name, programme, marks);
    }
}
