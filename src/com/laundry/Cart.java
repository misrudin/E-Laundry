package com.laundry;

public class Cart {
    private int id;
    private Laundry laundry;
    private Service service;
    private double totalPrice;
    private int qty;
    private float weight;

    public Cart(int id, Laundry laundry, Service service, int qty, float weight) {
        this.id = id;
        this.laundry = laundry;
        this.service = service;
        this.totalPrice = laundry.getType() == TypeLaundry.KILOAN ?
                (service.getPrice() + laundry.getPrice()) * weight :
                (service.getPrice() + laundry.getPrice()) * qty;
        this.qty = qty;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Laundry getLaundry() {
        return laundry;
    }

    public void setLaundry(Laundry laundry) {
        this.laundry = laundry;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", laundry=" + laundry +
                ", service=" + service +
                ", totalPrice=" + totalPrice +
                ", qty=" + qty +
                ", weight=" + weight +
                '}';
    }
}
