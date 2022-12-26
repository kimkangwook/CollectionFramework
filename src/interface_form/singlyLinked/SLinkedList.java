package interface_form.singlyLinked;

import interface_form.list.List;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SLinkedList<E> implements List<E>, Cloneable{

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SLinkedList() {
        this.head=null;
        this.tail=null;
        this.size=0;
    }

    private Node<E> search(int index) {

        if(index<0||index>=size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> x = head;

        for (int i=0;i<index;i++) {
            x = x.next;
        }
        return x;
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        if (size==0) {
            addFirst(value);
            return;
        }

        Node<E> newNode = new Node<E>(value);
        tail.next=newNode;
        tail=newNode;
        size++;

    }


    @Override
    public void add(int index, E value) {


        if(index<0 || index>size) {
            throw new IndexOutOfBoundsException();
        }

        if(index==0) {
            addFirst(value);
            return;
        }

        if(index==size) {
            addLast(value);
            return;
        }

        Node<E> prev_Node = search(index - 1);
        Node<E> next_Node = prev_Node.next;
        Node<E> newNode = new Node<>(value);

        prev_Node.next=null;
        prev_Node.next = newNode;
        newNode.next = next_Node;
        size++;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<E>(value);
        newNode.next=head;
        head=newNode;
        size++;

        if(head.next==null) {
            tail=head;
        }

    }

    @Override
    public E remove(int index) {
        if(index==0) {
            return remove();
        }

        if (index>=size || index<0) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> prevNode = search(index - 1);
        Node<E> removedNode = prevNode.next;
        Node<E> nextNode = removedNode.next;

        E element = removedNode.data;

        prevNode.next = nextNode;

        if(nextNode==null) {
            tail=prevNode;
        }

        removedNode.data=null;
        removedNode.next=null;

        size--;

        return element;
    }

    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        boolean hasValue = false;
        Node<E> x = head;

        for(;x!=null;x=x.next) {
            if (value.equals(x.data)) {
                hasValue=true;
                break;
            }
            prevNode=x;
        }

        if(x==null) {
            return false;
        }

        if(x==head) {
            remove();
            return true;
        } else {
            prevNode.next = x.next;

            if (prevNode.next==null) {
                tail=prevNode;
            }
            x.data=null;
            x.next=null;
            size--;
            return true;
        }


    }

    public E remove() {
        Node<E> headNode = head;
        if(headNode==null) {
            throw new NoSuchElementException();
        }

        E element = head.data;
        Node<E> nextNode = head.next;

        head.data=null;
        head.next=null;

        head = nextNode;
        size--;

        if(size==0) {
            tail=null;
        }

        return element;

    }

    @Override
    public E get(int index) {
         return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data=null;
        replaceNode.data=value;

    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value)>=0;
    }

    @Override
    public int indexOf(Object value) {
        int index=0;
        for (Node<E> x=head;x!=null;x=x.next) {
            if(value.equals(x.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        for(Node<E> x=head;x!=null;) {
            Node<E> nextNode = x.next;
            x.data=null;
            x.next=null;
            x=nextNode;
        }
        head=tail=null;
        size=0;
    }

    public Object clone() throws CloneNotSupportedException {
        SLinkedList<? super E> clone = (SLinkedList<? super E>)super.clone();
        clone.head=null;
        clone.tail=null;
        clone.size=0;

        for(Node<E> x = head;x!=null;x=x.next) {
            clone.addLast(x.data);
        }

        return clone;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx=0;
        for (Node<E> x=head;x!=null;x=x.next) {
            array[idx++] = (E)x.data;
        }
        return array;
    }

    public <T> T[] toArray(T[] a) {
        if (a.length<size) {
             a= (T[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i=0;
        Object[] result =a;
        for (Node<E> x = head; x!=null;x=x.next) {
            result[i++]=x.data;
        }
        return a;
    }

    public void sort() {
        sort(null);
    }

    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator)c);
        int i=0;
        for (Node<E> x=head;x!=null;x=x.next,i++) {
            x.data = (E)a[i];
        }
    }
}
