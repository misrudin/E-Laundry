package com.laundry;

public class Service {
    private int id;
    private String name;
    private float price;
    private int estimate;

    public Service(int id, String name, float price, int estimate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.estimate = estimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", estimate=" + estimate +
                '}';
    }
}
