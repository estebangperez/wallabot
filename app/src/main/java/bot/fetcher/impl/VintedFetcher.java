package bot.fetcher.impl;

import bot.App;
import bot.config.Config;
import bot.fetcher.ItemFetcher;
import bot.gson.vinted.VintedAuthResponse;
import bot.gson.vinted.VintedItemResponse;
import bot.item.Item;
import bot.item.impl.VintedItem;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class VintedFetcher implements ItemFetcher {

    @Override
    public List<Item> fetchItems(Config.Search search, Config.Auth auth) {
        List<Item> items = new ArrayList<>();
        List<VintedItemResponse.Item> fetchedItems = fetchItemsFromAPI(search, auth);

        for (VintedItemResponse.Item item : fetchedItems) {
            items.add(new VintedItem(item));
        }
        return items;
    }

    public static List<VintedItemResponse.Item> fetchItemsFromAPI(Config.Search search, Config.Auth auth) {
        try {
            Request request = new Request.Builder()
                    .url(generateVintedApiUrl(search))
                    .header("Authorization", "Bearer " + auth.getVintedBearerToken())
                    .header("Accept", "application/json")
                    .header("Accept-Language", "en-us-fr")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("X-Money-Object", "true")
                    .header("X-Device-Model", "iPhone15,3")
                    .header("X-App-Version", "24.38.0")
                    .header("User-Agent",
                            "vinted-ios Vinted/24.38.0 (lt.manodrabuziai.fr; build:30105; iOS 18.0.0) iPhone15,3")
                    .build();

            Call call = App.OKHTTP_CLIENT.newCall(request);
            try (Response response = call.execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    try (ResponseBody responseBody = response.body()) {
                        String encoding = response.header("Content-Encoding");
                        BufferedReader reader;

                        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                            GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
                            reader = new BufferedReader(new InputStreamReader(gzipStream));
                        } else {
                            reader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
                        }

                        VintedItemResponse deserializedResponse = App.GSON.fromJson(reader,
                                VintedItemResponse.class);

                        if (deserializedResponse.items == null) {
                            System.out.println("No items found for search keyword: " + search.getKeywords());
                            return List.of();
                        }

                        System.out.printf("[VINTED] Fetched %d items for search keyword '%s' %n",
                                deserializedResponse.items.size(),
                                search.getKeywords());

                        return deserializedResponse.items;
                    }
                } else {
                    VintedAuthResponse vintedAuthResponse = refreshToken(auth.getVintedRefreshToken());
                    auth.setVintedBearerToken(vintedAuthResponse.getAccessToken());
                    auth.setVintedRefreshToken(vintedAuthResponse.getRefreshToken());
                    System.out.println("New access token: " + vintedAuthResponse.getAccessToken());
                    System.out.println("New refresh token: " + vintedAuthResponse.getRefreshToken());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public static VintedAuthResponse refreshToken(String refreshToken) {
        String url = "https://www.vinted.fr/oauth/token";

        String jsonBody = "{\n" +
                "  \"client_id\": \"ios\",\n" +
                "  \"refresh_token\": \"" + refreshToken + "\",\n" +
                "  \"grant_type\": \"refresh_token\"\n" +
                "}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-us-fr")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("User-Agent",
                        "vinted-ios Vinted/24.38.0 (lt.manodrabuziai.fr; build:30105; iOS 18.0.0) iPhone15,3")
                .header("Short-Bundle-Version", "24.38.0")
                .header("X-Device-Model", "iPhone15,3")
                .header("X-App-Version", "24.38.0")
                .build();

        try (Response response = App.OKHTTP_CLIENT.newCall(request).execute()) {
            String encoding = response.header("Content-Encoding");

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {

                BufferedReader reader;

                if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                    GZIPInputStream gzipStream = new GZIPInputStream(responseBody.byteStream());
                    reader = new BufferedReader(new InputStreamReader(gzipStream));
                } else {
                    reader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
                }

                VintedAuthResponse authResponse = App.GSON.fromJson(reader, VintedAuthResponse.class);
                return authResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String generateVintedApiUrl(Config.Search search) {
        try {
            String encodedKeywords = URLEncoder.encode(search.getKeywords(), StandardCharsets.UTF_8.toString());

            StringBuilder url = new StringBuilder("https://www.vinted.fr/api/v2/catalog/items?");
            url.append("brand_ids=").append("");
            url.append("&color_ids=").append("");
            url.append("&currency=EUR");
            url.append("&material_ids=").append("");
            url.append("&order=newest_first");
            url.append("&page=").append(1);
            url.append("&per_page=").append(24);
            url.append("&saved_search_id=").append("17285738793");
            url.append("&time=").append(Instant.now().getEpochSecond());
            url.append("&price_from=").append(search.getMinPrice());
            url.append("&price_to=").append(search.getMaxPrice());
            url.append("&search_text=").append(encodedKeywords);
            url.append("&size_ids=").append("");
            url.append("&status_ids=").append("");

            return url.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}