package me.wouter.vehiclelocker;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.wouter.vehiclelocker.commands.VehicleLockCMD;
import me.wouter.vehiclelocker.data.DataFile;
import me.wouter.vehiclelocker.listeners.CraftmotoPluginListener;
import me.wouter.vehiclelocker.listeners.JoinListener;
import me.wouter.vehiclelocker.listeners.VehiclesPluginListener;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	private static Plugin pl;
	boolean vehiclesPlugin = false;
	boolean craftmotoPlugin = false;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		
		
		if (Bukkit.getPluginManager().isPluginEnabled("Craftmoto")) {
			getLogger().severe(color("&3CraftMoto &bv" + Bukkit.getPluginManager().getPlugin("Craftmoto").getDescription().getVersion() + " &3found! Hooking.."));
			craftmotoPlugin = true;
		
			Bukkit.getPluginManager().registerEvents(new CraftmotoPluginListener(), this);
		}
		if (Bukkit.getPluginManager().isPluginEnabled("Vehicles")) {
			getLogger().severe(color("&3Vehicles &bv" + Bukkit.getPluginManager().getPlugin("Vehicles").getDescription().getVersion() + " &3found! Hooking.."));
			vehiclesPlugin = true;
			
			Bukkit.getPluginManager().registerEvents(new VehiclesPluginListener(), this);
		}
		
		
		if (!vehiclesPlugin && !craftmotoPlugin) {
			getLogger().severe(color("&4Either Vehicles or Craftmoto &cis required for VehicleLocker to function."));
			getLogger().severe(color("You can purchase Vehicles here: https://www.spigotmc.org/resources/Vehicles.12446/"));
			getLogger().severe(color("You can purchase Craftmoto here: https://www.spigotmc.org/resources/Craftmoto.44111/"));
			Bukkit.getPluginManager().disablePlugin(pl);
			return;
		}
		
		getCommand("lockvehicle").setExecutor(new VehicleLockCMD());
		
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		
		
		getConfig().addDefault("Command.Locked", "&3Your vehicle is now &blocked&3.");
		getConfig().addDefault("Command.Unlocked", "&3Your vehicle is now &bunlocked&3.");
		
		getConfig().addDefault("VehicleIsLocked", "&b<Player> &3has locked his Vehicle, therefore you &bcan't&3 get in.");
		getConfig().addDefault("VehicleIsLockedAlert", "&3Your Vehicle is &blocked&3, therefore &bnobody &3can get in.\n&3To unlock your Vehicle, type &b/lockvehicle");
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		
		pl = this;
		
		DataFile.getInstance().setup(this);
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				DataFile.getInstance().saveData();
			}
		}, 20 * 60 * 15, 20 * 60 * 15);
	}

	public void onDisable() {
		DataFile.getInstance().saveData();
	}

	public static String cc(String path) {
		return color(pl.getConfig().getString(path));
	}
	
	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
