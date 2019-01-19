package me.wouter.vehiclelocker.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

import me.wouter.vehiclelocker.Main;
import me.wouter.vehiclelocker.data.DataFile;

public class VehiclesPluginListener implements Listener {

	// I stole this from the latest Vehicles jar.
	List<String> vehiclePartsNames = Arrays.asList("BikePart", "Bike", "Car", "CarPart", "Train", "TrainPart", "Raft",
			"RaftPart", "Plane", "PlanePart", "Para", "ParaPart", "Heli", "HeliPart", "Tank", "TankPart", "Subma",
			"SubmaPart", "Broom", "BroomPart", "HBike", "HBikePart", "SecondSeat");

	@EventHandler
	public void onEntityMount(EntityMountEvent e) {
		if (e.getEntity() instanceof Player && e.getMount() instanceof ArmorStand) {
			Player p = (Player) e.getEntity();
			ArmorStand as = (ArmorStand) e.getMount();
			boolean isVehicle = false;
			for (String part : vehiclePartsNames) {
				if (as.getName().contains(part)) {
					isVehicle = true;
					break;
				}
			}
			if (!isVehicle) {
				return;
			}
			if (as.getName().contains("SecondSeat")) {
				String vehicleId = as.getName().split(";")[1];
				ArmorStand frontSeat = null;
				Player frontSeatPassenger = null;
				for (Entity nearby : as.getNearbyEntities(3, 1, 3)) {
					if (nearby instanceof ArmorStand) {
						if (nearby.getPassenger() != null && nearby.getPassenger() instanceof Player) {
							String nearbyVehicleId = nearby.getName().split(";")[1];
							if (nearbyVehicleId.equals(vehicleId)) {
								frontSeat = (ArmorStand) nearby;
								frontSeatPassenger = (Player) nearby.getPassenger();
								break;
							}
						}
					}
				}
				if (frontSeat == null) {
					return;
				}

				if (DataFile.getInstance().getData().getBoolean(frontSeatPassenger.getUniqueId() + ".Locked")) {
					p.sendMessage(Main.cc("VehicleIsLocked").replaceAll("<Player>", frontSeatPassenger.getName()));
					e.setCancelled(true);
					p.teleport(p.getLocation());
				}
			} else {
				if (DataFile.getInstance().getData().getBoolean(p.getUniqueId() + ".Locked")) {
					p.sendMessage(Main.cc("VehicleIsLockedAlert"));
				}
			}
		}
	}
}
