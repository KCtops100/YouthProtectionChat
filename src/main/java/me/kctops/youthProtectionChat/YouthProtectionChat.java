package me.kctops.youthProtectionChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class YouthProtectionChat extends JavaPlugin implements Listener, TabExecutor {

    private final Set<UUID> adults = new HashSet<>();

    @Override
    public void onEnable() {
        // Register the chat event listener
        Bukkit.getPluginManager().registerEvents(this, this);

        // Register commands
        Objects.requireNonNull(getCommand("setrole")).setExecutor(this);
        Objects.requireNonNull(getCommand("setrole")).setTabCompleter(this);

        Objects.requireNonNull(getCommand("listyouth")).setExecutor(this);
        Objects.requireNonNull(getCommand("listadults")).setExecutor(this);
        Objects.requireNonNull(getCommand("listnotassigned")).setExecutor(this);

        getLogger().info("YouthProtectionChat plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("YouthProtectionChat plugin disabled.");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        UUID senderUUID = sender.getUniqueId();

        if (adults.contains(senderUUID)) {
            // Check if there is at least one other adult online
            boolean anotherAdultOnline = Bukkit.getOnlinePlayers().stream()
                    .anyMatch(player -> !player.getUniqueId().equals(senderUUID) && adults.contains(player.getUniqueId()));

            if (!anotherAdultOnline) {
                sender.sendMessage(ChatColor.RED + "You cannot chat as an adult unless another adult is online.");
                event.setCancelled(true);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase()) {
            case "setrole":
                return handleSetRoleCommand(sender, args);
            case "listyouth":
                return handleListYouthCommand(sender);
            case "listadults":
                return handleListAdultsCommand(sender);
            case "listnotassigned":
                return handleListNotAssignedCommand(sender);
            default:
                return false;
        }
    }

    private boolean handleSetRoleCommand(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /setrole <player> <youth|adult>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        String role = args[1].toLowerCase();
        if (role.equals("youth")) {
            adults.remove(target.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + target.getName() + " is now set as youth.");
        } else if (role.equals("adult")) {
            adults.add(target.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + target.getName() + " is now set as adult.");
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid role. Use 'youth' or 'adult'.");
        }

        return true;
    }

    private boolean handleListYouthCommand(CommandSender sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        List<String> youthPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!adults.contains(player.getUniqueId())) {
                youthPlayers.add(player.getName());
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Youth players online: " + String.join(", ", youthPlayers));
        return true;
    }

    private boolean handleListAdultsCommand(CommandSender sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        List<String> adultPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (adults.contains(player.getUniqueId())) {
                adultPlayers.add(player.getName());
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Adult players online: " + String.join(", ", adultPlayers));
        return true;
    }

    private boolean handleListNotAssignedCommand(CommandSender sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        List<String> notAssignedPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!adults.contains(player.getUniqueId())) {
                notAssignedPlayers.add(player.getName());
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Players without an assigned role: " + String.join(", ", notAssignedPlayers));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("setrole")) {
            if (args.length == 1) {
                return null; // Suggest online player names
            } else if (args.length == 2) {
                return Arrays.asList("youth", "adult");
            }
        }
        return Collections.emptyList();
    }
}
