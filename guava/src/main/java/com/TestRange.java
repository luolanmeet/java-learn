package com;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class TestRange {

    public static void main(String[] args) {
        base();
        calc();
    }

    private static void calc() {

    }

    private static void base() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        Date leftDate = ca.getTime();

        ca.set(Calendar.MINUTE, 50);
        Date rightDate = ca.getTime();

        // [left, right] closed
        Range<Date> r = Range.closed(leftDate, rightDate);
        System.out.println(r.contains(leftDate));
        System.out.println(r.contains(rightDate));

        // (left, right) open
        // (left, right] openClosed
        // [left, right) closedOpen

        // (left, +∞) greaterThan
        r = Range.greaterThan(leftDate);
        System.out.println(r.contains(leftDate));
        System.out.println(r.contains(rightDate));

        // [left, +∞) atLeast
        // [-∞, right) lessThan
        // [-∞, right] atMost
        // (-∞, +∞) all

        // 等价于 closed
        r = Range.range(leftDate, BoundType.CLOSED, rightDate, BoundType.CLOSED);
        System.out.println(r.contains(leftDate));
        System.out.println(r.contains(rightDate));
    }

}
