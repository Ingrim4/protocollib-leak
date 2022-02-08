package me.ingrim4.protocollibleak;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.AsynchronousManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLibLeak extends JavaPlugin implements Runnable {

	private static final boolean ASYNC = true;

	private boolean direction = false;

	@Override
	public void onEnable() {
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		AsynchronousManager asynchronousManager = protocolManager.getAsynchronousManager();

		if (ASYNC) {
			asynchronousManager.registerAsyncHandler(new LeakPacketListener(this)).start();
		} else {
			protocolManager.addPacketListener(new LeakPacketListener(this));
		}

		// teleport all players to trigger chunk packet spam
		Bukkit.getScheduler().runTaskTimer(this, this, 4L, 4L);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Location spawn = player.getWorld().getSpawnLocation();
			Location target = spawn.add(direction ? 250 : -250, 150, 0);
			player.teleport(target);
		}

		direction = !direction;
	}
}
