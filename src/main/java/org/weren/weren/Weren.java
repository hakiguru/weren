package org.weren.weren;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.util.Vector;

import java.util.Random;

public final class Weren extends JavaPlugin implements Listener {

    private final Random random = new Random();
    @Override
    public void onEnable() {
        log("&4Error 404: &cthe author was not found!");
        log("&fAuthor: &7&lan author who doesn't exist");
        getServer().getPluginManager().registerEvents(this, this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ServerTimePlaceholder(this).register();
        }
    }
    private void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            player.setInvisible(false);
        }
        log("&fDisabling weren!");
    }
    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location center = player.getLocation();
        player.setInvisible(true);
        ja(center, player);
    }
    private void ja(Location center, Player player) {
        new BukkitRunnable() {
            int ticks = 0;
            final int fticks = 20;
            final double phaseSwitch = 0.8;
            @Override
            public void run() {
                ticks++;
                double progress = (double) ticks / fticks;
                if (ticks > fticks) {
                    if (player.isOnline()) {
                        player.setInvisible(false);
                    }
                    this.cancel();
                    return;
                }
                if (progress >= phaseSwitch && player.isOnline() && player.isInvisible()){
                    player.setInvisible(false);
                }
                for (int i = 0; i<15;i++){
                    spawnPart(center, progress);
                }
            }
        }.runTaskTimer(this, 0L,1L);
    }
    private void spawnPart(Location center, double progress){
        double phase = 0.8;
        double radius = 2.0;
        double angle1 = random.nextDouble() * 2 * Math.PI;
        double angle2 = random.nextDouble() * 2 * Math.PI;
        double x = radius * Math.sin(angle1) * Math.cos(angle2);
        double y = radius * Math.sin(angle1) * Math.sin(angle2);
        double z = radius * Math.cos(angle1);
        Location startFrom = center.clone().add(x,y,z);
        if (progress <= phase){
            double gatherProgress = progress / phase;
            double dx = center.getX() - startFrom.getX();
            double dy = center.getY() - startFrom.getY();
            double dz = center.getZ() - startFrom.getZ();
            double currentX = startFrom.getX() + dx * gatherProgress;
            double currentY = startFrom.getY() + dy * gatherProgress;
            double currentZ = startFrom.getZ() + dz * gatherProgress;
            Location particleLoc = new Location(center.getWorld(), currentX, currentY, currentZ);
            center.getWorld().spawnParticle(Particle.END_ROD, particleLoc, 0,0,0,0,0,null, true);
        } else {
            double speed = 2.0;
            double dirX = (random.nextDouble() - 0.5)*2;
            double dirY = (random.nextDouble() - 0.5)*2;
            double dirZ = (random.nextDouble() - 0.5)*2;
            Vector direction = new Vector(dirX, dirY, dirZ).normalize();
            center.getWorld().spawnParticle(Particle.END_ROD, center, 0, direction.getX(), direction.getY(), direction.getZ(), speed, null,true);
        }
    }
}
