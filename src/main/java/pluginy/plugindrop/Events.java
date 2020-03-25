package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.event.inventory.InventoryAction.*;
import static org.bukkit.event.inventory.InventoryType.*;
import static org.apache.commons.lang.ArrayUtils.contains;
import static org.apache.commons.lang.ArrayUtils.indexOf;
import static org.bukkit.Material.*;


public class Events implements Listener {


    ArrayList<Permission> permisje = new ArrayList<Permission>();




    public int[] bonuses = {
            2,
            4,
            5
            };

    public int[] slots = {
            0,
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            32
    };

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
            true, //Diamond
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

    float[][] chances = {
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
        Player gracz = event.getPlayer();
        Block cube = event.getBlock();
        event.setDropItems(false);
        int ranga = 0;
        for(int i = 0; i < permisje.size(); i++)
        {
            if (gracz.hasPermission(permisje.get(i)))
                ranga = i;
        }


        if(contains(droppingBlocks,cube.getType()))
        {

            int toollvl = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            for(int i = 0; i < dropsy.size(); i++)
            {
                int ranchance = new Random().nextInt(10000);
                float szansa = chances[ranga][i];
                if (toollvl > 0) szansa += bonuses[toollvl-1]*100;
                if (szansa >= ranchance && enabled[i] == true)
                    gracz.getInventory().addItem(dropsy.get(i));
            }

        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getView().getTitle() == "NovumDrop")
        {
            Inventory inventory = event.getClickedInventory(); // The inventory that was clicked in
            ItemStack item = event.getCurrentItem(); // The item that was clicked
            int slot = event.getRawSlot();
            Player gracz = (Player) event.getWhoClicked(); // The player that clicked the item
            gracz.sendMessage(event.getAction().toString() + " " + event.getClickedInventory().getType().toString() + " " + event.getClickedInventory().toString() + " " + event.getView().getTitle() + " " + slot);

            if (slot >= inventory.getSize() + 36 || slot < 0 || item == null || item.getType() == AIR || event.getAction() == MOVE_TO_OTHER_INVENTORY)
            {
                event.setCancelled(true);
                return;
            }





            if(event.getClickedInventory().getType() != PLAYER)event.setCancelled(true);

            if (event.isLeftClick() && event.getClickedInventory().getType() != PLAYER)
            {
                

                if (contains(slots,slot))
                {
                    int index = indexOf(droppingitems,item.getType());

                    inventory.remove(item);
                    ItemStack menuitem = new ItemStack(droppingitems[index],1);

                    if (enabled[index])
                    {
                        gracz.sendMessage(PluginDrop.messageData.get("WylaczenieDropu") + " " + item.getItemMeta().getDisplayName());
                        enabled[index] = false;
                        SetItemLore(menuitem,PluginDrop.infoDrop(index,false));
                    }
                    else
                    {
                        gracz.sendMessage(PluginDrop.messageData.get("WlaczenieDropu") + " " + item.getItemMeta().getDisplayName());
                        enabled[index] = true;
                        SetItemLore(menuitem,PluginDrop.infoDrop(index,true));
                    }
                    inventory.setItem(slot,menuitem);
                }
                else if (slot == 34)
                {
                    for (int i = 0; i < enabled.length; i++)
                    {
                        enabled[i] = true;
                        SetItemLore(inventory.getItem(slots[i]),PluginDrop.infoDrop(i,true));
                    }
                    gracz.sendMessage(PluginDrop.messageData.get("WlaczWszystko"));
                }
                else if (slot == 35)
                {
                    for (int i = 0; i < slots.length; i++)
                    {
                        enabled[i] = false;
                        SetItemLore(inventory.getItem(slots[i]),PluginDrop.infoDrop(i,false));
                    }
                    gracz.sendMessage(PluginDrop.messageData.get("WylaczWszystko"));
                }
            }
        }

    }

    public static void AddToMenu(Material material, int menuslot, Inventory menu, List<String> opis)
    {
        ItemStack item = new ItemStack(material,1);
        ItemMeta mitemmeta = item.getItemMeta();
        mitemmeta.setLore(opis);
        item.setItemMeta(mitemmeta);
        menu.setItem(menuslot,item);
    }

    public static void SetItemName(ItemStack item, String opis)
    {
        ItemMeta mitemmeta = item.getItemMeta();
        mitemmeta.setDisplayName(opis);
        item.setItemMeta(mitemmeta);
    }

    public static void SetItemLore(ItemStack item, List<String> opis)
    {
        ItemMeta mitemmeta = item.getItemMeta();
        mitemmeta.setLore(opis);
        item.setItemMeta(mitemmeta);
    }
}
