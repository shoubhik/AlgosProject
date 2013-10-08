package com.ncsu.project;

/**
 * Created with IntelliJ IDEA. User: shoubhik Date: 7/10/13 Time: 8:26 PM To
 * change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;
import java.util.List;

public class ShiftOr {

    private long match;
    private long comparison;

    private static long preSo(char[] x, long[] s) {
        long j, lim, m = x.length;
        int i;
        for (i = 0; i < s.length; ++i)
            s[i] = ~0;
        for (lim = i = 0, j = 1; i < m; ++i, j <<= 1) {
            s[x[i]] &= ~j;
            lim |= j;
        }
        lim = ~(lim >> 1);
        return (lim);
    }

    public static List<Integer> findAll(String pattern, String source) {
        char[] x = pattern.toCharArray(), y = source.toCharArray();
        long lim, state;
        long[] s = new long[65536];
        int j, m = x.length, n = y.length;

        List<Integer> result = new ArrayList<Integer>();

		/* Preprocessing */
        lim = preSo(x, s);

		/* Searching */
        for (state = ~0, j = 0; j < n; ++j) {
            state = (state << 1) | s[y[j]];
            if (state < lim)
                result.add(j - m + 1);
        }

        return result;
    }

    public static ShiftOr compile(String pattern) {
        char[] x = pattern.toCharArray();
        long lim;
        long[] s = new long[65536];
        int m = x.length;

        lim = preSo(x, s);

        ShiftOr so = new ShiftOr();
        so.lim = lim;
        so.m = m;
        so.s = s;

        return so;

    }

    public List<Integer> findAll(String source) {
        char[] y = source.toCharArray();
        long state;
        int j, n = y.length;
        List<Integer> result = new ArrayList<Integer>();

        for (state = ~0, j = 0; j < n; ++j) {
            state = (state << 1) | s[y[j]];
            comparison++;
            if (state < lim) {
                match++;
//                result.add(j - m + 1);
            }
        }

        return result;
    }

    private int m;
    private long[] s;
    private long lim;

    public static void main(String[] args) {
        String pattern = "and";
        String text = "nand nandand";
        ShiftOr shiftOr = ShiftOr.compile(pattern);
        System.out.println(shiftOr.findAll(text));
    }

    public long getMatch() {
        return match;
    }

    public long getComparison() {
        return comparison;
    }
}
