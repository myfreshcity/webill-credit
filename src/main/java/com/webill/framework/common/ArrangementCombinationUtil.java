package com.webill.framework.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组排列组合算法工具类
 * Created by david on 17/2/21.
 */
public class ArrangementCombinationUtil {

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     * @param n
     * @return
     */
    private static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     * @param n
     * @param m
     * @return
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

    /**
     * 排列选择（从列表中选择n个排列）
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public static List<Object> arrangementSelect(Object[] dataList, int n) {
        System.out.println(String.format("A(%d, %d) = %d", dataList.length, n, arrangement(dataList.length, n)));
        if(dataList.length < n){
            return new ArrayList<Object>();
        }
        Object[] result = new Object[n];
        List<Object> allResult = new ArrayList<>();
        arrangementSelect(dataList, result, 0,allResult);
        return allResult;
    }

    /**
     * 排列选择
     * @param dataList 待选列表
     * @param resultList 前面（resultIndex-1）个的排列结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void arrangementSelect(Object[] dataList, Object[] resultList, int resultIndex,List<Object> allResult) {
        int resultLen = resultList.length;
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果
            //System.out.println(result);
            allResult.add(resultList.clone());
            return;
        }

        // 递归选择下一个
        for (int i = 0; i < dataList.length; i++) {
            // 判断待选项是否存在于排列结果中
            boolean exists = false;
            for (int j = 0; j < resultIndex; j++) {
                if (dataList[i].equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择
                resultList[resultIndex] = dataList[i];
                arrangementSelect(dataList, resultList, resultIndex + 1,allResult);
            }
        }
    }

    /**
     * 组合选择（从列表中选择n个组合）
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public static List<Object> combinationSelect(Object[] dataList, int n) {
        System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, combination(dataList.length, n)));
        if(dataList.length < n){
            return new ArrayList<Object>();
        }
        Object[] result = new Object[n];
        List<Object> allResult = new ArrayList<>();
        combinationSelect(dataList, 0, result, 0,allResult);
        return allResult;
    }

    /**
     * 组合选择
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void combinationSelect(Object[] dataList, int dataIndex, Object[] resultList, int resultIndex,List<Object> allResult) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            //System.out.println(result);
            allResult.add(resultList.clone());
            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1,allResult);
        }
    }

    public static void main(String[] args) {
        //arrangementSelect(new Integer[] { 0,1,2,3,5}, 2);
        List<Object> c1 = combinationSelect(new Integer[]{
                0, 1, 2, 3, 5
        }, 2);
        System.out.println(Arrays.asList(c1));
    }
}
