package bot.item.impl;

import bot.gson.vinted.VintedItemResponse;
import bot.item.Item;

public class VintedItem implements Item {
    private VintedItemResponse.Item item;

    public VintedItem(VintedItemResponse.Item item) {
        this.item = item;
    }

    @Override
    public String getId() {
        return String.valueOf(item.id);
    }

    @Override
    public String getTitle() {
        return item.title;
    }

    @Override
    public String getDescription() {
        return "Brand: " + (item.brandTitle != null ? item.brandTitle : "Unknown");
    }

    @Override
    public String getPrice() {
        return item.price.amount + " " + item.price.currencyCode;
    }

    @Override
    public String getUrl() {
        return item.url;
    }

    @Override
    public String getLocation() {
        return "Unknown";
    }

    @Override
    public boolean hasShipping() {
        return true;
    }

    @Override
    public String getImageUrl() {
        return item.photo != null ? item.photo.url : null;
    }
}