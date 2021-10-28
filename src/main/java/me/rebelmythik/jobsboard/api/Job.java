package me.rebelmythik.jobsboard.api;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class Job {

    UUID owner;
    String ownerName;
    ItemStack item;
    int quantity;
    int reward;
    int time;
    int expirationDate;

    //Constructor
    public Job(UUID owner, String ownerName, ItemStack item, int quantity, int reward) {
        this.owner = owner;
        this.ownerName = ownerName;
        this.item = item;
        this.quantity = quantity;
        this.reward = reward;
        this.expirationDate = getExpirationDate();
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
    public int getQuantity() {
        return quantity;
    }
    public int getReward() {
        return reward;
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
    public void setQuantity(int count) {
        this.quantity = count;
    }
    public void setReward(int price) {
        this.reward = price;
    }
    public void setTime(int time) {
        this.time = time;
    }


    private int getExpirationDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, 24); //TODO add in config value for days and multiply the 24 by it
        return (int) ((c.getTimeInMillis())/1000);
    }
}
