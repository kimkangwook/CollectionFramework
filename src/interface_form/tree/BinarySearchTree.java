package interface_form.tree;

import java.util.Comparator;

public class BinarySearchTree<E> {

    private Node<E> root;
    private int size;

    private final Comparator<? super E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public boolean add(E value) {
        if(comparator==null) {
            return addUsingComparable(value) == null;
        }
        return addUsingComparator(value, comparator) == null;
    }

    public E remove(Object o) {

        if(root == null) {
            return null;
        }
        if(comparator==null) {
            return removeUsingComparable(o);
        } else {
            return removeUsingComparator(o, comparator);

        }
    }

    private E removeUsingComparable(Object o) {
        E oldVal = (E) o;
        Node<E>  parent =null, current = root;
        boolean hasLeft = false;

        if(root==null) {
            return null;
        }

        Comparable<? super E> compValue = (Comparable<? super E>) o;

        do {
            int resComp = compValue.compareTo(current.value);
            if(resComp==0) {
                break;
            }
            parent=current;
            if(resComp<0) {
                hasLeft=true;
                current = current.left;
            } else {
                hasLeft=false;
                current = current.right;
            }
        } while (current != null);

        if (current == null) {
            return null;
        }

        if(parent ==null) {
            deleteNode(current);
            size--;
            return oldVal;
        }

        if(hasLeft) {
            parent.left = deleteNode(current);

            if (parent.left !=null ) {
                parent.left.parent=parent;
            }
        } else {
            parent.right = deleteNode(current);
            if(parent.right!=null) {
                parent.right.parent=parent;
            }
        }

        size--;
        return oldVal;
    }

    private E removeUsingComparator(Object o, Comparator<? super E> comp) {

        E oldVal = (E) o;
        Node<E>  parent =null, current = root;
        boolean hasLeft = false;

        if(root==null) {
            return null;
        }

        E compValue = (E) o;

        do {
            int resComp = comp.compare(compValue,current.value);
            if(resComp==0) {
                break;
            }
            parent=current;
            if(resComp<0) {
                hasLeft=true;
                current = current.left;
            } else {
                hasLeft=false;
                current = current.right;
            }
        } while (current != null);

        if (current == null) {
            return null;
        }

        if(parent ==null) {
            deleteNode(current);
            size--;
            return oldVal;
        }

        if(hasLeft) {
            parent.left = deleteNode(current);

            if (parent.left !=null ) {
                parent.left.parent=parent;
            }
        } else {
            parent.right = deleteNode(current);
            if(parent.right!=null) {
                parent.right.parent=parent;
            }
        }

        size--;
        return oldVal;
    }

    private E addUsingComparable(E value) {

        Node<E> current = root;

        if(current==null) {
            root = new Node<E>(value);
            size++;
            return null;
        }

        Node<E> currentParent;

        Comparable<? super E> compValue = (Comparable<? super E>)value;

        int compResult;

        do {
            currentParent = current;
            compResult = compValue.compareTo(current.value);

            if(compResult<0) {
                current = current.left;
            } else if (compResult>0) {
                current = current.right;
            } else {
                return value;
            }

        } while (current != null);

        Node<E> newNode = new Node<E>(value, currentParent);

        if(compResult <0) {
            currentParent.left = newNode;
        } else {
            currentParent.right = newNode;
        }
        size++;
        return null;

    }

    private E addUsingComparator(E value, Comparator<? super E> comp) {

        Node<E> current = root;
        if(current==null) {
            root = new Node<E>(value,null);
            size++;
            return null;
        }

        Node<E> currentParent;
        int compResult;
        do {
            currentParent = current;
            compResult = comp.compare(value, current.value);
            if(compResult<0) {
                current = current.left;
            } else if(compResult>0) {
                current = current.right;
            } else {
                return value;
            }

        } while (current != null);

        Node<E> newNode = new Node<E>(value, currentParent);
        if(compResult<0) {
            currentParent.left=newNode;
        } else {
            currentParent.right=newNode;
        }
        size++;
        return null;
    }


    private Node<E> getSuccessorAndUnlink(Node<E> node) {

        Node<E> currentParent = node;
        Node<E> current = node.right;

        if (current.left==null) {
            currentParent.right=current.right;
            if(currentParent.right!=null) {
                currentParent.right.parent=currentParent;
            }
            current.right=null;
            return current;
        }

        while(current.left !=null) {
            currentParent = current;
            current = current.left;
        }

        currentParent.left = current.right;
        if(currentParent.left != null) {
            currentParent.left.parent = currentParent;
        }
        current.right=null;
        return current;
    }

    private Node<E> deleteNode(Node<E> node) {

        if (node != null) {
            if (node.left == null && node.right == null) {
                if (node == root) {
                    root = null;
                } else {
                    node = null;
                }
                return null;
            }


        if (node.left != null && node.right != null) {
            Node<E> replacement = getSuccessorAndUnlink(node);

            node.value = replacement.value;
        } else if (node.left != null) {

            if (node == root) {
                node = node.left;
                root = node;
                root.parent = null;
            } else {
                node = node.left;
            }
        } else {

            if (node == root) {
                node = node.right;
                root = node;
                root.parent = null;
            } else {
                node = node.right;
            }
        }

    }
        return node;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size()==0;
    }

    public boolean contains(Object o) {
        if(comparator==null) {
            return containsUsingComparable(o);
        }
        return containsUsingComparator(o, comparator);
    }

    private boolean containsUsingComparable(Object o) {

        Comparable<? super E> value = (Comparable<? super E>) o;
        Node<E> node = root;
        while(node!=null) {
            int res = value.compareTo(node.value);
            if(res<0) {
                node = node.left;
            } else if (res>0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean containsUsingComparator(Object o, Comparator<? super E> comp) {

        E value = (E) o;
        Node<E> node = root;
        while(node!=null) {
            int res = comp.compare(value,node.value);
            if(res<0) {
                node = node.left;
            } else if (res>0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        size=0;
        root=null;
    }

    public void preorder() {
        preorder(this.root);
    }

    public void preorder(Node<E> o) {
        if (o!=null) {
            System.out.print(o.value + " ");
            preorder(o.left);
            preorder(o.right);
        }
    }

    public void inorder() {
        inorder(this.root);
    }

    public void inorder(Node<E> o) {
        if(o!=null) {
            inorder(o.left);
            System.out.print(o.value + " ");
            inorder(o.right);
        }
    }

    public void postorder() {
        postorder(this.root);
    }

    public void postorder(Node<E> o) {
        if(o!=null) {
            postorder(o.left);
            postorder(o.right);
            System.out.print(o.value+ " ");
        }
    }

    public void reverseInorder() {
        reverseInorder(this.root);
    }

    public void reverseInorder(Node<E> o) {
        if (o!=null) {
            reverseInorder(o.right);
            System.out.print(o.value + " ");
            reverseInorder(o.left);
        }
    }

}
