package pers.decorator;

public class MangoCake extends BaseDecoratorCake {

    public MangoCake(BaseCake baseCake) {
        super(baseCake);
    }

    @Override
    public String getMsg() {
        return super.getMsg() + "+芒果";
    }

    @Override
    public Integer getCalorie() {
        return super.getCalorie() + 80;
    }

}
