package bot.config;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigLoader {
    private static final String CONFIG_PATH = "config.json";

    public static Config loadConfig() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CONFIG_PATH)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveConfig(Config config) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(CONFIG_PATH)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
