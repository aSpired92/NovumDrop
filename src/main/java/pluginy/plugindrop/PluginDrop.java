package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Material.*;

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
            menuitem = new ItemStack(events.droppingitems[i],1);
            ItemMeta mitemmeta = menuitem.getItemMeta();
            if(events.enabled[i])
                mitemmeta.setDisplayName("ON");
            else
                mitemmeta.setDisplayName("OFF");
            menuitem.setItemMeta(mitemmeta);
            commands.dropmenu.setItem(i,menuitem);
            events.dropsy.add(new ItemStack(events.droppingitems[i],1));
        }

        System.out.println("Uruchomienie NovumDrop przebieglo pomyslnie");
    }



    @Override
    public void onDisable() {
        System.out.println("Wylaczono NovumDrop");
    }
}
