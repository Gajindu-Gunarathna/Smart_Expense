package com.smartbudget.model;

public class Node {
    public Expense data;
    public Node next;

    public Node(Expense data) {
        this.data = data;
        this.next = null;
    }


}