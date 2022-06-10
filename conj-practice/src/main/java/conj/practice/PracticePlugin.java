package conj.practice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.conjurate.sentry.SentryHandler;
import com.github.conjurate.sentry.SentryRegistry;

public class PracticePlugin extends JavaPlugin {

	public static boolean debug = true;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PracticeListener(),this);
		Bukkit.getPluginManager().registerEvents(new SpleefListener(), this);
		SentryRegistry.registerGUI(new PracticeGUI());
		SentryRegistry.registerGUITransfer(SentryHandler.GUI_SENTRY_MANAGE, 45, "Practice");
		SentryRegistry.registerGUIMod(SentryHandler.GUI_SENTRY_MANAGE, (gui, info, data) -> {
			gui.setItem(gui.getBukkitSize()-9, Material.HONEY_BLOCK, "Practice");
		});
	}
	
	public void onDisable() {
	}
	
	public static void debug(Object...msg) {
		if (debug) {
			for(Object o : msg) {
				Bukkit.getLogger().info(o.toString());
			}
		}
	}
	
}
