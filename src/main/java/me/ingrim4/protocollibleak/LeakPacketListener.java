package me.ingrim4.protocollibleak;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketPostListener;

public class LeakPacketListener extends PacketAdapter {

	public LeakPacketListener(Plugin plugin) {
		super(plugin, PacketType.Play.Server.MAP_CHUNK, PacketType.Play.Server.UNLOAD_CHUNK);
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		event.getNetworkMarker().addPostListener(new PacketPostListener() {
			
			@Override
			public void onPostEvent(PacketEvent e) {
				event.getPacket().equals(null);
			}
			
			@Override
			public Plugin getPlugin() {
				return LeakPacketListener.this.getPlugin();
			}
		});
	}
}
