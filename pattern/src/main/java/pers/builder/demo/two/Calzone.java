package pers.builder.demo.two;

/**
 * @author cck
 * @date 2020/11/8 14:36
 */
public class Calzone extends Pizza {

    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {

        private boolean sauceInside = false;

        public Builder() {
            this.sauceInside = sauceInside;
        }

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        Pizza build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

}
