package me.wouter.vehiclelocker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.wouter.vehiclelocker.data.DataFile;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		DataFile.getInstance().getData().addDefault(e.getPlayer().getUniqueId() + ".Locked", false);
	}
}
