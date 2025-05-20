public class Cell {
    public Object content;

    public Cell(Object content){
        this.content = content;
    }

    @Override
    public String toString() {
        return content != null ? content.toString() : "null";
    }
}