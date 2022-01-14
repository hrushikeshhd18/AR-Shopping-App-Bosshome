package com.hrushikesh.Bosshomes.modals;

/**
 * Created by vinup on 7/4/2018.
 */

public class MainItemModal
{
    String item_id;
    String name;
    String description;
    String price;
    String type;
    String quantity;
    String colour;
    String image;

    public MainItemModal(String item_id, String name, String description, String price, String type, String quantity, String colour, String image) {
        this.item_id = item_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
        this.colour = colour;
        this.image = image;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
