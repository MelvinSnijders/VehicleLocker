package me.wouter.vehiclelocker.data;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DataFile {

	static DataFile instance = new DataFile();

	public static DataFile getInstance() {
		return instance;
	}

	FileConfiguration PlayerData;
	File pdfile;
	Plugin pl;

	public void setup(Plugin pl) {
		pdfile = new File(pl.getDataFolder(), "Data/Spelers.yml");
		PlayerData = YamlConfiguration.loadConfiguration(pdfile);
		this.pl = pl;
	}

	public FileConfiguration getData() {
		return PlayerData;
	}

	public void reloadData() {
		PlayerData = YamlConfiguration.loadConfiguration(pdfile);
	}

	public void saveData() {
		try {
			if (PlayerData != null && pdfile != null) {
				PlayerData.save(pdfile);
			} else {
				setup(pl);
				PlayerData.save(pdfile);
			}
		} catch (Exception e) {
			pl.getLogger().info(ChatColor.RED + "Het opslaan van Data.yml is niet gelukt!");
			e.printStackTrace();
		}
	}
}
