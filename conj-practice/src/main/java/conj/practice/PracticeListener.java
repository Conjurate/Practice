package conj.practice;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PracticeListener implements Listener {

	@EventHandler
	public void changeBlock(PlayerInteractEvent e){
		Block b = e.getClickedBlock();
		if (b.getType() == Material.GOLD_BLOCK) {
			b.setType(Material.GLOWSTONE);
		}
		
	}
	
}
