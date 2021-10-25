package me.rebelmythik.requestboard.api;

import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class Request {

    UUID owner;
    String ownerName;
    ItemStack item;
    int count;
    int price;
    int time;

    //Constructor
    public Request(UUID owner, String ownerName, ItemStack item, int count, int price, int time) {
        this.owner = owner;
        this.ownerName = ownerName;
        this.item = item;
        this.count = count;
        this.price = price;
        this.time = time;
    }

    //Gets
    public UUID getOwner() {
        return owner;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public ItemStack getItem() {
        return item;
    }
    public int getCount() {
        return count;
    }
    public int getPrice() {
        return price;
    }
    public int getTime() {
        return time;
    }

    //Sets
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public void setOwnerName(String owner) {
        this.ownerName = ownerName;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setTime(int time) {
        this.time = time;
    }
}
