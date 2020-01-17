package com.collection;

import com.google.common.collect.BoundType;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class TestSortedMultiset {

    @Test
    public void test1() {

        TreeMultiset<Integer> treeMultiset = TreeMultiset.create();

        treeMultiset.add(100);
        treeMultiset.add(100);
        treeMultiset.add(100);

        treeMultiset.add(200);

        treeMultiset.add(300);
        treeMultiset.add(300);

        //
        SortedMultiset<Integer> subMultiset = treeMultiset.subMultiset(
                120, BoundType.CLOSED, 300, BoundType.OPEN);

        System.out.println(subMultiset.elementSet());
    }

}
