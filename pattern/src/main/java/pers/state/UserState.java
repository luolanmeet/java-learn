package pers.state;

public abstract class UserState {

    protected AppContext appContext;

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    /**
     * 观看
     */
    public abstract void watch();

    /**
     * 点赞
     */
    public abstract void getLike();

    /**
     * 收藏
     */
    public abstract void collec();

}
