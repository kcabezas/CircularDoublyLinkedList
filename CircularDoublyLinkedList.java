/**
 * Your implementation of a CircularDoublyLinkedList
 *
 * @author Katherine Cabezas
 * @version 1.0
 */
public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws java.lang.IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }

        for (T t : data) {
            addToBack(t);
        }
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index is out of bounds.");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> current = head;
            LinkedListNode<T> node = new LinkedListNode<T>(data);
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            LinkedListNode<T> temp = current;
            node.setPrevious(temp.getPrevious());
            node.setNext(temp);
            temp.getPrevious().setNext(node);
            temp.setPrevious(node);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index is out of bounds.");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return head.getPrevious().getData();
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException(
                "Index is out of bounds.");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            T data = current.getData();
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            size--;
            return data;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }

        LinkedListNode<T> node = new LinkedListNode<T>(data);
        if (head == null) {
            node.setNext(node);
            node.setPrevious(node);
            head = node;
        } else {
            LinkedListNode<T> temp = head.getPrevious();
            temp.setNext(node);
            node.setPrevious(temp);
            node.setNext(head);
            head.setPrevious(node);
            head = node;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }

        LinkedListNode<T> node = new LinkedListNode<T>(data);
        if (head == null) {
            node.setNext(node);
            node.setPrevious(node);
            head = node;
        } else {
            LinkedListNode<T> temp = head.getPrevious();
            temp.setNext(node);
            node.setPrevious(temp);
            node.setNext(head);
            head.setPrevious(node);
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }

        T data = head.getData();
        if (size == 1) {
            clear();
        } else {
            LinkedListNode<T> temp = head.getNext();
            temp.setPrevious(head.getPrevious());
            head.getPrevious().setNext(temp);
            head = temp;
            size--;
        }
        return data;
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }

        T data = head.getPrevious().getData();
        if (size == 1) {
            clear();
        } else {
            LinkedListNode<T> temp = head.getPrevious().getPrevious();
            head.setPrevious(temp);
            temp.setNext(head);
            size--;
        }
        return data;
    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }

        if (head.getData().equals(data)) {
            removeFromFront();
            return 0;
        } else {
            int index = 0;
            boolean removed = false;
            LinkedListNode<T> current = head;
            while (index != size && !removed) {
                if (current.getData().equals(data)) {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                    removed = true;
                } else {
                    index++;
                }
                current = current.getNext();
            }

            if (!removed) {
                throw new java.util.NoSuchElementException(
                    "Data was not found in list.");
            }

            return index;
        }
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }

        LinkedListNode<T> current = head;
        int length = size;
        boolean removed = false;
        for (int i = 0; i < length; i++) {
            if (current.getData().equals(data)) {
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                if (current == head) {
                    head = current.getNext();
                }
                size--;
                removed = true;
            }
            current = current.getNext();
        }

        return removed;
    }

    @Override
    public Object[] toArray() {
        Object[] list = (T[]) new Object[size];
        if (!isEmpty()) {
            LinkedListNode<T> current = head;
            for (int i = 0; i < size; i++) {
                list[i] = current.getData();
                current = current.getNext();
            }
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /* DO NOT MODIFY THIS METHOD */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}
