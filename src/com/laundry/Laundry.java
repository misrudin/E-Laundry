package com.laundry;

public class Laundry {
    private int id;
    private String name;
    private float price;
    TypeLaundry type;

    public Laundry(int id, String name, float price, TypeLaundry type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TypeLaundry getType() {
        return type;
    }

    public void setType(TypeLaundry type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Laundry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
