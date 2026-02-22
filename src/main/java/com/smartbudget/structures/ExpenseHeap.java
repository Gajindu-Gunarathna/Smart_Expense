package com.smartbudget.structures;

import com.smartbudget.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseHeap {
    private List<Expense> heap = new ArrayList<>();

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    private boolean higherPriority(Expense a, Expense b) {
        if (a.getPriority() != b.getPriority()) {
            return a.getPriority() > b.getPriority();
        }
        return a.getAmount() > b.getAmount();
    }

    public void insert(Expense expense) {
        heap.add(expense);
        heapifyUp(heap.size() - 1);
    }

    public Expense extractMax() {
        if (heap.isEmpty()) return null;

        Expense max = heap.get(0);
        Expense last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return max;
    }


    private void heapifyUp(int index) {
        while (index > 0 && higherPriority(heap.get(index), heap.get(parent(index)))) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void heapifyDown(int index) {
        int largest = index;
        int left = left(index);
        int right = right(index);

        if (left < heap.size() && higherPriority(heap.get(left), heap.get(largest)))
            largest = left;

        if (right < heap.size() && higherPriority(heap.get(right), heap.get(largest)))
            largest = right;

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {
        Expense temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

}
