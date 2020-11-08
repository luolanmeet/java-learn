package pers.builder.demo.one;

/**
 * 比较常见的写法
 * @author cck
 * @date 2020/11/8 14:00
 */
public class NutritionFacts {

    private final int servingSize;
    private final int servings;
    private final int fat;
    private final int sodium;

    // 注意构造器私有
    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        fat = builder.fat;
        sodium = builder.sodium;
    }

    public static class Builder {
        // required
        private final int servingSize;
        private final int servings;

        // optional, with default val
        private int fat = 0;
        private int sodium = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder fat(int val) {
            this.fat = val;
            return this;
        }

        public Builder dodium(int val) {
            this.sodium = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

}
