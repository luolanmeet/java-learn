package com.autoValue;

import com.google.auto.value.AutoValue;

/**
 * @author cck
 * @date 2020/11/14 16:55
 */
@AutoValue
public abstract class PersonWithBuilder {

    abstract String getName();
    abstract int getAge();

    /**
     * 构建者模式
     * @return
     */
    static Builder builder() {
        return new AutoValue_PersonWithBuilder.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder name(String name);
        abstract Builder age(int age);
        abstract PersonWithBuilder build();
    }

}
