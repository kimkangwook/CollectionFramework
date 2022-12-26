package interface_form.queue.doublelinkedlist;

public class Node<E> {
    E data;
    Node<E> next;
    Node<E> prev;

    Node(E data) {
        this.data=data;
        this.prev=null;
        this.prev=null;
    }
}
