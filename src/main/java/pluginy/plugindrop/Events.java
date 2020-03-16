package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

import static org.apache.commons.lang.ArrayUtils.contains;
import static org.bukkit.Material.*;


public class Events implements Listener {

    Commands commands = new Commands();


    public Material[] droppingitems = {
            DIAMOND,
            GOLD_INGOT,
            IRON_INGOT,
            EMERALD,
            COAL,
            REDSTONE,
            LAPIS_LAZULI,
            OBSIDIAN,
            ENDER_PEARL,
            TNT,
            GUNPOWDER,
            SLIME_BALL,
            COBBLESTONE
    };

    Material[] droppingBlocks = {
            STONE,       //1
            IRON_ORE,    //2
            COAL_ORE,    //3
            DIAMOND_ORE, //4
            EMERALD_ORE, //5
            GOLD_ORE,    //6
            LAPIS_ORE,   //7
            ANDESITE,    //8
            DIORITE,     //9
            GRANITE      //10
    };

    boolean[] enabled = {
            false, //Diamond
            true, //Gold
            true, //Iron
            true, //Emerald
            true, //Coal
            true, //Redstone
            true, //Lapis
            true, //Obsidian
            true, //Ender Pearl
            true, //TNT
            true, //Gun Powder
            true, //Slimeball
            true  //Cobblestone
    };

    ArrayList<ItemStack> dropsy = new ArrayList<ItemStack>();

    int[][] chances = {
            {120, 50,150,100,300,250,140,100, 5, 1, 20,10,10000}, //Gracz
            {150, 80,190,130,310,300,180,200,10, 3, 40,15,10000}, //VIP
            {200,100,230,150,320,310,200,300,15, 5, 60,20,10000}, //sVIP
            {250,150,280,190,330,320,220,400,30, 8, 80,25,10000}, //eVIP
            {200,100,230,150,320,310,200,300,15, 5, 60,20,10000}, //Youtuber
            {300,200,320,220,340,350,250,500,50,10,100,30,10000}, //Sponsor
    };

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Random rand = new Random();
        Player gracz = (Player) event.getPlayer();
        Block cube = event.getBlock();
        event.setDropItems(false);
        if(contains(droppingBlocks,cube.getType()))
        {
            for(int i = 0; i < dropsy.size(); i++)
            {
                int ranchance = rand.nextInt(10000);
                if (chances[1][i] >= ranchance && enabled[i] == true)
                    gracz.getInventory().addItem(dropsy.get(i));
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player gracz = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack item = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in
        int slot = event.getSlot();
        gracz.sendMessage(String.valueOf(slot));
        if (inventory.getSize() == 18)
        {
            for (int i = 0; i < enabled.length; i++)
            {
                if (item.getType() == droppingitems[i])
                {
                    event.setCancelled(true); // Make it so the dirt is back in its original spot
                    commands.dropmenu.remove(item);
                    ItemStack menuitem = new ItemStack(droppingitems[i],1);
                    ItemMeta mitemmeta = menuitem.getItemMeta();
                    if (enabled[i])
                    {
                        gracz.sendMessage("Wyłączyłeś drop " + droppingitems[i].toString());
                        enabled[i] = false;
                        mitemmeta.setDisplayName("OFF");
                    }
                    else if (!enabled[i])
                    {
                        gracz.sendMessage("Włączyłeś drop " + droppingitems[i].toString());
                        enabled[i] = true;
                        mitemmeta.setDisplayName("ON");
                    }
                    menuitem.setItemMeta(mitemmeta);
                    //commands.dropmenu.setItem(i,menuitem);
                    break;
                }
            }
        }
    }
}
