package com.smartbudget.service;

import com.smartbudget.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private String groupType; // Couple / Family / Roommates
    private double groupBudget;
    private List<User> members = new ArrayList<>();
    private int memberCount;
    private double expectedShare;

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public double getGroupBudget() {
        return groupBudget;
    }

    public void setGroupBudget(double groupBudget) {
        this.groupBudget = groupBudget;
    }

    public double getExpectedShare() {
        return expectedShare;
    }

    public void setExpectedShare(double expectedShare) {
        this.expectedShare = expectedShare;
    }

    public Group(String groupType, double groupBudget, int memberCount) {
        this.groupType = groupType;
        this.groupBudget = groupBudget;
        this.memberCount = memberCount;
        this.expectedShare = groupBudget / memberCount;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void showGroupSummary() {
        System.out.println("Group Type: " + groupType);
        System.out.println("Group Budget: " + groupBudget);
        System.out.println("Expected per-person share: " + expectedShare);

        double total = 0;

        for (User u : members) {
            double spent = u.getExpenses().calculateTotal();
            total += spent;

            System.out.println(
                    u.getName() + " spent: " + spent +
                            " | Expected: " + expectedShare +
                            (spent > expectedShare ? " ⚠ Overspent" : " ✔ Fair")
            );
        }

        System.out.println("Total Group Spending: " + total);

        if (total > groupBudget) {
            System.out.println("⚠ Group exceeded budget by: " + (total - groupBudget));
        } else {
            System.out.println("✔ Group within budget");
        }
    }

}