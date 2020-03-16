package pluginy.plugindrop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Commands implements CommandExecutor {


    public Inventory dropmenu = Bukkit.createInventory(null, 18, "Ustawienia Dropu");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player gracz = (Player) sender;
            gracz.getServer().broadcastMessage("DROOOOOOOP");
            gracz.openInventory(dropmenu);
            return true;
        }
        return false;
    }
}
