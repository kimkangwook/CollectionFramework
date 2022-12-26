package interface_form.queue.linkedlist;

import interface_form.queue.Queue;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedListQueue<E> implements Queue<E>,Cloneable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListQueue() {
        this.head=null;
        this.tail=null;
        this.size=0;
    }

    @Override
    public boolean offer(E e) {
        Node<E> newNode = new Node<>(e);

        if(size==0) {
            head=newNode;
        } else {
            tail.next=newNode;
        }
        tail=newNode;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if(size==0) {
            return null;
        }

        E element = head.data;

        Node<E> nextNode = head.next;

        head.data=null;
        head.next=null;

        head=nextNode;
        size--;

        return element;
    }

    public E remove() {
        E element = poll();

        if(element==null) {
            throw new NoSuchElementException();
        }

        return element;
    }

    @Override
    public E peek() {
        if(size==0) {
            return null;
        }

        return head.data;
    }

    public E element() {
        E element = peek();
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
        for (Node<E> x=head;x!=null;x=x.next) {
            if(value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }
    public void clear() {
        for (Node<E> x=head;x!=null;) {
            Node<E> next = x.next;
            x.data=null;
            x.next=null;
            x = next;
        }
        size=0;
        head=tail=null;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx=0;
        for (Node<E> x=head;x!=null;x=x.next) {
            array[idx++]=(E)x.data;
        }
        return array;
    }

    public <T> T[] toArray(T[] a) {
        if(a.length < size) {
            a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i=0;
        Object[] result=a;
        for (Node<E> x=head;x!=null;x=x.next) {
            result[i++] = x.data;
        }
        return a;
    }

    public Object clone() {
        try {
            LinkedListQueue<E> clone = (LinkedListQueue<E>)super.clone();
            clone.head=null;
            clone.tail=null;
            clone.size=0;

            for (Node<E> x=head;x!=null;x=x.next) {
                clone.offer(x.data);
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
        Arrays.sort(a, (Comparator)c);
        int i=0;
        for(Node<E> x=head;x!=null;x=x.next,i++) {
            x.data = (E) a[i];
        }
    }


}
