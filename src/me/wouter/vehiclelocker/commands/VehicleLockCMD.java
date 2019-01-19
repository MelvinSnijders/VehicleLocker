package me.wouter.vehiclelocker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wouter.vehiclelocker.Main;
import me.wouter.vehiclelocker.data.DataFile;

public class VehicleLockCMD implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command is for players only.");
			return true;
		}
		Player p = (Player) sender;
		boolean isLocked = DataFile.getInstance().getData().getBoolean(p.getUniqueId() + ".Locked");
		if (!isLocked) {
			DataFile.getInstance().getData().set(p.getUniqueId() + ".Locked", true);
			p.sendMessage(Main.cc("Command.Locked"));
		}else {
			DataFile.getInstance().getData().set(p.getUniqueId() + ".Locked", null);
			p.sendMessage(Main.cc("Command.Unlocked"));
		}
		return true;
	}
}
