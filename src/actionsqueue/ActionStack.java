package actionsqueue;
import java.util.EmptyStackException;

public class ActionStack {

   
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
