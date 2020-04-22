package pers.builder;

/**
 * Room的建造类
 */
public class RoomBuilder {

    /**
     * 创建Room的建造类实例的同时创建Room实例
     */
    private Room room = new Room();

    /**
     * 返回Room
     */
    public Room builder() {
        return room;
    }

    /**
     * 设值之后，继续返回建造类实例
     */
    public RoomBuilder bed(String bed) {
        room.setBed(bed);
        return this;
    }

    public RoomBuilder table(String table) {
        room.setTable(table);
        return this;
    }

    public RoomBuilder chair(String chair) {
        room.setChair(chair);
        return this;
    }

}
