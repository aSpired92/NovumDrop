package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Material.*;
import static org.bukkit.enchantments.Enchantment.*;
import static org.bukkit.inventory.ItemFlag.*;

public final class PluginDrop extends JavaPlugin {




    @Override
    public void onEnable()
    {
        Events events = new Events();
        Commands commands = new Commands();
        ItemStack menuitem;
        getCommand("drop").setExecutor(commands);
        getServer().getPluginManager().registerEvents(events, this);

        for(int i = 0; i < events.droppingitems.length; i++)
        {
            Events.AddToMenu(events.droppingitems[i],events.slots[i],commands.dropmenu,"ON");
            events.dropsy.add(new ItemStack(events.droppingitems[i],1));
        }


        ItemStack kilof = new ItemStack(DIAMOND_PICKAXE,1);
        kilof.addEnchantment(LOOT_BONUS_BLOCKS,1);
        ItemMeta pickmeta = kilof.getItemMeta();
        pickmeta.setDisplayName("Opis dropu bla bla bla");
        pickmeta.addItemFlags(HIDE_ATTRIBUTES,HIDE_ENCHANTS);
        kilof.setItemMeta(pickmeta);
        commands.dropmenu.setItem(27,kilof);


        Events.AddToMenu(LIME_CONCRETE,34,commands.dropmenu,"ALL ON");
        Events.AddToMenu(RED_CONCRETE,35,commands.dropmenu,"ALL OFF");

        System.out.println("Uruchomienie NovumDrop przebieglo pomyslnie");
    }





    @Override
    public void onDisable() {
        System.out.println("Wylaczono NovumDrop");
    }
}
