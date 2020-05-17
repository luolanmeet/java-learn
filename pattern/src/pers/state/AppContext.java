package pers.state;

public class AppContext {

    public static final UserState STATE_LOGIN = new LoginState();
    public static final UserState STATE_UNLOGIN = new UnLoginState();
    private UserState currentState = STATE_UNLOGIN;

    {
        STATE_LOGIN.setAppContext(this);
        STATE_UNLOGIN.setAppContext(this);
    }

    public void login() {
        this.currentState = STATE_LOGIN;
    }

    public void setState(UserState state) {
        this.currentState = state;
        this.currentState.setAppContext(this);
    }

    public void watch() {
        this.currentState.watch();
    }

    public void getLike() {
        this.currentState.getLike();
    }

    public void collec() {
        this.currentState.collec();
    }

}
