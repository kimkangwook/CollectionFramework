package interface_form.stack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EmptyStackException;

public class Stack<E> implements StackInterface<E>,Cloneable{

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    private Object[] array;
    private int size;

    public Stack() {
        this.array = EMPTY_ARRAY;
        this.size=0;
    }

    public Stack(int capacity) {
        this.array = new Object[capacity];
        this.size=0;
    }

    private void resize() {

        if(Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        int arrayCapacity = array.length;

        if (size==arrayCapacity) {
            int newSize=arrayCapacity*2;

            array=Arrays.copyOf(array, newSize);
            return;
        }

        if (size<(arrayCapacity/2)) {
            int newCapacity = (arrayCapacity/2);
            array = Arrays.copyOf(array,Math.max(newCapacity,DEFAULT_CAPACITY));
            return;
        }
    }

    @Override
    public E push(E item) {
        if (size==array.length) {
            resize();
        }
        array[size]=item;
        size++;

        return item;
    }

    @Override
    public E pop() {
        if (size==0) {
            throw new EmptyStackException();
        }

        E obj = (E) array[size - 1];
        array[size-1]=null;
        size--;
        resize();
        return obj;
    }

    @Override
    public E peek() {
        if (size==0) {
            throw new EmptyStackException();
        }
        return (E)array[size - 1];
    }

    @Override
    public int search(Object value) {
        if (value==null) {
            for(int idx=size-1;idx>=0;idx--) {
                if(array[idx]==null) {
                    return size-idx;
                }
            }
        } else {
            for (int idx=size-1;idx>=0;idx--) {
                if(array[idx].equals(value)) {
                    return size-idx;
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

        for (int i=0;i<size;i++) {
            array[i]=null;
        }
        size=0;
        resize();

    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    public Object clone() throws CloneNotSupportedException {
        Stack<?> cloneStack = (Stack<?>)super.clone();
        cloneStack.array = new Object[size];
        System.arraycopy(array, 0 , cloneStack.array,0,size);
        return cloneStack;
    }

    public Object[] toArray() {
       return Arrays.copyOf(array, size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length<size) {
           return (T[])Arrays.copyOf(array, size, a.getClass());
        }

        System.arraycopy(array,0,a,0,size);
        return a;
    }

    public void sort() {
        sort(null);
    }

    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[])array, 0, size, c);
    }
}
