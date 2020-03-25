package pluginy.plugindrop;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Material.*;
import static org.bukkit.enchantments.Enchantment.*;
import static org.bukkit.inventory.ItemFlag.*;

public final class PluginDrop extends JavaPlugin {


    private static Events events = new Events();
    Commands commands = new Commands();


    public static HashMap<String, String> messageData = new HashMap<String, String>();
    public File f = new File(getDataFolder()+File.separator+"messages.yml");

    private static String[] szanse = {
            "Szansa",
            "Szansa VIP",
            "Szansa sVIP",
            "Szansa eVIP",
            "Szansa YouTuber",
            "Szansa Sponsor"
    };



    @Override
    public void onEnable()
    {
        String[] permnames = {
                "Gracz",
                "VIP",
                "sVIP",
                "eVIP",
                "Youtuber",
                "Sponsor"
        };


        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        setMessage("WlaczenieDropu", "Włączyłeś drop");
        setMessage("WylaczenieDropu", "Wyłączyłeś drop");
        setMessage("WlaczWszystko", "Włączyłeś wszystko");
        setMessage("WylaczWszystko", "Wyłączyłeś wszystko");
        setMessage("PrzyciskWlaczWszystko", "Włączyłeś wszystko");
        setMessage("PrzyciskWylaczWszystko", "Wyłączyłeś wszystko");
        setMessage("KilofInfo", "Informacje na temat dropu");
        setMessage("DropInfo_KolorNazwyPrzedmiotu", "");
        setMessage("DropInfo_KolorRang", "");
        setMessage("DropInfo_KolorWartosci", "");
        setMessage("DropInfo_KolorStatusDropu", "");
        setMessage("DropInfo_Wlacz", "ON");
        setMessage("DropInfo_Wylacz", "OFF");

        loadMessages();



        getCommand("drop").setExecutor(commands);
        getServer().getPluginManager().registerEvents(events, this);

        for(int i = 0; i < events.droppingitems.length; i++)
        {
            Events.AddToMenu(events.droppingitems[i],events.slots[i],commands.dropmenu,infoDrop(i,true));
            events.dropsy.add(new ItemStack(events.droppingitems[i],1));
        }

        for (int i = 0; i<permnames.length;i++)
        {
            events.permisje.add(new Permission(permnames[i]));
        }




        ItemStack kilof = new ItemStack(DIAMOND_PICKAXE,1);
        kilof.addEnchantment(LOOT_BONUS_BLOCKS,1);
        ItemMeta pickmeta = kilof.getItemMeta();
        pickmeta.setDisplayName(messageData.get("KilofInfo"));
        pickmeta.addItemFlags(HIDE_ATTRIBUTES,HIDE_ENCHANTS);
        kilof.setItemMeta(pickmeta);
        commands.dropmenu.setItem(27,kilof);


        ItemStack on = new ItemStack(LIME_CONCRETE,1);
        ItemMeta onmeta = on.getItemMeta();
        onmeta.setDisplayName(messageData.get("PrzyciskWlaczWszystko"));
        on.setItemMeta(onmeta);
        commands.dropmenu.setItem(34,on);

        ItemStack off = new ItemStack(RED_CONCRETE,1);
        ItemMeta offmeta = off.getItemMeta();
        offmeta.setDisplayName(messageData.get("PrzyciskWylaczWszystko"));
        off.setItemMeta(offmeta);
        commands.dropmenu.setItem(35,off);

        System.out.println("[NovumDrop] Uruchomienie NovumDrop przebieglo pomyslnie. Jebac Jave");

    }

    private void setMessage(String name, String message)
    {
        File f = new File(getDataFolder()+File.separator+"messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if (!config.isSet(name))
        {
            config.set(name, message);
            try
            {
                config.save(f);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void loadMessages()
    {
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        for (String message : config.getConfigurationSection("").getKeys(false)) {
            messageData.put(message, config.getString(message));
        }
    }


    public static List<String> infoDrop(int itemnumber, boolean wlaczone)
    {
        ArrayList<String> dropInfo = new ArrayList<String>();
        if(events.slots[itemnumber] != 32)
        {
            for(int i = 0; i < szanse.length; i++)
            {
                float szansa = events.chances[i][itemnumber]/100;
                dropInfo.add(szanse[i] + ": " + szansa + "%");
            }
        }

        String status = "Status Dropu: ";
        if(wlaczone)
            dropInfo.add(status + messageData.get("DropInfo_Wlacz")) ;
        else
            dropInfo.add(status + messageData.get("DropInfo_Wylacz")) ;
        return dropInfo;
    }



    @Override
    public void onDisable() {
        System.out.println("[NovumDrop] Wylaczono NovumDrop");
    }
}
