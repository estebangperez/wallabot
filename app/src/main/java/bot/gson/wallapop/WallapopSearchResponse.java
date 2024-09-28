package bot.gson.wallapop;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WallapopSearchResponse {
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("section")
        public Section section;
    }

    public static class Section {
        @SerializedName("payload")
        public Payload payload;
    }

    public static class Payload {
        @SerializedName("order")
        public String order;
        @SerializedName("title")
        public String title;
        @SerializedName("items")
        public List<Item> items;
    }

    public static class Item {
        @SerializedName("id")
        public String id;
        @SerializedName("user_id")
        public String userId;
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("category_id")
        public int categoryId;
        @SerializedName("price")
        public Price price;
        @SerializedName("images")
        public List<Image> images;
        @SerializedName("reserved")
        public Reserved reserved;
    }

    public static class Reserved {
        @SerializedName("flag")
        public boolean flag;
    }

    public static class Price {
        @SerializedName("amount")
        public double amount;
        @SerializedName("currency")
        public String currency;
    }

    public static class Image {
        @SerializedName("urls")
        public Urls urls;
    }

    public static class Urls {
        @SerializedName("small")
        public String small;
        @SerializedName("medium")
        public String medium;
        @SerializedName("big")
        public String big;
    }
}