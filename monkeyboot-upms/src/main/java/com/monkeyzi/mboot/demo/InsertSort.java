package com.monkeyzi.mboot.demo;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {

        int[] arr={23,11,34,55,2,5};
        sort1(arr);
        System.out.println("----------------------");
        System.out.println("最终排序结果为");
        Arrays.stream(arr).forEach(a-> System.out.print(a+" "));
    }

    public static  void  sort(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){

            for (int j=i;j>0;j--){
                if (arr[j]<arr[j-1]){
                    swap(arr,j,j-1);
                }else {
                    break;
                }
            }
            System.out.print("第"+(i)+"趟的结果为:");
            Arrays.stream(arr).forEach(a-> System.out.print(a+"  "));
            System.out.println();
        }
    }

    private static  void  swap(int[] arr,int k,int m){
        int temp=arr[k];
        arr[k]=arr[m];
        arr[m]=temp;
    }


    public static  void  sort1(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){

            for (int j=i;j>0&&arr[j]>arr[j-1];j--){
                swap(arr,j,j-1);
            }
            System.out.print("第"+(i)+"趟的结果为:");
            Arrays.stream(arr).forEach(a-> System.out.print(a+"  "));
            System.out.println();
        }
    }
}
