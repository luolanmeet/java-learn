package pers.builder;

public class Main {

    public static void main(String[] args) {

        Room room = new RoomBuilder()
                .bed("big bed")
                .table("big table")
                .chair("big chair")
                .builder();

        System.out.println(room);
    }

}
