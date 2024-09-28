package bot;

import java.util.Timer;

import com.google.gson.Gson;

import bot.config.Config;
import bot.config.ConfigLoader;
import bot.task.MonitorTask;
import okhttp3.OkHttpClient;

public class App {
    public static final Gson GSON = new Gson();
    public static final OkHttpClient OKHTTP_CLIENT = new OkHttpClient();

    public static void main(String[] args) {
        Config config = ConfigLoader.loadConfig();
        System.out.println("Loaded config: " + config.getSearches().size() + " searches.");
        for (Config.Search search : config.getSearches()) {
            Timer timer = new Timer();
            MonitorTask task = new MonitorTask(search, config.getAuth());
            timer.schedule(task, 0, search.getDelay());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConfigLoader.saveConfig(config);
            OKHTTP_CLIENT.dispatcher().cancelAll();
        }));
    }
}
