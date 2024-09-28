package bot.gson.vinted;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VintedItemResponse {
    
    @SerializedName("items")
    public List<Item> items;

    public static class Item {
        @SerializedName("id")
        public long id;

        @SerializedName("title")
        public String title;

        @SerializedName("price")
        public Price price;

        @SerializedName("is_visible")
        public boolean isVisible;

        @SerializedName("discount")
        public Object discount;

        @SerializedName("brand_title")
        public String brandTitle;

        @SerializedName("user")
        public User user;

        @SerializedName("conversion")
        public Object conversion;

        @SerializedName("url")
        public String url;

        @SerializedName("promoted")
        public boolean promoted;

        @SerializedName("photo")
        public Photo photo;

        @SerializedName("favourite_count")
        public int favouriteCount;

        @SerializedName("is_favourite")
        public boolean isFavourite;

        @SerializedName("badge")
        public Object badge;

        @SerializedName("service_fee")
        public Price serviceFee;

        @SerializedName("total_item_price")
        public Price totalItemPrice;

        @SerializedName("view_count")
        public int viewCount;

        @SerializedName("size_title")
        public String sizeTitle;

        @SerializedName("content_source")
        public String contentSource;

        @SerializedName("status")
        public String status;

        @SerializedName("icon_badges")
        public List<Object> iconBadges;

        @SerializedName("item_box")
        public ItemBox itemBox;

        @SerializedName("search_tracking_params")
        public SearchTrackingParams searchTrackingParams;
    }

    public static class Price {
        @SerializedName("amount")
        public String amount;

        @SerializedName("currency_code")
        public String currencyCode;
    }

    public static class User {
        @SerializedName("id")
        public long id;

        @SerializedName("login")
        public String login;

        @SerializedName("profile_url")
        public String profileUrl;

        @SerializedName("photo")
        public Photo photo;

        @SerializedName("business")
        public boolean business;
    }

    public static class Photo {
        @SerializedName("id")
        public long id;

        @SerializedName("width")
        public int width;

        @SerializedName("height")
        public int height;

        @SerializedName("url")
        public String url;

        @SerializedName("dominant_color")
        public String dominantColor;

        @SerializedName("dominant_color_opaque")
        public String dominantColorOpaque;

        @SerializedName("thumbnails")
        public List<Thumbnail> thumbnails;

        @SerializedName("high_resolution")
        public HighResolution highResolution;

        @SerializedName("full_size_url")
        public String fullSizeUrl;

        @SerializedName("is_hidden")
        public boolean isHidden;
    }

    public static class Thumbnail {
        @SerializedName("type")
        public String type;

        @SerializedName("url")
        public String url;

        @SerializedName("width")
        public int width;

        @SerializedName("height")
        public int height;

        @SerializedName("original_size")
        public Object originalSize;
    }

    public static class HighResolution {
        @SerializedName("id")
        public String id;

        @SerializedName("timestamp")
        public long timestamp;

        @SerializedName("orientation")
        public int orientation;
    }

    public static class ItemBox {
        @SerializedName("first_line")
        public String firstLine;

        @SerializedName("second_line")
        public String secondLine;
    }

    public static class SearchTrackingParams {
        @SerializedName("score")
        public double score;

        @SerializedName("matched_queries")
        public List<String> matchedQueries;
    }
}
