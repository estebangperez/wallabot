package bot.item.impl;

import bot.gson.wallapop.WallapopItemInfoResponse;
import bot.item.Item;

public class WallapopItem implements Item {
    private WallapopItemInfoResponse itemInfo;

    public WallapopItem(WallapopItemInfoResponse itemInfo) {
        this.itemInfo = itemInfo;
    }

    @Override
    public String getId() {
        return itemInfo.id;
    }

    @Override
    public String getTitle() {
        return itemInfo.title.original;
    }

    @Override
    public String getDescription() {
        return itemInfo.description.original;
    }

    @Override
    public String getPrice() {
        return itemInfo.price.cash.amount + " " + itemInfo.price.cash.currency;
    }

    @Override
    public String getUrl() {
        return itemInfo.shareUrl;
    }

    @Override
    public String getLocation() {
        return itemInfo.location.city + ", " + itemInfo.location.postalCode;
    }

    @Override
    public boolean hasShipping() {
        return itemInfo.shipping != null && itemInfo.shipping.userAllowsShipping;
    }

    @Override
    public String getImageUrl() {
        return itemInfo.images.isEmpty() ? null : itemInfo.images.get(0).urls.medium;
    }
}