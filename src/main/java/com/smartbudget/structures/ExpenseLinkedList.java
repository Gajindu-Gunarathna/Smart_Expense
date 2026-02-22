package com.smartbudget.structures;

import com.smartbudget.model.Expense;
import com.smartbudget.model.Node;

import java.util.ArrayList;
import java.util.List;

public class ExpenseLinkedList {
    private Node head;

    public void addExpense(Expense expense) {
        Node newNode = new Node(expense);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    public void displayExpenses() {
        if (head == null) {
            System.out.println("No expenses recorded.");
            return;
        }

        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    public double calculateTotal() {
        double total = 0;
        Node temp = head;

        while (temp != null) {
            total += temp.data.getAmount();
            temp = temp.next;
        }
        return total;
    }

    public List<Expense> toList() {
        List<Expense> list = new ArrayList<>();
        Node temp = head;

        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }
        return list;
    }

    public void removeExpense(Expense target) {
        if (head == null) return;

        if (head.data.equals(target)) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(target)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }


}
