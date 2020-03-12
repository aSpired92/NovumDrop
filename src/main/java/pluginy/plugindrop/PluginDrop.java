package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;


import java.util.ArrayList;
import java.util.Random;

import static org.apache.commons.lang.ArrayUtils.contains;
import static org.bukkit.Material.*;

public final class PluginDrop extends JavaPlugin implements Listener {

    ArrayList<ItemStack> dropsy = new ArrayList<ItemStack>();

    Material[] droppingBlocks = {
            STONE, //1
            IRON_ORE, //2
            COAL_ORE, //3
            DIAMOND_ORE, //4
            EMERALD_ORE, //5
            GOLD_ORE, //6
            LAPIS_ORE, //7
            ANDESITE, //8
            DIORITE, //9
            GRANITE //10
    };

    int[][] chances = {
            {120,50,150,100,300,250,140,100, 5,1,20,10}, //Gracz
            {150,80,190,130,310,300,180,200,10,3,40,15}, //VIP
            {},
            {},
            {},
            {},

    };
    @Override
    public void onEnable()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        dropsy.add(new ItemStack(DIAMOND,1));
        dropsy.add(new ItemStack(GOLD_INGOT,1));
        dropsy.add(new ItemStack(IRON_INGOT,1));
        dropsy.add(new ItemStack(EMERALD,1));
        dropsy.add(new ItemStack(COAL,1));
        dropsy.add(new ItemStack(REDSTONE,1));
        dropsy.add(new ItemStack(LAPIS_LAZULI,1));
        dropsy.add(new ItemStack(OBSIDIAN,1));
        dropsy.add(new ItemStack(ENDER_PEARL,1));
        dropsy.add(new ItemStack(TNT,1));





        System.out.println("Uruchomienie NovumDrop przebieglo pomyslnie");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Random rand = new Random();
        Player gracz = event.getPlayer();
        Block cube = event.getBlock();
        event.setDropItems(false);
        if(contains(droppingBlocks,cube.getType()))
        {
            gracz.getInventory().addItem(dropsy.get(rand.nextInt(dropsy.size())));

        }
    }
}
