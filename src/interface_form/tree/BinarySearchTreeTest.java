package interface_form.tree;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.add(23);
        tree.add(12);
        tree.add(40);
        tree.add(7);
        tree.add(16);
        tree.add(1);
        tree.add(14);
        tree.add(17);
        tree.add(29);
        tree.add(55);
        tree.add(61);

        System.out.print("전위 순회 : ");
        tree.preorder();
        System.out.println();

        System.out.print("중위 순회 : ");
        tree.inorder();
        System.out.println();

        System.out.print("역중위 순회 : ");
        tree.reverseInorder();
        System.out.println();

        System.out.print("후위 순회 : ");
        tree.postorder();
        System.out.println();
    }
}
