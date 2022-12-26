package interface_form.list;

import java.util.Arrays;

public class ArrayList<E> implements List<E>, Cloneable{

    private static final  int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    private int size;

    Object[] array;

    public ArrayList() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    public ArrayList(int capacity) {
        this.array = new Object[capacity];
        this.size=0;
    }

    private void resize() {
        int array_capacity = array.length;

        if(Arrays.equals(array,EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }

        if (size==array_capacity) {
            int new_capacity = array_capacity * 2;
            array = Arrays.copyOf(array, new_capacity);
            return;
        }

        if (size<(array_capacity/2)) {
            int new_capacity = array_capacity / 2;
            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
            return;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        if (size==array.length) {
            resize();
        }
        array[size] = value;
        size++;
    }

    @Override
    public void add(int index, E value) {
        if (index<0 || index>0) {
            throw new IndexOutOfBoundsException();
        }

        if(index==size) {
            addLast(value);
        } else {
            if (size==array.length) {
                resize();
            }

            for (int i=size;i>index;i--) {
                array[i]=array[i-1];
            }
            array[index] = value;
            size++;
        }
    }

    public void addFirst(E value) {
        add(0, value);
    }

    @Override
    public E remove(int index) {
        if (index>=size || index<0) {
            throw new IndexOutOfBoundsException();
        }

        E element = (E) array[index];
        array[index] = null;

        for (int i=index;i<size-1;i++) {
            array[i] = array[i+1];
            array[i + 1] = null;
        }
        size--;
        resize();
        return element;
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);

        if (index==-1) {
            return false;
        }

        remove(index);
        return true;
    }

    @Override
    public E get(int index) {
        if (index>=size || index<0) {
            throw new IndexOutOfBoundsException();
        }

        return (E) array[index];
    }

    @Override
    public void set(int index, E value) {
        if (index>=size || index<0) {
            throw new IndexOutOfBoundsException();
        } else {
            array[index] = value;
        }
    }

    @Override
    public boolean contains(Object value) {
        if(indexOf(value)>=0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(Object value) {

        for (int i=0;i<size;i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object value) {

        for (int i=size-1;i>=0;i--) {
            if(array[i].equals(value)) {
                return i;
            }
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
        for (int i=0;i<size;i++) {
            array[i]=null;
        }
        size=0;
        resize();
    }

    public Object clone() throws CloneNotSupportedException {
        ArrayList<?> cloneList = (ArrayList<?>)super.clone();

        cloneList.array = new Object[size];

        System.arraycopy(array,0, cloneList.array,0,size);
        return cloneList;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length<size) {
            return  (T[])Arrays.copyOf(array, size, a.getClass());
        }

        System.arraycopy(array,0,a,0,size);
        return a;
    }
}
