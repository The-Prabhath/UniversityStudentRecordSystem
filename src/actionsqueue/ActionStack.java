package actionsqueue;

import java.util.EmptyStackException;

/**
 * Custom stack (LIFO) implementation used to track recent actions
 * performed on student records (add/update/delete), giving the system
 * an undo/history feature.
 *
 * Owned by: Member 2
 * Covers assignment requirement 3.
 *
 * Implemented internally using a singly linked structure so the team is
 * not relying on java.util.Stack — this keeps the "practical use of
 * data structures" requirement genuine rather than just wrapping a
 * built-in collection.
 *
 * INTEGRATION NOTE:
 * SystemManager calls push() every time a record is added, updated, or
 * deleted elsewhere in the system (linked list, BST, hash table). This
 * class does not know about Student, StudentLinkedList, or any other
 * module — it only stores ActionRecord objects, which keeps it fully
 * independent and testable on its own.
 */
public class ActionStack {

    /** Internal node for the stack's linked structure. */
    private static class StackNode {
        ActionRecord data;
        StackNode next;

        StackNode(ActionRecord data) {
            this.data = data;
        }
    }

    private StackNode top;
    private int size;

    public ActionStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes a new action onto the top of the stack.
     */
    public void push(ActionRecord record) {
        if (record == null) {
            return;
        }
        StackNode newNode = new StackNode(record);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the most recent action from the stack.
     * Used for "undo" style features.
     *
     * @return the most recent ActionRecord, or null if the stack is empty
     */
    public ActionRecord pop() {
        if (isEmpty()) {
            return null;
        }
        ActionRecord data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Returns the most recent action without removing it.
     */
    public ActionRecord peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int getSize() {
        return size;
    }

    /**
     * Displays recent actions, most recent first.
     * Satisfies menu item 7: "Display Recent Actions using Stack".
     */
    public void displayRecentActions() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }

        System.out.println("---- Recent Actions (most recent first) ----");
        StackNode current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.data);
            current = current.next;
            count++;
        }
    }
}
