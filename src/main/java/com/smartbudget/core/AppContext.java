package com.smartbudget.core;

import com.smartbudget.model.SavingsGoal;
import com.smartbudget.service.ExpenseManager;
import com.smartbudget.service.Group;

public class AppContext {

    private static ExpenseManager expenseManager;
    private static Group group;
    private static SavingsGoal savingsGoal;

    public static void init(ExpenseManager manager, Group grp) {
        expenseManager = manager;
        group = grp;
    }
    public static void setExpenseManager(ExpenseManager manager) {
        expenseManager = manager;
    }

    public static ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public static Group getGroup() {
        return group;
    }

    public static void setSavingsGoal(SavingsGoal goal) {
        savingsGoal = goal;
    }

    public static SavingsGoal getSavingsGoal() {
        return savingsGoal;
    }
}
