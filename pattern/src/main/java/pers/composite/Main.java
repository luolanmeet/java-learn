package pers.composite;

public class Main {

    public static void main(String[] args) {

        Branch root = new Branch();

        Branch branch = new Branch();
        branch.addChild(new Left());
        branch.addChild(new Left());

        root.addChild(branch);

        root.method();
    }

}
