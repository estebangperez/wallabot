package bot.config;

import java.util.List;

public class Config {

    private Auth auth;
    private List<Search> searches;

    public Auth getAuth() {
        return auth;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public static class Auth {
        private String wallapop_bearer_token;
        private String vinted_bearer_token;
        private String vinted_refresh_token;

        public String getVintedBearerToken() {
            return vinted_bearer_token;
        }

        public String getVintedRefreshToken() {
            return vinted_refresh_token;
        }

        public void setVintedRefreshToken(String vinted_refresh_token) {
            this.vinted_refresh_token = vinted_refresh_token;
        }

        public void setVintedBearerToken(String vinted_bearer_token) {
            this.vinted_bearer_token = vinted_bearer_token;
        }

        public String getWallapopBearerToken() {
            return wallapop_bearer_token;
        }
    }

    public static class Search {
        private String webhook_url;
        private String keywords;
        private double latitude;
        private double longitude;
        private int min_price;
        private int max_price;
        private long delay;

        public String getWebhookUrl() {
            return webhook_url;
        }

        public String getKeywords() {
            return keywords;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getMinPrice() {
            return min_price;
        }

        public int getMaxPrice() {
            return max_price;
        }

        public long getDelay() {
            return delay;
        }
    }
}