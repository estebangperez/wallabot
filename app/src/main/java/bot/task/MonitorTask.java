package bot.task;

import java.util.*;

import bot.config.Config;
import bot.fetcher.ItemFetcher;
import bot.fetcher.impl.VintedFetcher;
import bot.fetcher.impl.WallapopFetcher;
import bot.item.Item;
import bot.utils.DiscordEmbedUtil;

public class MonitorTask extends TimerTask {

    private final Config.Search search;
    private final Config.Auth auth;
    private final Set<String> cachedItemIds = new HashSet<>();
    private final List<ItemFetcher> fetchers;

    public MonitorTask(Config.Search search, Config.Auth auth) {
        this.search = search;
        this.auth = auth;
        this.fetchers = List.of(new WallapopFetcher(), new VintedFetcher());
    }

    @Override
    public void run() {
        List<Item> allItems = new ArrayList<>();

        for (ItemFetcher fetcher : fetchers) {
            allItems.addAll(fetcher.fetchItems(search, auth));
        }

        if (cachedItemIds.isEmpty()) {
            for (Item item : allItems) {
                cacheItem(item.getId());
            }
            return;
        }

        for (Item item : allItems) {
            if (isCached(item.getId())) {
                continue;
            }

            DiscordEmbedUtil.sendItemToWebhook(search, item);
            System.out.println("New item found: " + item.getTitle() + " " + item.getUrl());

            cacheItem(item.getId());
        }
    }

    private boolean isCached(String itemId) {
        return cachedItemIds.contains(itemId);
    }

    private void cacheItem(String itemId) {
        cachedItemIds.add(itemId);
    }
}