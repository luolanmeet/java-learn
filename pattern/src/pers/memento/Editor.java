package pers.memento;

/**
 * 编辑器
 * 对应 Originator
 */
public class Editor {

    private String title;
    private String content;
    private String imgs;

    public Editor(String title, String content, String imgs) {
        this.title = title;
        this.content = content;
        this.imgs = imgs;
    }

    public ArticleMemento saveToMemento() {
        return new ArticleMemento(this.title, this.content, this.imgs);
    }

    public void undoFromMemento(ArticleMemento memento) {
        this.title = memento.getTitle();
        this.content = memento.getContent();
        this.imgs = memento.getImgs();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}
