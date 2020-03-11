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

public final class PluginDrop extends JavaPlugin implements Listener {

    ArrayList<ItemStack> dropsy = new ArrayList<ItemStack>();

    @Override
    public void onEnable()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        dropsy.add(new ItemStack(Material.SPONGE,1));
        dropsy.add(new ItemStack(Material.GOLD_INGOT,1));


        System.out.println("TEST: Jebłeś kostke?");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {

        ItemStack Stak = dropsy.get(0);
        Block cube = event.getBlock();
        event.setDropItems(false);
        if(cube.getType() == Material.STONE)
        {

            System.out.println("Jebłeś kostke");
            cube.getWorld().dropItemNaturally(cube.getLocation(), Stak);
        }
    }
}
