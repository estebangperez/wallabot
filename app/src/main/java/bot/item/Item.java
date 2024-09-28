package bot.item;

public interface Item {
    String getId();
    String getTitle();
    String getDescription();
    String getPrice();
    String getUrl();
    String getLocation();
    boolean hasShipping();
    String getImageUrl();
}