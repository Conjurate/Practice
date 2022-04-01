package conj.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SpleefListener implements Listener {
	
	private static List<UUID> spleefQueue = new ArrayList<>();
	private static List<Block> spleefBlocks = new ArrayList<>();
	private static HashMap<UUID, Location> spleefHome = new HashMap<>();
	
	private static int playersReq = 1;

	@EventHandler
	public void SpleefQueue(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		Player player = e.getPlayer();
		if (b != null && b.getType() == Material.ACACIA_WALL_SIGN && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Sign sign = (Sign) b.getState();
			String string = sign.getLine(0);
			if (string.equalsIgnoreCase("[Spleef]")) {
				UUID uuid = player.getUniqueId();
				if (spleefQueue.contains(uuid)) {
					spleefQueue.remove(uuid);
					player.sendMessage(ChatColor.RED + "You have been removed from the queue");
				} else {
					spleefQueue.add(uuid);
					player.sendMessage(ChatColor.GREEN + "You have joined the queue "+ChatColor.GRAY+"("+ChatColor.GOLD+spleefQueue.size()+ChatColor.GRAY+"/"+ChatColor.GOLD+playersReq+ChatColor.GRAY+")");
					if (spleefQueue.size() >= playersReq) {
						startGame();
					}
				}
			}
		}

	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		spleefQueue.remove(player.getUniqueId());
		if (spleefQueue.size() <= 1) {
			endGame();
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player player = e.getPlayer();
		if (spleefQueue.contains(player.getUniqueId())) {
			spleefBlocks.add(b);
			e.setDropItems(false);
		}

	}
	
	@EventHandler
	public void stopItemDrop(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		if (spleefQueue.contains(p.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void inGame(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location to = e.getTo();
		if (spleefQueue.contains(player.getUniqueId())) {
			if (to.getY()<=65) {
				endGame();
			}
		}
	}
	
	@EventHandler
	public void stopDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e.getType() == EntityType.PLAYER) {
			Player p = (Player) e;
			if (spleefQueue.contains(p.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
	
	public void startGame() {
		for (UUID uuid : spleefQueue) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				spleefHome.put(uuid, player.getLocation());
				player.teleport(new Location(player.getWorld(), -316.5, 85, 157.5));
				player.getInventory().addItem(new ItemStack(Material.NETHERITE_SHOVEL));
			}
		}
	}

	public void endGame() {
		for (Block b : spleefBlocks) {
			b.setType(Material.SNOW_BLOCK);
		}
		for (UUID uuid : spleefQueue) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				Location loc = spleefHome.get(uuid);
				player.setFallDistance(0);
				player.teleport(loc);
				player.getInventory().remove(Material.NETHERITE_SHOVEL);
			}
		}
		spleefBlocks.clear();
		spleefQueue.clear();
		spleefHome.clear();
	}

}
