package conj.practice;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.conjurate.sentry.SentryHandler;
import com.github.conjurate.sentry.entity.SentryInfo;
import com.github.conjurate.sentry.gui.SentryGUI;

import conj.api.gui.GUI;
import conj.api.gui.GUIClickEvent;
import conj.api.gui.GUIClickLocation;

public class PracticeGUI extends SentryGUI {

	@Override
	protected void buildGUI(GUI gui, Player player, SentryInfo info, Object... data) {
		gui.setItem(0, Material.COMPASS, ChatColor.RED+"Back");
		
	}

	@Override
	public String getId() {	
		return "Practice";
	}

	@Override
	public int getSize(Player arg0, SentryInfo arg1, Object... arg2) {
		return 1;
	}

	@Override
	public String getTitle(Player arg0, SentryInfo arg1, Object... arg2) {
		return "Practice1";
	}

	@Override
	protected void onClick(GUIClickEvent event, SentryInfo info) {
		if(event.getLocation() != GUIClickLocation.GUI) return;
		Player p = event.getPlayer();
		int slot = event.getSlot();
		if (slot == 0) SentryHandler.openGUI(p, SentryHandler.GUI_SENTRY_MANAGE, info);
	}

	
	

	

}
