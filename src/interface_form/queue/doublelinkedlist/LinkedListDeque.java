package interface_form.queue.doublelinkedlist;

import interface_form.queue.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedListDeque<E> implements Queue<E>, Cloneable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListDeque() {
        this.head=null;
        this.tail=null;
        this.size=0;
    }

    @Override
    public boolean offer(E item) {
        return offerLast(item);
    }

    public boolean offerLast(E item) {
        if (size==0) {
            return offerFirst(item);
        }

        Node<E> newNode = new Node<E>(item);

        tail.next=newNode;
        newNode.prev=tail;
        tail=newNode;
        size++;

        return true;
    }

    public boolean offerFirst(E item) {
        Node<E> newNode = new Node<E>(item);
        newNode.next=head;

        if(head!=null) {
            head.prev = newNode;
        }

        head=newNode;
        size++;

        if(head.next==null) {
            tail=head;
        }
        return true;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        if (size==0) {
            return null;
        }

        E element = head.data;
        Node<E> nextNode = head.next;

        head.next=null;
        head.data=null;

        if(nextNode !=null) {
            nextNode.prev=null;
        }
        head=null;
        head=nextNode;
        size--;

        if(size==0) {
            tail=null;
        }
        return element;

    }

    public E remove() {
        return removeFirst();
    }

    public E removeFirst() {
        E element = pollFirst();

        if(element==null) {
            throw new NoSuchElementException();
        }

        return element;
    }

    public E pollLast() {
        if(size==0) {
            return null;
        }

        E element = tail.data;
        Node<E> prevNode = tail.prev;

        tail.data=null;
        tail.prev=null;

        if(prevNode!=null) {
            prevNode.next=null;
        }

        tail=null;
        tail=prevNode;
        size--;

        if(size==0) {
            head=null;
        }
        return element;
    }

    public E removeLast() {
        E element = pollLast();

        if(element==null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    private E peekFirst() {
        if(size==0) {
            return null;
        }

        return head.data;
    }

    public E peekLast() {
        if(size==0) {
            return null;
        }

        return tail.data;
    }

    public E element() {
        return getFirst();
    }

    public E getFirst() {
        E element = peekFirst();
        if(element==null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    public E getLast() {
        E element = peekLast();
        if(element==null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object value) {
        for(Node<E> x=head;x!=null;x=x.next) {
            if(value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for(Node<E> x=head;x!=null;) {
            Node<E> next=x.next;

            x.data=null;
            x.next=null;
            x.prev=null;
            x=x.next;
        }
        size=0;
        head=tail=null;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx=0;
        for(Node<E> x=head;x!=null;x=x.next) {
            array[idx++]=(E)x.data;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if(a.length<size) {
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i=0;
        Object[] result = a;
        for(Node<E> x=head;x!=null;x=x.next) {
            result[i++]=x.data;
        }
        return a;
    }

    public Object clone() {
        try {
            @SuppressWarnings("unchecked")
            LinkedListDeque<E> clone = (LinkedListDeque<E>) super.clone();
            clone.head=null;
            clone.tail=null;
            clone.size=0;

            for (Node<E> x=head;x!=null;x=x.next) {
                clone.offerLast(x.data);
            }
            return clone;
        } catch(CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public void sort() {
        sort(null);
    }

    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a,(Comparator) c);

        int i=0;
        for(Node<E> x=head;x!=null;x=x.next,i++) {
            x.data = (E)a[i];
        }
    }

}
