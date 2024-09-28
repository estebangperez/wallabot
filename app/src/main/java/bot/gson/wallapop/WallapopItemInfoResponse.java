package bot.gson.wallapop;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WallapopItemInfoResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public Title title;

    @SerializedName("description")
    public Description description;

    @SerializedName("taxonomy")
    public List<Taxonomy> taxonomy;

    @SerializedName("type")
    public String type;

    @SerializedName("user")
    public User user;

    @SerializedName("slug")
    public String slug;

    @SerializedName("share_url")
    public String shareUrl;

    @SerializedName("modified_date")
    public long modifiedDate;

    @SerializedName("images")
    public List<Image> images;

    @SerializedName("price")
    public Price price;

    @SerializedName("location")
    public Location location;

    @SerializedName("type_attributes")
    public TypeAttributes typeAttributes;

    @SerializedName("supports_shipping")
    public SupportsShipping supportsShipping;

    @SerializedName("shipping")
    public Shipping shipping;

    @SerializedName("hashtags")
    public Hashtags hashtags;

    @SerializedName("favorited")
    public Favorited favorited;

    @SerializedName("counters")
    public Counters counters;

    @SerializedName("characteristics")
    public Characteristics characteristics;

    public static class Title {
        @SerializedName("original")
        public String original;
    }

    public static class Description {
        @SerializedName("original")
        public String original;
    }

    public static class Taxonomy {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("icon")
        public String icon;
    }

    public static class User {
        @SerializedName("id")
        public String id;
    }

    public static class Image {
        @SerializedName("id")
        public String id;

        @SerializedName("average_color")
        public String averageColor;

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

    public static class Price {
        @SerializedName("cash")
        public Cash cash;

        public static class Cash {
            @SerializedName("amount")
            public double amount;

            @SerializedName("currency")
            public String currency;
        }
    }

    public static class Location {
        @SerializedName("latitude")
        public double latitude;

        @SerializedName("longitude")
        public double longitude;

        @SerializedName("approximated")
        public boolean approximated;

        @SerializedName("country_code")
        public String countryCode;

        @SerializedName("city")
        public String city;

        @SerializedName("postal_code")
        public String postalCode;
    }

    public static class TypeAttributes {
        @SerializedName("brand")
        public Brand brand;

        @SerializedName("condition")
        public Condition condition;

        public static class Brand {
            @SerializedName("value")
            public String value;

            @SerializedName("title")
            public String title;

            @SerializedName("text")
            public String text;

            @SerializedName("icon_text")
            public String iconText;
        }

        public static class Condition {
            @SerializedName("value")
            public String value;

            @SerializedName("title")
            public String title;

            @SerializedName("text")
            public String text;

            @SerializedName("icon_text")
            public String iconText;
        }
    }

    public static class SupportsShipping {
        @SerializedName("flag")
        public boolean flag;
    }

    public static class Shipping {
        @SerializedName("item_is_shippable")
        public boolean itemIsShippable;

        @SerializedName("user_allows_shipping")
        public boolean userAllowsShipping;
    }

    public static class Hashtags {
        @SerializedName("values")
        public List<String> values;
    }

    public static class Favorited {
        @SerializedName("flag")
        public boolean flag;
    }

    public static class Counters {
        @SerializedName("views")
        public int views;

        @SerializedName("favorites")
        public int favorites;

        @SerializedName("conversations")
        public int conversations;
    }

    public static class Characteristics {
        @SerializedName("text")
        public String text;

        @SerializedName("original")
        public String original;
    }
}
