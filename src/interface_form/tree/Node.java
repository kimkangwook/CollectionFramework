package interface_form.tree;

public class Node<E> {

    E value;

    Node<E> left;
    Node<E> right;
    Node<E> parent;

    Node(E value) {
        this(value, null);
    }

    Node(E value, Node<E> parent) {
        this.value=value;
        this.parent=parent;
        this.left=this.right=null;
    }
}
