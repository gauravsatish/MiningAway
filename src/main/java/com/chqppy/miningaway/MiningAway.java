package com.chqppy.miningaway;

import com.chqppy.miningaway.commands.KitsCommand;
import com.chqppy.miningaway.commands.setBlocksBrokenCommand;
import com.chqppy.miningaway.enums.Ranks;
import com.chqppy.miningaway.eventhandlers.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MiningAway extends JavaPlugin {
    public HashMap<Player, Integer> blocksBrokenPlayer;

    public HashMap<Player, Ranks> playerRank;
    public List<String> nonDamageableItems;

    /* KIT ITEMS */
    // Nub Kit

    public ItemStack nubSword = new ItemStack(Material.IRON_SWORD);
    public ItemStack nubPick = new ItemStack(Material.IRON_PICKAXE);
    public ItemStack nubAxe = new ItemStack(Material.IRON_AXE);
    public ItemStack nubShovel = new ItemStack(Material.IRON_SHOVEL);

    // Epico Gamer Kit
    public ItemStack epicoSword = new ItemStack(Material.GOLDEN_SWORD);
    public ItemStack epicoPick = new ItemStack(Material.GOLDEN_PICKAXE);
    public ItemStack epicoAxe = new ItemStack(Material.GOLDEN_AXE);
    public ItemStack epicoShovel = new ItemStack(Material.GOLDEN_SHOVEL);

    // Wopi Kit
    public ItemStack wopiSword = new ItemStack(Material.DIAMOND_SWORD);
    public ItemStack wopiPick = new ItemStack(Material.DIAMOND_PICKAXE);
    public ItemStack wopiAxe = new ItemStack(Material.DIAMOND_AXE);
    public ItemStack wopiShovel = new ItemStack(Material.DIAMOND_SHOVEL);

    // Wah Kit
    public ItemStack wahSword = new ItemStack(Material.NETHERITE_SWORD);
    public ItemStack wahPick = new ItemStack(Material.NETHERITE_PICKAXE);
    public ItemStack wahAxe = new ItemStack(Material.NETHERITE_AXE);
    public ItemStack wahShovel = new ItemStack(Material.NETHERITE_SHOVEL);


    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("MiningAway plugin enabled haha get ready to witness a bootleg version of a prison server lmao");

        blocksBrokenPlayer = new HashMap<>();
        playerRank = new HashMap<>();
        nonDamageableItems = new ArrayList<>();

        getCommand("kits").setExecutor(new KitsCommand(this));
        getCommand("setblocksbroken").setExecutor(new setBlocksBrokenCommand(this));

        Bukkit.getPluginManager().registerEvents(new PlayerManager(this), this);
        Bukkit.getPluginManager().registerEvents(new KitsManager(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemManager(this), this);
        Bukkit.getPluginManager().registerEvents(new HungerManager(), this);
        Bukkit.getPluginManager().registerEvents(new SidebarManager(this), this);


        for (Player player : getServer().getOnlinePlayers()) {
            PersistentDataContainer playerData = player.getPersistentDataContainer();

            if (playerData.has(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER)) {
                blocksBrokenPlayer.put(player, playerData.get(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER));
            } else {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER, 0);
                blocksBrokenPlayer.put(player, 0);
            }

            updatePlayerRank(player);
            buildSideBar(player);
        }
        createKitItems();


    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "numBlocksBroken"), PersistentDataType.INTEGER, blocksBrokenPlayer.get(player));
        }
    }

    public void buildSideBar(Player player) {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("test", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "TEST");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score stuff15 = obj.getScore(ChatColor.AQUA + "Hello");
        stuff15.setScore(15);

        Score stuff14 = obj.getScore("---------------");
        stuff14.setScore(14);

        Score stuff13 = obj.getScore("");
        stuff13.setScore(13);

        Score stuff12 = obj.getScore(ChatColor.DARK_PURPLE + "Rank:");
        stuff12.setScore(12);

        Team rank = board.registerNewTeam("player_rank");
        rank.addEntry(ChatColor.DARK_PURPLE.toString());
        rank.setPrefix("");
        rank.setSuffix(playerRank.get(player).getRankString());
        obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(11);

        Score stuff10 = obj.getScore("");
        stuff10.setScore(10);

        Score stuff9 = obj.getScore(ChatColor.DARK_AQUA + "Blocks Broken: ");
        stuff9.setScore(9);

        Team brokenBlocks = board.registerNewTeam("blocks_broken");
        brokenBlocks.addEntry(ChatColor.AQUA.toString());
        brokenBlocks.setPrefix("");
        brokenBlocks.setSuffix(ChatColor.GOLD + "" + blocksBrokenPlayer.get(player) + "");
        obj.getScore(ChatColor.AQUA.toString()).setScore(8);

        player.setScoreboard(board);
    }

    public void applyKitsUI(Player player) {

        // THE BEGINNING
        Inventory kitGUI = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Select a kit");

        // LORE
        List<String> nubKitRepLore = new ArrayList<>();
        nubKitRepLore.add("");
        nubKitRepLore.add(ChatColor.AQUA + "Click to receive the nub kit");
        nubKitRepLore.add(" ");
        nubKitRepLore.add("  ");
        nubKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + "Available");

        List<String> epicoKitRepLore = new ArrayList<>();
        epicoKitRepLore.add("");
        epicoKitRepLore.add(ChatColor.AQUA + "Click to receive the epico gamer kit");

        String epicoKitStatus;
        String epicoKitRemBlocks;
        epicoKitRepLore.add("");
        epicoKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 500) {
            epicoKitStatus = ChatColor.GREEN + "Available";
            epicoKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + epicoKitStatus);

        } else {
            epicoKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 500 - blocksBrokenPlayer.get(player);
            epicoKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            epicoKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + epicoKitStatus);
            epicoKitRepLore.add(epicoKitRemBlocks);
        }

        List<String> wopiKitRepLore = new ArrayList<>();
        wopiKitRepLore.add("");
        wopiKitRepLore.add(ChatColor.AQUA + "Click to receive the wopi gamer kit");

        String wopiKitStatus;
        String wopiKitRemBlocks;
        wopiKitRepLore.add("");
        wopiKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 3500) {
            wopiKitStatus = ChatColor.GREEN + "Available";
            wopiKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + wopiKitStatus);

        } else {
            wopiKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 3500 - blocksBrokenPlayer.get(player);
            wopiKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            wopiKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + wopiKitStatus);
            wopiKitRepLore.add(wopiKitRemBlocks);
        }


        List<String> wahKitRepLore = new ArrayList<>();
        wahKitRepLore.add("");
        wahKitRepLore.add(ChatColor.AQUA + "Click to receive the wah gamer kit");

        String wahKitStatus;
        String wahKitRemBlocks;
        wahKitRepLore.add("");
        wahKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 10000) {
            wahKitStatus = ChatColor.GREEN + "Available";
            wahKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + wahKitStatus);

        } else {
            wahKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 10000 - blocksBrokenPlayer.get(player);
            wahKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            wahKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + wahKitStatus);
            wahKitRepLore.add(wahKitRemBlocks);
        }

        // ITEMSTACKS
        ItemStack nubKitRep = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta nubKitRepMeta = nubKitRep.getItemMeta();
        nubKitRepMeta.setDisplayName(ChatColor.GOLD + "Nub Kit");
        nubKitRepMeta.setLore(nubKitRepLore);
        nubKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        nubKitRep.setItemMeta(nubKitRepMeta);
        nubKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack epicoKitRep = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta epicoKitRepMeta = epicoKitRep.getItemMeta();
        epicoKitRepMeta.setDisplayName(ChatColor.GOLD + "Epico Gamer kit");
        epicoKitRepMeta.setLore(epicoKitRepLore);
        epicoKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        epicoKitRep.setItemMeta(epicoKitRepMeta);
        epicoKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack wopiKitRep = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta wopiKitRepMeta = wopiKitRep.getItemMeta();
        wopiKitRepMeta.setDisplayName(ChatColor.GOLD + "Wopi Gamer kit");
        wopiKitRepMeta.setLore(wopiKitRepLore);
        wopiKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wopiKitRep.setItemMeta(wopiKitRepMeta);
        wopiKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack wahKitRep = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta wahKitRepMeta = wahKitRep.getItemMeta();
        wahKitRepMeta.setDisplayName(ChatColor.GOLD + "Wah");
        wahKitRepMeta.setLore(wahKitRepLore);
        wahKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wahKitRep.setItemMeta(wahKitRepMeta);
        wahKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        // ITEM SETTING
        kitGUI.setItem(0, nubKitRep);
        kitGUI.setItem(1, epicoKitRep);
        kitGUI.setItem(2, wopiKitRep);
        kitGUI.setItem(3, wahKitRep);

        // FINAL
        player.openInventory(kitGUI);

    }

    public void createKitItems() {


        /* ITEM METAS */
        // Nub Kit Items
        ItemMeta nubSwordMeta = nubSword.getItemMeta();
        ItemMeta nubPickMeta = nubPick.getItemMeta();
        ItemMeta nubAxeMeta = nubAxe.getItemMeta();
        ItemMeta nubShovelMeta = nubShovel.getItemMeta();

        // Epico Kit Items
        ItemMeta epicoSwordMeta = epicoSword.getItemMeta();
        ItemMeta epicoPickMeta = epicoPick.getItemMeta();
        ItemMeta epicoAxeMeta = epicoAxe.getItemMeta();
        ItemMeta epicoShovelMeta = epicoShovel.getItemMeta();

        // Wopi Kit Items
        ItemMeta wopiSwordMeta = wopiSword.getItemMeta();
        ItemMeta wopiPickMeta = wopiPick.getItemMeta();
        ItemMeta wopiAxeMeta = wopiAxe.getItemMeta();
        ItemMeta wopiShovelMeta = wopiShovel.getItemMeta();

        // Wah Kit Items
        ItemMeta wahSwordMeta = wahSword.getItemMeta();
        ItemMeta wahPickMeta = wahPick.getItemMeta();
        ItemMeta wahAxeMeta = wahAxe.getItemMeta();
        ItemMeta wahShovelMeta = wahShovel.getItemMeta();


        /* SETTING DISPLAY NAMES */
        // Nub Kit Items
        nubSwordMeta.setDisplayName(ChatColor.AQUA + "Nub Sword");
        nonDamageableItems.add(ChatColor.AQUA + "Nub Sword");
        nubPickMeta.setDisplayName(ChatColor.AQUA + "Nub Pickaxe");
        nonDamageableItems.add(ChatColor.AQUA + "Nub Pickaxe");
        nubAxeMeta.setDisplayName(ChatColor.AQUA + "Nub Axe");
        nonDamageableItems.add(ChatColor.AQUA + "Nub Axe");
        nubShovelMeta.setDisplayName(ChatColor.AQUA + "Nub Shovel");
        nonDamageableItems.add(ChatColor.AQUA + "Nub Shovel");

        // Epico Gamer Kit Items
        epicoSwordMeta.setDisplayName(ChatColor.GOLD + "Epico Gamer Sword");
        nonDamageableItems.add(ChatColor.GOLD + "Epico Gamer Sword");
        epicoPickMeta.setDisplayName(ChatColor.GOLD + "Epico Gamer Pickaxe");
        nonDamageableItems.add(ChatColor.GOLD + "Epico Gamer Pickaxe");
        epicoAxeMeta.setDisplayName(ChatColor.GOLD + "Epico Gamer Axe");
        nonDamageableItems.add(ChatColor.GOLD + "Epico Gamer Axe");
        epicoShovelMeta.setDisplayName(ChatColor.GOLD + "Epico Gamer Shovel");
        nonDamageableItems.add(ChatColor.GOLD + "Epico Gamer Shovel");

        // Wopi Kit Items
        wopiSwordMeta.setDisplayName(ChatColor.BLUE + "Wopi Gamer Sword");
        nonDamageableItems.add(ChatColor.BLUE + "Wopi Gamer Sword");
        wopiPickMeta.setDisplayName(ChatColor.BLUE + "Wopi Gamer Pickaxe");
        nonDamageableItems.add(ChatColor.BLUE + "Wopi Gamer Pickaxe");
        wopiAxeMeta.setDisplayName(ChatColor.BLUE + "Wopi Gamer Axe");
        nonDamageableItems.add(ChatColor.BLUE + "Wopi Gamer Axe");
        wopiShovelMeta.setDisplayName(ChatColor.BLUE + "Wopi Gamer Shovel");
        nonDamageableItems.add(ChatColor.BLUE + "Wopi Gamer Shovel");

        // Wah Kit Items
        wahSwordMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wah");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Wah");
        wahPickMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wah");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Wah");
        wahAxeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wah");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Wah");
        wahShovelMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wah");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Wah");


        /* SETTING ITEM METAS */
        // Nub Kit Items
        nubSword.setItemMeta(nubSwordMeta);
        nubPick.setItemMeta(nubPickMeta);
        nubAxe.setItemMeta(nubAxeMeta);
        nubShovel.setItemMeta(nubShovelMeta);

        // Epico Gamer Kit Items
        epicoSword.setItemMeta(epicoSwordMeta);
        epicoPick.setItemMeta(epicoPickMeta);
        epicoAxe.setItemMeta(epicoAxeMeta);
        epicoShovel.setItemMeta(epicoShovelMeta);

        // Wopi Kit Items
        wopiSword.setItemMeta(wopiSwordMeta);
        wopiPick.setItemMeta(wopiPickMeta);
        wopiAxe.setItemMeta(wopiAxeMeta);
        wopiShovel.setItemMeta(wopiShovelMeta);

        // Wah Kit Items
        wahSword.setItemMeta(wahSwordMeta);
        wahPick.setItemMeta(wahPickMeta);
        wahAxe.setItemMeta(wahAxeMeta);
        wahShovel.setItemMeta(wahShovelMeta);

        /* ADDING ENCHANTMENTS */
        addEnchants(nubSword, nubPick, nubAxe, nubShovel, 1);
        addEnchants(epicoSword, epicoPick, epicoAxe, epicoShovel, 3);
        addEnchants(wopiSword, wopiPick, wopiAxe, wopiShovel, 5);
        addEnchants(wahSword, wahPick, wahAxe, wahShovel, 7);

    }

    public void addEnchants(ItemStack sword, ItemStack pickaxe, ItemStack axe, ItemStack shovel, int level) {
        if (level == 1 || level == 3 || level == 5 || level == 7) {
            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level);
            pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, level);
            axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level);
            axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, level);
            shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, level);

            if (level == 1) {
                pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
            } else if (level == 3) {
                pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
            } else if (level == 5) {
                pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            } else if (level == 7) {
                pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 5);
            }
        } else {
            System.out.println("enter correct level for enchanting");
        }
    }

    public void updatePlayerRank(Player player) {

        Ranks oldRank = playerRank.get(player);
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (blocksBrokenPlayer.get(player) >= 0 && blocksBrokenPlayer.get(player) < 300) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.NUB);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 500 && blocksBrokenPlayer.get(player) < 3500) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.EPICOGAMER);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 3500 && blocksBrokenPlayer.get(player) < 10000) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.WOPIPIRO);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 10000) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.WAH);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        }

        if (oldRank != playerRank.get(player)) {
            if (playerRank.get(player).equals(Ranks.NUB)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "NUB");
            } else if (playerRank.get(player).equals(Ranks.EPICOGAMER)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "EPICOGAMER");
            } else if (playerRank.get(player).equals(Ranks.WOPIPIRO)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "WOPIPIRO");
            } else if (playerRank.get(player).equals(Ranks.WAH)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "WAH");
            }
        }
    }

}
