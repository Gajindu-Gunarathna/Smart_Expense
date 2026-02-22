package com.smartbudget.algorithms;

import com.smartbudget.model.Expense;

import java.util.List;

public class QuickSortUtil {
    public static void quickSort(List<Expense> list, int low, int high) {
        if (low < high) {
            int p = partition(list, low, high);
            quickSort(list, low, p - 1);
            quickSort(list, p + 1, high);
        }
    }

    private static int partition(List<Expense> list, int low, int high) {
        Expense pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {

            boolean shouldSwap =
                    list.get(j).getPriority() < pivot.getPriority()
                            ||
                            (list.get(j).getPriority() == pivot.getPriority()
                                    && list.get(j).getAmount() > pivot.getAmount());

            if (shouldSwap) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }


    private static void swap(List<Expense> list, int i, int j) {
        Expense temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}