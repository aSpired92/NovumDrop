package pluginy.plugindrop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {


    public static Inventory dropmenu = Bukkit.createInventory(null, 36, "NovumDrop");

    private final Plugin plugin;

    public Commands(Plugin plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player gracz = (Player) sender;
            gracz.openInventory(dropmenu);
            return true;
        }
        return false;
    }
}
