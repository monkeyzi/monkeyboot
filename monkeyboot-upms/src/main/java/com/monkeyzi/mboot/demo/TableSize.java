package com.monkeyzi.mboot.demo;

public class TableSize {

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        System.out.println("n1="+n);
        n |= n >>> 1;
        System.out.println("n2="+n);
        n |= n >>> 2;
        System.out.println("n3="+n);
        n |= n >>> 4;
        System.out.println("n4="+n);
        n |= n >>> 8;
        System.out.println("n5="+n);
        n |= n >>> 16;
        System.out.println("n6="+n);
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }
    public static void main(String[] args) {
        //System.out.println(tableSizeFor(888));
        int c=22;
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;

        System.out.println(n);
    }
}
