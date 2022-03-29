package conj.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PracticeListener implements Listener {

	private static List<UUID> grassToStone = new ArrayList<>();
	
	@EventHandler
	public void interactBlock(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		if (e.getHand() == EquipmentSlot.HAND) {
			if (b.getType() == Material.GOLD_BLOCK) {
				b.setType(Material.GLOWSTONE);
			} else if(b.getType() == Material.DIAMOND_BLOCK && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				UUID uuid = p.getUniqueId();
				if (grassToStone.contains(uuid)) {
					grassToStone.remove(uuid);
					p.sendMessage(ChatColor.RED+"No longer turning grass to stone");
				} else {
					grassToStone.add(uuid);
					p.sendMessage(ChatColor.GREEN+"Turning grass to stone");
				}
			}
		}
	}
	
	@EventHandler
	public void turnGrassToStoneEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block b = p.getLocation().getBlock().getRelative(0, -1, 0);
		Location from = e.getFrom();
		Location to = e.getTo();
		if (grassToStone.contains(p.getUniqueId())) {
			if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
				if (b.getType() == Material.GRASS_BLOCK) {
					b.setType(Material.STONE);
				}
			}
		}
	}

	@EventHandler
	public void killMobSound(EntityDamageByEntityEvent event) {
		Entity defender = event.getEntity();
		Entity attacker = event.getDamager();
		if (defender.getType().isAlive() && attacker.getType() == EntityType.PLAYER) {
			LivingEntity living = (LivingEntity) defender;
			Player player = (Player) attacker;
			if (living.getHealth() - event.getFinalDamage() <= 0) {
				player.getWorld().playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 1);
			}
		}
	}
	
	@EventHandler
	public void interactBlockSound(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		Player player = e.getPlayer();
		if (e.getHand() == EquipmentSlot.HAND) {
			if (b.getType() == Material.GRASS_BLOCK && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				player.getWorld().playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 1);
			}
		}
	}

}
