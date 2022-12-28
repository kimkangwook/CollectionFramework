package interface_form.queue.priorityqueue;

import interface_form.queue.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E> implements Queue<E>, Cloneable{

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] array;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size=0;
        this.comparator = comparator;
    }

    public PriorityQueue(int capacity) {
        this(capacity, null);
    }

    public PriorityQueue(int capacity, Comparator<? super E> comparator) {
        this.array = new Object[capacity];
        this.size=0;
        this.comparator = comparator;
    }

    private int getParent(int index) {
        return index / 2;
    }

    private int getLeftChild(int index) {
        return index*2;
    }

    private int getRightChild(int index) {
        return index*2+1;
    }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];

        for(int i=1;i<=size;i++) {
            newArray[i] = array[i];
        }

        this.array = null;
        this.array = newArray;
    }
    @Override
    public boolean offer(E value) {
        if(size+1==array.length) {
            resize(array.length * 2);
        }

        siftUp(size + 1, value);
        size++;
        return true;
    }

    private void siftUp(int idx, E target) {
        if(comparator!=null) {
            siftUpComparator(idx, target, comparator);
        } else {
            siftUpComparable(idx, target);
        }
    }

    private void siftUpComparator(int idx, E target, Comparator<? super E> comp) {
        while(idx>1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if(comp.compare(target,(E)parentVal)>=0) {
                break;
            }

            array[idx] = parentVal;
            idx=parent;
        }
        array[idx]=target;
    }

    private void siftUpComparable(int idx, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;

        while(idx>1) {
            int parent = getParent(idx);
            Object parentVal = array[parent];

            if((comp.compareTo((E)parentVal))>=0) {
                break;
            }

            array[idx] = parentVal;
            idx=parent;
        }
        array[idx]=comp;
    }


    @Override
    public E poll() {
        if(array[1] == null) {
            return null;
        }

        return remove();
    }

    public E remove() {
        if(array[1]==null) {
            throw new NoSuchElementException();
        }

        E result = (E) array[1];
        E target = (E) array[size];

        array[size]=null;
        size--;
        siftDown(1, target);
        return result;
    }

    private void siftDown(int idx,E target) {
        if(comparator!=null) {
            siftDownComparator(idx, target, comparator);
        } else {
            siftDownComparable(idx, target);
        }
    }

    private void siftDownComparator(int idx, E target, Comparator<? super E> comp) {
        array[idx]=null;

        int parent = idx;
        int child;

        while((child=getLeftChild(parent))<=size) {
            int right = getRightChild(parent);
            Object childVal = array[child];

            if(right<=size && comp.compare((E)array[right],(E)childVal)<0) {
                child = right;
                childVal = array[child];
            }

            if(comp.compare((E)target, (E)childVal)<=0) {
                break;
            }

            array[parent]=childVal;
            parent=child;
        }

        array[parent]=target;

        if(array.length>DEFAULT_CAPACITY && size < array.length/4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    private void siftDownComparable(int idx, E target) {
        Comparable<? super E> comp = (Comparable<? super E>) target;
        array[idx]=null;

        int parent = idx;
        int child;

        while((child=parent<<1)<=size) {
            int right = child+1;
            Object childVal = array[child];

            if(right<=size && ((Comparable<? super E>)childVal).compareTo((E)array[right])>0) {
                child = right;
                childVal = array[child];
            }

            if(comp.compareTo((E)childVal)<=0) {
                break;
            }

            array[parent]=childVal;
            parent=child;
        }

        array[parent]=comp;

        if(array.length>DEFAULT_CAPACITY && size < array.length/4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    public int size() {
        return size;
    }

    @Override
    public E peek() {
        if(array==null) {
            throw new NoSuchElementException();
        }
        return (E) array[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        for(int i=1;i<=size;i++) {
            if(array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i=0;i< array.length;i++) {
            array[i]=null;
        }
        size=0;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length<=size) {
            return (T[]) Arrays.copyOfRange(array, 1, size + 1, a.getClass());
        }
        System.arraycopy(array,1,a,0,size);
        return a;
    }

    public Object clone() {
        try {
            PriorityQueue<?> cloneHeap = (PriorityQueue<?>) super.clone();
            cloneHeap.array=new Object[size+1];
            System.arraycopy(array,0,cloneHeap.array,0,size+1);
            return cloneHeap;

        } catch(CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
