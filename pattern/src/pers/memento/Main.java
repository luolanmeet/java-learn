package pers.memento;

public class Main {

    public static void main(String[] args) {

        DraftsBox draftsBox = new DraftsBox();
        Editor editor = new Editor("如何熬汤", "先烧开水", "cook.jpg");

        System.out.println(editor);
        draftsBox.addMemento(editor.saveToMemento());

        editor.setContent("先准备好锅");
        System.out.println(editor);

        editor.undoFromMemento(draftsBox.getMemento());
        System.out.println(editor);
    }

}
