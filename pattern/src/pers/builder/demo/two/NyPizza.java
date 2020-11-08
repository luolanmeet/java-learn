package pers.builder.demo.two;

/**
 * @author cck
 * @date 2020/11/8 14:32
 */
public class NyPizza extends Pizza {

    public enum Size {SMALL, MEDIUM, LARGE}
    private final Size size;

    private NyPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
    }

    public static class Builder extends Pizza.Builder<Builder> {

        private final Size size;

        public Builder(Size size) {
            this.size = size;
        }

        @Override
        Pizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }

}
