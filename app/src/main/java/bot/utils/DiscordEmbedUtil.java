package bot.utils;

import bot.config.Config;
import bot.item.Item;
import bot.webhook.DiscordWebhook;
import java.awt.Color;
import java.io.IOException;

public class DiscordEmbedUtil {
    public static void sendItemToWebhook(Config.Search search, Item item) {
        DiscordWebhook webhook = new DiscordWebhook(search.getWebhookUrl());

        String title = formatTitle(item.getTitle(), item.getId());
        String description = formatDescription(item.getDescription());
        String price = item.getPrice();
        String url = item.getUrl();
        String location = item.getLocation();
        boolean hasShipping = item.hasShipping();
        String imageUrl = item.getImageUrl();

        DiscordWebhook.EmbedObject embed = createEmbed(title, description, price, location, url, hasShipping, imageUrl);

        webhook.addEmbed(embed);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DiscordWebhook.EmbedObject createEmbed(String title, String description, String price,
                                                          String location, String url, boolean hasShipping, String imageUrl) {
        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject()
                .setTitle(sanitizeForJson(title))
                .setDescription(sanitizeForJson(description))
                .setColor(Color.GREEN)
                .addField("Price", price, true)
                .addField("Location", location, true)
                .addField("Shipping", hasShipping ? "Available" : "Not available", true)
                .setUrl(url);

        if (imageUrl != null) {
            embed.setThumbnail(imageUrl);
            embed.setImage(imageUrl);
        }

        return embed;
    }

    private static String formatTitle(String originalTitle, String id) {
        String title = originalTitle + " (ID: " + id + ")";
        return title.length() > 250 ? title.substring(0, 250) + "..." : title;
    }

    private static String formatDescription(String originalDescription) {
        if (originalDescription == null) {
            return "No description available.";
        }
        return originalDescription.length() > 500 ? originalDescription.substring(0, 500) + "..." : originalDescription;
    }


    private static String sanitizeForJson(String input) {
        if (input == null) {
            return null;
        }

        String sanitized = input
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replaceAll("[^\\x20-\\x7E]", "");

        return sanitized.trim();
    }
}
