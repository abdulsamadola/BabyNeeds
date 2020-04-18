package com.campstore.babyneeds.model;

public class Item {
    private int id;
    private String itemName;
    private int itemQty;
    private String itemColor;
    private int itemSize;
    private String itemDate;


    public Item() {
    }

    public Item(String itemName, int itemQty, String itemColor, int itemSize, String itemDate) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemDate = itemDate;
    }



    public Item(int id, String itemName, int itemQty, String itemColor, int itemSize, String itemDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemDate = itemDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", itemColor='" + itemColor + '\'' +
                ", itemSize=" + itemSize +
                ", itemDate='" + itemDate + '\'' +
                '}';
    }
}
