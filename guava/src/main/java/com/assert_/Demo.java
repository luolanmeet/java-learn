package com.assert_;

import com.google.common.base.Preconditions;

/**
 * @author cck
 * @date 2020/12/10 23:28
 */
public class Demo {

    public static void main(String[] args) {

        Preconditions.checkArgument(1 == 1, "error");
//        Preconditions.checkArgument(1 == 2, "error");

        Preconditions.checkArgument(1 == 2, "error %s", " 1 != 2");
    }

}
