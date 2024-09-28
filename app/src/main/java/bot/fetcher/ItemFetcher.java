package bot.fetcher;

import java.util.List;

import bot.config.Config;
import bot.item.Item;

public interface ItemFetcher {
    List<Item> fetchItems(Config.Search search, Config.Auth auth);
}