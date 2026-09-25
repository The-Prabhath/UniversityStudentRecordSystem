package studentrecords;

import common.Student;

/**
 * A single node in the singly linked list of student records.
 * Owned by: Member 1
 */
public class StudentNode {

    private Student student;
    private StudentNode next;

    public StudentNode(Student student) {
        this.student = student;
        this.next = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentNode getNext() {
        return next;
    }

    public void setNext(StudentNode next) {
        this.next = next;
    }
}
