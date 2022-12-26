package interface_form.queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E>,Cloneable {

    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;

    private int front; //시작 위치(빈공간)
    private int rear; // 마지막 요소의 위치(index)

    public ArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0 ;
        this.front = 0;
        this.rear = 0;
    }

    public ArrayQueue(int capacity) {
        this.array = new Object[capacity];
        this.size = 0 ;
        this.front = 0;
        this.rear = 0;
    }

    public void resize(int newCapacity) {
        int arrayCapacity = array.length;

        Object[] newArray = new Object[newCapacity];

        for (int i=1, j=front+1;i<=size;i++,j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array=null;
        this.array=newArray;

        front=0;
        rear=size;
    }

    @Override
    public boolean offer(E e) {

        if((rear+1)%array.length == front) {
            resize(array.length * 2);
        }

        rear = (rear + 1) % array.length;
        array[rear]=e;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size==0) {
            return null;
        }

        front = (front + 1) % array.length;

        @SuppressWarnings("unchecked")
        E item = (E) array[front];

        array[front]=null;
        size--;
        if(array.length > DEFAULT_CAPACITY && size < (array.length/4)) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
        return item;
    }

    @Override
    public E peek() {
        if(size==0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[(front + 1) % array.length];
        return item;


    }

    public E element() {
        E item = peek();
        if(item==null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public E remove() {
        E item = poll();
        if(item==null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public boolean contains(Object value) {
        int start = (front + 1) % array.length;

        for(int i=0,idx=start;i<size;i++,idx=(idx+1)%array.length) {
            if(value.equals(array[idx])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i=0;i< array.length;i++) {
            array[i]=null;
        }

        front=rear=size=0;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    public <T> T[] toArray(T[] a) {
        final T[] res;


        //요소의 개수보다 a배열 길이가 작을때
        if(a.length<size) {


            // f가 r보다 작거나 같을때
            if(front<=rear) {
                return (T[]) Arrays.copyOfRange(array, front + 1, rear + 1, a.getClass());
            }


            //f가 r보다 클때
            res = (T[]) Arrays.copyOfRange(array, 0, size, a.getClass());
            //뒷부분 요소개수
            int rearlength = array.length-1-front;

            //뒷부분 복사
            if(rearlength>0) {
                System.arraycopy(array, front + 1, res, 0, rearlength);
            }
            //앞부분 복사
            System.arraycopy(array, 0, res, rearlength, rear + 1);
            return res;
        }

        //요소의 개수보다 a배열 길이가 클때
        //f가 r보다 작거나 같을때
        if (front <= rear) {
            System.arraycopy(array, front + 1, a, 0, size);
        } //f가 r보다 클때
        else {
            int rearlength = array.length-front-1;

            if (rearlength>0) {
                System.arraycopy(array, front + 1, a, 0, rearlength);
            }
            System.arraycopy(array, 0, a, rearlength, rear + 1);
        }
        return a;
    }

    public Object clone() {
        try {
            ArrayQueue<E> clone = (ArrayQueue<E>) super.clone();
            clone.array = Arrays.copyOf(array, array.length);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public void sort() {
        sort(null);
    }

    public void sort(Comparator<? super E> c) {
        Object[] res = toArray();

        Arrays.sort((E[])res, 0, size, c);
        clear();

        System.arraycopy(res,0,array,1,res.length);
        this.rear = this.size = res.length;
    }
}
