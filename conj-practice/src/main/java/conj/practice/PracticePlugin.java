package conj.practice;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PracticePlugin extends JavaPlugin {

	public static boolean debug = true;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PracticeListener(),this);
		Bukkit.getPluginManager().registerEvents(new SpleefListener(), this);
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
