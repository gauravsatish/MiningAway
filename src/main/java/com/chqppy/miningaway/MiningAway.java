package com.chqppy.miningaway;

// TODO: Add exception handling to commands

import com.chqppy.miningaway.commands.KitsCommand;
import com.chqppy.miningaway.commands.ResetCommand;
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
    // Basic Kit

    public ItemStack basicSword = new ItemStack(Material.IRON_SWORD);
    public ItemStack basicPick = new ItemStack(Material.IRON_PICKAXE);
    public ItemStack basicAxe = new ItemStack(Material.IRON_AXE);
    public ItemStack basicShovel = new ItemStack(Material.IRON_SHOVEL);

    // senator Kit
    public ItemStack senatorSword = new ItemStack(Material.GOLDEN_SWORD);
    public ItemStack senatorPick = new ItemStack(Material.GOLDEN_PICKAXE);
    public ItemStack senatorAxe = new ItemStack(Material.GOLDEN_AXE);
    public ItemStack senatorShovel = new ItemStack(Material.GOLDEN_SHOVEL);

    // padawan Kit
    public ItemStack padawanSword = new ItemStack(Material.DIAMOND_SWORD);
    public ItemStack padawanPick = new ItemStack(Material.DIAMOND_PICKAXE);
    public ItemStack padawanAxe = new ItemStack(Material.DIAMOND_AXE);
    public ItemStack padawanShovel = new ItemStack(Material.DIAMOND_SHOVEL);

    // jedi Kit
    public ItemStack jediSword = new ItemStack(Material.NETHERITE_SWORD);
    public ItemStack jediPick = new ItemStack(Material.NETHERITE_PICKAXE);
    public ItemStack jediAxe = new ItemStack(Material.NETHERITE_AXE);
    public ItemStack jediShovel = new ItemStack(Material.NETHERITE_SHOVEL);


    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("MiningAway plugin enabled haha get ready to witness a bootleg version of a prison server lmao");

        blocksBrokenPlayer = new HashMap<>();
        playerRank = new HashMap<>();
        nonDamageableItems = new ArrayList<>();

        getCommand("kits").setExecutor(new KitsCommand(this));
        getCommand("setblocksbroken").setExecutor(new setBlocksBrokenCommand(this));
        getCommand("reset").setExecutor(new ResetCommand(this));

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

        Objective obj = board.registerNewObjective("test", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "Mining Away");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score stuff15 = obj.getScore(ChatColor.GRAY + "" + ChatColor.ITALIC + "idk what to mine");
        stuff15.setScore(15);

        Score stuff14 = obj.getScore(ChatColor.GRAY + "" + ChatColor.ITALIC + "but i'll mine it anyway");
        stuff14.setScore(14);

        Score stuff13 = obj.getScore("  ");
        stuff13.setScore(13);

        Score stuff12 = obj.getScore(ChatColor.GRAY + "---------------");
        stuff12.setScore(12);

        Score stuff11 = obj.getScore(" ");
        stuff11.setScore(11);

        Score stuff10 = obj.getScore(ChatColor.DARK_PURPLE + "Rank:");
        stuff10.setScore(10);

        Team rank = board.registerNewTeam("player_rank");
        rank.addEntry(ChatColor.DARK_PURPLE.toString());
        rank.setPrefix("");
        rank.setSuffix(playerRank.get(player).getRankString());
        obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(9);

        Score stuff8 = obj.getScore("");
        stuff8.setScore(8);

        Score stuff7 = obj.getScore(ChatColor.DARK_AQUA + "Blocks Broken: ");
        stuff7.setScore(7);

        Team brokenBlocks = board.registerNewTeam("blocks_broken");
        brokenBlocks.addEntry(ChatColor.AQUA.toString());
        brokenBlocks.setPrefix("");
        brokenBlocks.setSuffix(ChatColor.GOLD + "" + blocksBrokenPlayer.get(player) + "");
        obj.getScore(ChatColor.AQUA.toString()).setScore(6);

        player.setScoreboard(board);
    }

    public void applyKitsUI(Player player) {

        // THE BEGINNING
        Inventory kitGUI = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Select a kit");

        // LORE
        List<String> basicKitRepLore = new ArrayList<>();
        basicKitRepLore.add("");
        basicKitRepLore.add(ChatColor.AQUA + "Click to receive the basic kit");
        basicKitRepLore.add(" ");
        basicKitRepLore.add("  ");
        basicKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + "Available");

        List<String> senatorKitRepLore = new ArrayList<>();
        senatorKitRepLore.add("");
        senatorKitRepLore.add(ChatColor.AQUA + "Click to receive the senator kit");

        String senatorKitStatus;
        String senatorKitRemBlocks;
        senatorKitRepLore.add("");
        senatorKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 500) {
            senatorKitStatus = ChatColor.GREEN + "Available";
            senatorKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + senatorKitStatus);

        } else {
            senatorKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 500 - blocksBrokenPlayer.get(player);
            senatorKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            senatorKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + senatorKitStatus);
            senatorKitRepLore.add(senatorKitRemBlocks);
        }

        List<String> padawanKitRepLore = new ArrayList<>();
        padawanKitRepLore.add("");
        padawanKitRepLore.add(ChatColor.AQUA + "Click to receive the padawan kit");

        String padawanKitStatus;
        String padawanKitRemBlocks;
        padawanKitRepLore.add("");
        padawanKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 3500) {
            padawanKitStatus = ChatColor.GREEN + "Available";
            padawanKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + padawanKitStatus);

        } else {
            padawanKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 3500 - blocksBrokenPlayer.get(player);
            padawanKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            padawanKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + padawanKitStatus);
            padawanKitRepLore.add(padawanKitRemBlocks);
        }


        List<String> jediKitRepLore = new ArrayList<>();
        jediKitRepLore.add("");
        jediKitRepLore.add(ChatColor.AQUA + "Click to receive the jedi kit");

        String jediKitStatus;
        String jediKitRemBlocks;
        jediKitRepLore.add("");
        jediKitRepLore.add("");
        if (blocksBrokenPlayer.get(player) >= 10000) {
            jediKitStatus = ChatColor.GREEN + "Available";
            jediKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + jediKitStatus);

        } else {
            jediKitStatus = ChatColor.RED + "Unavailable";
            int remBlocks = 10000 - blocksBrokenPlayer.get(player);
            jediKitRemBlocks = ChatColor.GOLD + Integer.toString(remBlocks) + ChatColor.RED + " blocks remaining";
            jediKitRepLore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "STATUS: " + ChatColor.GREEN + jediKitStatus);
            jediKitRepLore.add(jediKitRemBlocks);
        }

        // ITEMSTACKS
        ItemStack basicKitRep = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta basicKitRepMeta = basicKitRep.getItemMeta();
        basicKitRepMeta.setDisplayName(ChatColor.GOLD + "Basic Kit");
        basicKitRepMeta.setLore(basicKitRepLore);
        basicKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        basicKitRep.setItemMeta(basicKitRepMeta);
        basicKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack senatorKitRep = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta senatorKitRepMeta = senatorKitRep.getItemMeta();
        senatorKitRepMeta.setDisplayName(ChatColor.GOLD + "Senator kit");
        senatorKitRepMeta.setLore(senatorKitRepLore);
        senatorKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        senatorKitRep.setItemMeta(senatorKitRepMeta);
        senatorKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack padawanKitRep = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta padawanKitRepMeta = padawanKitRep.getItemMeta();
        padawanKitRepMeta.setDisplayName(ChatColor.GOLD + "Padawan kit");
        padawanKitRepMeta.setLore(padawanKitRepLore);
        padawanKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        padawanKitRep.setItemMeta(padawanKitRepMeta);
        padawanKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        ItemStack jediKitRep = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta jediKitRepMeta = jediKitRep.getItemMeta();
        jediKitRepMeta.setDisplayName(ChatColor.GOLD + "Jedi Kit");
        jediKitRepMeta.setLore(jediKitRepLore);
        jediKitRepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        jediKitRep.setItemMeta(jediKitRepMeta);
        jediKitRep.addEnchantment(Enchantment.DIG_SPEED, 1);

        // ITEM SETTING
        kitGUI.setItem(0, basicKitRep);
        kitGUI.setItem(1, senatorKitRep);
        kitGUI.setItem(2, padawanKitRep);
        kitGUI.setItem(3, jediKitRep);

        // FINAL
        player.openInventory(kitGUI);

    }

    public void createKitItems() {


        /* ITEM METAS */
        // basic Kit Items
        ItemMeta basicSwordMeta = basicSword.getItemMeta();
        ItemMeta basicPickMeta = basicPick.getItemMeta();
        ItemMeta basicAxeMeta = basicAxe.getItemMeta();
        ItemMeta basicShovelMeta = basicShovel.getItemMeta();

        // senator Kit Items
        ItemMeta senatorSwordMeta = senatorSword.getItemMeta();
        ItemMeta senatorPickMeta = senatorPick.getItemMeta();
        ItemMeta senatorAxeMeta = senatorAxe.getItemMeta();
        ItemMeta senatorShovelMeta = senatorShovel.getItemMeta();

        // padawan Kit Items
        ItemMeta padawanSwordMeta = padawanSword.getItemMeta();
        ItemMeta padawanPickMeta = padawanPick.getItemMeta();
        ItemMeta padawanAxeMeta = padawanAxe.getItemMeta();
        ItemMeta padawanShovelMeta = padawanShovel.getItemMeta();

        // jedi Kit Items
        ItemMeta jediSwordMeta = jediSword.getItemMeta();
        ItemMeta jediPickMeta = jediPick.getItemMeta();
        ItemMeta jediAxeMeta = jediAxe.getItemMeta();
        ItemMeta jediShovelMeta = jediShovel.getItemMeta();


        /* SETTING DISPLAY NAMES */
        // basic Kit Items
        basicSwordMeta.setDisplayName(ChatColor.AQUA + "Basic Sword");
        nonDamageableItems.add(ChatColor.AQUA + "Basic Sword");
        basicPickMeta.setDisplayName(ChatColor.AQUA + "Basic Pickaxe");
        nonDamageableItems.add(ChatColor.AQUA + "Basic Pickaxe");
        basicAxeMeta.setDisplayName(ChatColor.AQUA + "Basic Axe");
        nonDamageableItems.add(ChatColor.AQUA + "Basic Axe");
        basicShovelMeta.setDisplayName(ChatColor.AQUA + "Basic Shovel");
        nonDamageableItems.add(ChatColor.AQUA + "Basic Shovel");

        // senator Kit Items
        senatorSwordMeta.setDisplayName(ChatColor.GOLD + "Senator Sword");
        nonDamageableItems.add(ChatColor.GOLD + "Senator Sword");
        senatorPickMeta.setDisplayName(ChatColor.GOLD + "Senator Pickaxe");
        nonDamageableItems.add(ChatColor.GOLD + "Senator Pickaxe");
        senatorAxeMeta.setDisplayName(ChatColor.GOLD + "Senator Axe");
        nonDamageableItems.add(ChatColor.GOLD + "Senator Axe");
        senatorShovelMeta.setDisplayName(ChatColor.GOLD + "Senator Shovel");
        nonDamageableItems.add(ChatColor.GOLD + "Senator Shovel");

        // padawan Kit Items
        padawanSwordMeta.setDisplayName(ChatColor.BLUE + "Padawan Lightsaber");
        nonDamageableItems.add(ChatColor.BLUE + "Padawan Lightsaber");
        padawanPickMeta.setDisplayName(ChatColor.BLUE + "Padawan Pickaxe");
        nonDamageableItems.add(ChatColor.BLUE + "Padawan Pickaxe");
        padawanAxeMeta.setDisplayName(ChatColor.BLUE + "Padawan Pickaxe");
        nonDamageableItems.add(ChatColor.BLUE + "Padawan Axe");
        padawanShovelMeta.setDisplayName(ChatColor.BLUE + "Padawan Shovel");
        nonDamageableItems.add(ChatColor.BLUE + "Padawan Shovel");

        // jedi Kit Items
        jediSwordMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Jedi Lightsaber");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Jedi Lightsaber");
        jediPickMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Jedi Pickaxe");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Jedi Pickaxe");
        jediAxeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Jedi Axe");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Jedi Axe");
        jediShovelMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Jedi Shovel");
        nonDamageableItems.add(ChatColor.LIGHT_PURPLE + "Jedi Shovel");


        /* SETTING ITEM METAS */
        // basic Kit Items
        basicSword.setItemMeta(basicSwordMeta);
        basicPick.setItemMeta(basicPickMeta);
        basicAxe.setItemMeta(basicAxeMeta);
        basicShovel.setItemMeta(basicShovelMeta);

        // senator Kit Items
        senatorSword.setItemMeta(senatorSwordMeta);
        senatorPick.setItemMeta(senatorPickMeta);
        senatorAxe.setItemMeta(senatorAxeMeta);
        senatorShovel.setItemMeta(senatorShovelMeta);

        // padawan Kit Items
        padawanSword.setItemMeta(padawanSwordMeta);
        padawanPick.setItemMeta(padawanPickMeta);
        padawanAxe.setItemMeta(padawanAxeMeta);
        padawanShovel.setItemMeta(padawanShovelMeta);

        // jedi Kit Items
        jediSword.setItemMeta(jediSwordMeta);
        jediPick.setItemMeta(jediPickMeta);
        jediAxe.setItemMeta(jediAxeMeta);
        jediShovel.setItemMeta(jediShovelMeta);

        /* ADDING ENCHANTMENTS */
        addEnchants(basicSword, basicPick, basicAxe, basicShovel, 1);
        addEnchants(senatorSword, senatorPick, senatorAxe, senatorShovel, 3);
        addEnchants(padawanSword, padawanPick, padawanAxe, padawanShovel, 5);
        addEnchants(jediSword, jediPick, jediAxe, jediShovel, 7);

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
            playerRank.put(player, Ranks.PEASANT);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 500 && blocksBrokenPlayer.get(player) < 3500) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.SENATOR);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 3500 && blocksBrokenPlayer.get(player) < 10000) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.PADAWAN);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        } else if (blocksBrokenPlayer.get(player) >= 10000) {
            playerRank.remove(player);
            playerRank.put(player, Ranks.JEDI);

            player.getScoreboard().getTeam("player_rank").setSuffix(ChatColor.GOLD + playerRank.get(player).getRankString());
        }

        if (oldRank != playerRank.get(player)) {
            if (playerRank.get(player).equals(Ranks.PEASANT)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "basic");
            } else if (playerRank.get(player).equals(Ranks.SENATOR)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "senatorGAMER");
            } else if (playerRank.get(player).equals(Ranks.PADAWAN)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "PadawanPIRO");
            } else if (playerRank.get(player).equals(Ranks.JEDI)) {
                playerData.set(new NamespacedKey(JavaPlugin.getPlugin(MiningAway.class), "playerRank"), PersistentDataType.STRING, "Jedi");
            }
        }
    }
}
