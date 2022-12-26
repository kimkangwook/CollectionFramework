package interface_form.queue;

import java.util.Comparator;

public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Student> dq = new ArrayDeque<>();
        dq.offer(new Student("Chen", 89));
        dq.offer(new Student("Malli", 65));
        dq.offer(new Student("Tomas", 53));
        dq.offer(new Student("Cris", 79));
        dq.offer(new Student("Julia", 93));

        dq.sort();

        for(Object a: dq.toArray()) {
            System.out.println(a);
        }
    }

//    static Comparator<Student> customComp = new Comparator<Student>() {
//        @Override
//        public int compare(Student o1, Student o2) {
//            return o2.score - o1.score;
//        }
//    };
}
