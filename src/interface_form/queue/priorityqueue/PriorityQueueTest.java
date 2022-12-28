package interface_form.queue.priorityqueue;

import interface_form.test.HeapTestCustom;

import java.util.Comparator;
import java.util.Random;

public class PriorityQueueTest {
    public static void main(String[] args) {

        PriorityQueue<Student> pq1 = new PriorityQueue<Student>();
        PriorityQueue<Student> pq2 = new PriorityQueue<Student>(comp);

        Random rnd = new Random();

        char name = 'A';
        for(int i=0;i<10;i++) {
            int math = rnd.nextInt(10);
            int science = rnd.nextInt(10);

            pq1.offer(new Student(name, math, science));
            pq2.offer(new Student(name, math, science));
            name++;
        }

        System.out.println("[pq1 내부 배열 상태]");
        for (Object val : pq1.toArray()) {
            System.out.println(val);
        }
        System.out.println();
        System.out.println();

        System.out.println("[pq2 내부 배열 상태]");
        for (Object val : pq2.toArray()) {
            System.out.println(val);
        }
        System.out.println();
        System.out.println();

        System.out.println("[수학-과학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while(!pq1.isEmpty()) {
            System.out.println(pq1.poll());
        }
        System.out.println();
        System.out.println("[과학-수학-이름순 뽑기]");
        System.out.println("name\tmath\tscience");
        while(!pq2.isEmpty()) {
            System.out.println(pq2.poll());
        }

    }

    private static Comparator<Student> comp = new Comparator<Student>() {
        public int compare(Student o1, Student o2) {
            if(o1.science==o2.science) {
                if(o1.math==o2.math) {
                    return o1.name - o2.name;
                }
                return o2.math-o1.math;
            }
            return o2.science-o1.science;
        }
    };

    static class Student implements Comparable<Student> {

        char name;
        int math;
        int science;

        public Student(char name, int math,int science) {
            this.name=name;
            this.math=math;
            this.science = science;
        }

        @Override
        public int compareTo(Student o) {
            if (this.math==o.math) {
                if(this.science==o.science) {
                    return this.name - o.name;
                }
                return o.science - this.science;
            }
            return o.math - this.math;
        }

        public String toString() {
            return name + "\t" + math + "\t" + science + "\n";
        }
    }
}
