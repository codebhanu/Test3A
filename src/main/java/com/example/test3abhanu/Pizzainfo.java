package com.example.test3abhanu;

public class Pizzainfo {
    private int orderId;
    private String name;
    private String phone;
    private String size;
    private  int numberOfToppings;
    private  double totalBills;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getNumberOfToppings() {
        return numberOfToppings;
    }

    public void setNumberOfToppings(int numberOfToppings) {
        this.numberOfToppings = numberOfToppings;
    }

    public double getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(double totalBills) {
        this.totalBills = totalBills;
    }

    public Pizzainfo(int orderId, String name, String phone, String size, int numberOfToppings, double totalBills) {
        this.orderId = orderId;
        this.name = name;
        this.phone = phone;
        this.size = size;
        this.numberOfToppings = numberOfToppings;
        this.totalBills = totalBills;
    }
}
