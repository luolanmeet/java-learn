package pers.state;

public class Main {

    public static void main(String[] args) {

        AppContext appContext = new AppContext();

        appContext.watch();

        appContext.getLike();
        appContext.collec();

        appContext.watch();
    }

}
