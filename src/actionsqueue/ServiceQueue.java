package actionsqueue;

/**
 * Custom queue (FIFO) implementation used to manage student service
 * requests in the order they arrive.
 *
 *
 * Implemented internally using a singly linked structure with head/tail
 * pointers for O(1) enqueue and dequeue, rather than relying on
 * java.util.Queue, to keep the "practical use of data structures"
 * requirement genuine.
 *
 * INTEGRATION NOTE:
 * This class only stores ServiceRequest objects and does not depend on
 * Student, StudentLinkedList, or any other module. SystemManager is
 * responsible for calling enqueue() (menu item 5: "Add Service Request
 * to Queue") and dequeue() (menu item 6: "Process Next Service
 * Request"), optionally logging the processing step to Member 2's own
 * ActionStack if the team wants processed requests to appear in the
 * action history too.
 */
public class ServiceQueue {

    /** Internal node for the queue's linked structure. */
    private static class QueueNode {
        ServiceRequest data;
        QueueNode next;

        QueueNode(ServiceRequest data) {
            this.data = data;
        }
    }

    private QueueNode front;
    private QueueNode rear;
    private int size;

    public ServiceQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds a new service request to the back of the queue.
     * Satisfies menu item 5: "Add Service Request to Queue".
     */
    public void enqueue(ServiceRequest request) {
        if (request == null) {
            return;
        }
        QueueNode newNode = new QueueNode(request);
        if (rear == null) {
            // Queue was empty
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the request at the front of the queue (the one
     * that has been waiting longest).
     * Satisfies menu item 6: "Process Next Service Request".
     *
     * @return the next ServiceRequest to process, or null if the queue
     *         is empty (satisfies requirement 14: handling an empty
     *         queue gracefully instead of crashing).
     */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest data = front.data;
        front = front.next;
        if (front == null) {
            // Queue is now empty
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Returns the request at the front of the queue without removing it.
     */
    public ServiceRequest peekFront() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int getSize() {
        return size;
    }

    /**
     * Displays all pending service requests, in arrival order.
     */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }

        System.out.println("---- Pending Service Requests (arrival order) ----");
        QueueNode current = front;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.data);
            current = current.next;
            count++;
        }
    }
}
