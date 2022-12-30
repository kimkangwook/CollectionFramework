package interface_form.set.hashset;

import interface_form.set.Set;

import java.util.Arrays;

public class HashSet<E> implements Set<E> {

    private final static int DEFAULT_CAPACITY = 1 << 4;

    // 테이블의 크기에 비해 데이터의 양이 일정 이상 많아지면 성능이 저하되는데 이를 해결하기 위해
    // 배열의 용적을 늘릴 수 있도록 기준이 되는 변수임
    private final static float LOAD_FACTOR = 0.75f;

    Node<E>[] table;
    private int size;

    public HashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size=0;
    }

    private static final int hash(Object key) {
        int hash;
        if (key==null) {
            return 0;
        } else {
            return Math.abs(hash=key.hashCode()) ^ (hash >>> 16);
        }
    }
    @Override
    public boolean add(E e) {
        return add(hash(e), e)==null;
    }

    private E add(int hash, E key) {
        int idx = hash & table.length;

        if(table[idx]==null) {
            table[idx] = new Node<E>(hash, key, null);
        } else {
            Node<E> temp = table[idx];
            Node<E> prev = null;

            while (temp!=null) {
                if((temp.hash == hash) && (temp.key==key||temp.key.equals(key))) {
                    return key;
                }
                prev = temp;
                temp = temp.next;
            }
            prev.next = new Node<E>(hash, key, null);
        }
        size++;

        if(size>=LOAD_FACTOR*table.length) {
            resize();
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;

        final Node<E>[] newTable = (Node<E>[])new Node[newCapacity];

        for (int i=0; i<table.length;i++) {
            Node<E> value = table[i];

            if(value==null) {
                continue;
            }

            table[i] = null;

            Node<E> nextNode;

            while (value!=null) {
                int idx = value.hash % newCapacity;

                // 해시충돌 시
                if(newTable[idx]!=null) {
                    Node<E> tail = newTable[idx];

                    while(tail.next !=null) {
                        tail=tail.next;
                    }

                    nextNode = value.next;
                    value.next=null;
                    tail.next=value;
                }
                //해시 충돌 없을 시
                else {
                    nextNode = value.next;
                    value.next=null;
                    newTable[idx]=value;
                }
                value = nextNode;
            }
        }
        table = null;
        table = newTable;

    }

    @Override
    public boolean remove(Object o) {
        return remove(hash(o), o)!=null;
    }

    private Object remove(int hash, Object key) {
        int idx = hash % table.length;

        Node<E> node = table[idx];
        Node<E> removedNode = null;
        Node<E> prev = null;

        if(node==null) {
            return null;
        }

        while(node!=null) {
            if (node.hash==hash && (node.key==key)||node.key.equals(key)) {
                removedNode = node;

                if(prev==null) {
                   table[idx] = node.next;
                    node=null;
                } else {
                    prev.next=node.next;
                    node=null;
                }
                size--;
                break;
            }
            prev=node;
            node=node.next;
        }
        return removedNode;
    }



    @Override
    public boolean contains(Object o) {
        int idx = hash(o) % table.length;
        Node<E> temp = table[idx];

        while(temp!=null) {
            if(o==temp.key || (o!=null &&(o.equals(temp.key)))) {
                return true;
            }
            temp=temp.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        if(table !=null && size>0) {
            for(int i=0; i<table.length;i++) {
                table[i]=null;
            }
            size =0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o==this) {
            return true;
        }
        if(!(o instanceof HashSet)) {
            return false;
        }

        HashSet<E> oSet;

        try {
            oSet=(HashSet<E>)o;
            if(oSet.size()!=size) {
                return false;
            }

            for(int i=0; i<oSet.table.length;i++) {
                Node<E> oTable = oSet.table[i];

                while (oTable !=null) {
                    if(!contains(oTable)) {
                        return false;
                    }
                    oTable = oTable.next;
                }
            }
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }

    public Object[] toArray() {
        if (table == null) {
            return null;
        }
        Object[] ret = new Object[size];
        int index=0;

        for(int i=0;i<table.length;i++) {
            Node<E> node = table[i];

            while(node!=null) {
                ret[index] = node.key;
                index++;
                node=node.next;
            }
        }

        return ret;
    }

    public <T> T[] toArray(T[] a) {
        Object[] copy = toArray();
        if (a.length<size) {
            return (T[])Arrays.copyOf(copy, size, a.getClass());
        }
        System.arraycopy(copy,0,a,0,size);
        return a;
    }
}
