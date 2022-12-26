package interface_form.stack;

public interface StackInterface<E> {
    E push(E item);

    E pop();

    E peek();

    int search(Object value);

    int size();

    void clear();

    boolean empty();
}
