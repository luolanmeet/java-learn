package pers.builder;

public class Room {

    private String bed;

    private String table;

    private String chair;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    @Override
    public String toString() {
        return "Room{" +
                "table='" + table + '\'' +
                ", chair='" + chair + '\'' +
                ", bed='" + bed + '\'' +
                '}';
    }

}
