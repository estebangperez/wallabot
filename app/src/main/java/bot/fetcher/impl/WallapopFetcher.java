package bot.fetcher.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import bot.App;
import bot.config.Config;
import bot.fetcher.ItemFetcher;
import bot.gson.wallapop.WallapopItemInfoResponse;
import bot.gson.wallapop.WallapopSearchResponse;
import bot.item.Item;
import bot.item.impl.WallapopItem;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WallapopFetcher implements ItemFetcher {

    @Override
    public List<Item> fetchItems(Config.Search search, Config.Auth auth) {
        List<Item> items = new ArrayList<>();
        List<WallapopSearchResponse.Item> fetchedItems = fetchItemsFromAPI(search, auth);

        for (WallapopSearchResponse.Item item : fetchedItems) {
            if (item.reserved.flag) {
                continue;
            }
            WallapopItemInfoResponse itemInfo = gatherItemFromId(search, auth, item.id);
            if (itemInfo.shipping.userAllowsShipping) {
                items.add(new WallapopItem(itemInfo));
            }
        }
        return items;
    }

    public static List<WallapopSearchResponse.Item> fetchItemsFromAPI(Config.Search search, Config.Auth auth) {
        try {
            Request request = new Request.Builder()
                    .url(generateWallapopApiUrl(search))
                    .header("X-LocationLatitude", String.valueOf(search.getLatitude()))
                    .header("Authorization", "Bearer " + auth.getWallapopBearerToken())
                    .header("Accept", "*/*")
                    .header("Accept-Language", "es-ES;q=1.0, en-ES;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("X-LocationAccuracy", "6.672700003602631")
                    .header("User-Agent", "Wallapop/1.230.0 (iPhone; iOS 18.0; Scale/3.00)")
                    .header("X-LocationLongitude", String.valueOf(search.getLongitude()))
                    .header("X-AppVersion", "763")
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

                        WallapopSearchResponse deserializedResponse = App.GSON.fromJson(reader,
                                WallapopSearchResponse.class);
                        System.out.printf("[WALLAPOP] Fetched %d items for search keyword '%s' %n",
                                deserializedResponse.data.section.payload.items.size(),
                                search.getKeywords());

                        return deserializedResponse.data.section.payload.items;
                    }
                } else {
                    System.out.println("Response code: " + response.code());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public static WallapopItemInfoResponse gatherItemFromId(Config.Search search, Config.Auth auth, String id) {
        try {
            Request request = new Request.Builder()
                    .url("https://api.wallapop.com:443/api/v3/items/" + id)
                    .header("X-LocationLatitude", String.valueOf(search.getLatitude()))
                    .header("Authorization", "Bearer " + auth.getWallapopBearerToken())
                    .header("Accept", "*/*")
                    .header("Accept-Language", "es-ES;q=1.0, en-ES;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("X-LocationAccuracy", "6.672700003602631")
                    .header("X-DeviceOS", "2")
                    .header("User-Agent", "Wallapop/1.230.0 (iPhone; iOS 18.0; Scale/3.00)")
                    .header("X-LocationLongitude", String.valueOf(search.getLongitude()))
                    .header("X-AppVersion", "763")
                    .build();

            try (Response response = App.OKHTTP_CLIENT.newCall(request).execute()) {
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

                        WallapopItemInfoResponse deserializedResponse = App.GSON.fromJson(reader,
                                WallapopItemInfoResponse.class);

                        return deserializedResponse;
                    }
                } else {
                    System.out.println("Response code: " + response.code());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateWallapopApiUrl(Config.Search search) {
        try {
            String encodedKeywords = URLEncoder.encode(search.getKeywords(), StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");

            StringBuilder url = new StringBuilder("https://api.wallapop.com:443/api/v3/search?");
            url.append("keywords=").append(encodedKeywords);
            url.append("&latitude=").append(search.getLatitude());
            url.append("&longitude=").append(search.getLongitude());
            url.append("&max_sale_price=").append(search.getMaxPrice());
            url.append("&min_sale_price=").append(search.getMinPrice());
            url.append("&order_by=newest&source=search_box");

            return url.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}