package me.wouter.vehiclelocker.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.wouter.vehiclelocker.Main;
import me.wouter.vehiclelocker.data.DataFile;
import net.jordan.vehicles.api.events.PlayerChangeSeatEvent;
import net.jordan.vehicles.nms.CustomVehicle;

public class CraftmotoPluginListener implements Listener {

	@EventHandler
	public void onSeatChange(PlayerChangeSeatEvent e) {
		CustomVehicle vehicle = e.getTo();
		Player player = e.getPlayer();
		if (vehicle.getOwnerUUID() != player.getUniqueId()) {
			if (Bukkit.getPlayer(vehicle.getOwnerUUID()) != null) {
				Player owner = Bukkit.getPlayer(vehicle.getOwnerUUID());
				if (DataFile.getInstance().getData().getBoolean(owner.getUniqueId() + ".Locked")) {
					player.sendMessage(Main.cc("VehicleIsLocked").replaceAll("<Player>", owner.getName()));
					e.setCancelled(true);
				}
			}
		} else {
			if (DataFile.getInstance().getData().getBoolean(player.getUniqueId() + ".Locked")) {
				player.sendMessage(Main.cc("VehicleIsLockedAlert"));
			}
		}
	}

}
