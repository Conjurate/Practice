package conj.practice;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PracticeListener implements Listener {

	@EventHandler
	public void changeBlock(PlayerInteractEvent e){
		Block b = e.getClickedBlock();
		if (b.getType() == Material.GOLD_BLOCK) {
			b.setType(Material.GLOWSTONE);
		}
		
	}
	
	@EventHandler
	public void killMobSound(EntityDamageByEntityEvent event) {
		Entity defender = event.getEntity();
		Entity attacker = event.getDamager();
		if (defender.getType().isAlive() && attacker.getType() == EntityType.PLAYER) {
			LivingEntity living = (LivingEntity) defender;
			Player player = (Player) attacker;
			if (living.getHealth()-event.getFinalDamage() <= 0) {
				player.getWorld().playSound(player, Sound.ENTITY_VILLAGER_YES, 10, 1);
			}
		}
	}
	
}
