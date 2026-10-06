package org.weren.weren;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.time.LocalTime;

public class ServerTimePlaceholder extends PlaceholderExpansion {

    private final Weren plugin; // Ссылка на ваш главный класс плагина

    // Конструктор для внедрения зависимости (Dependency Injection)
    public ServerTimePlaceholder(Weren plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "servertime";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors()); // Берём автора из plugin.yml
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion(); // Берём версию из plugin.yml
    }

    // Важно! Запрещаем PlaceholderAPI выгружать наше расширение при перезагрузке
    @Override
    public boolean persist() {
        return true;
    }

    // Основная логика (без изменений)
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        LocalTime now = LocalTime.now();
        LocalTime nightStart = LocalTime.of(22, 0);
        LocalTime nightEnd = LocalTime.of(7, 0);

        if (!now.isBefore(nightStart) || now.isBefore(nightEnd)) {
            return "🌙";
        } else {
            return "☀️";
        }
    }
}
