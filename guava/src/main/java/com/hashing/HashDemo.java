package com.hashing;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import lombok.Setter;

/**
 * @author cck
 * @date 2020/11/14 15:06
 */
public class HashDemo {

    @Setter
    public static class Country {

        public String name;
        public long population;

        Funnel<Country> funnel = new Funnel<Country>() {
            private static final long serialVersionUID = 6405178766484863699L;
            @Override
            public void funnel(Country from, PrimitiveSink into) {
                into.putString(from.name, Charsets.UTF_8)
                        .putLong(from.population);
            }
        };

        @Override
        public int hashCode() {

            HashFunction hashFunction = Hashing.goodFastHash(11);

            Hasher hasher = hashFunction.newHasher();
            hasher.putObject(this,funnel);

            HashCode hashCode = hasher.hash();

            return hashCode.hashCode();
        }
    }

    public static void main(String[] args) {

        Country country = new Country();
        country.setName("Pizza");
        country.setPopulation(10000);

        System.out.println(country.hashCode());
    }

}
