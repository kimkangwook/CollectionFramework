package interface_form.queue;

import java.util.Comparator;

public class test2 {
    public static void main(String[] args) {
        ArrayQueue<Student> q = new ArrayQueue<>();

        q.offer(new Student("김자바", 92));
        q.offer(new Student("이시플", 72));
        q.offer(new Student("조시샾", 98));
        q.offer(new Student("파이손", 51));

        q.sort();

        for(Object a:q.toArray()) {
            System.out.println(a);
        }
    }

    static Comparator<Student> customComp = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.score - o2.score;
        }
    };
}

class Student  implements Comparable<Student> {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String toString(){
        return "이름 : " + name + "\t성적 : " + score;
    }

    @Override
    public int compareTo(Student o) {
        return o.score-this.score;
    }
}
