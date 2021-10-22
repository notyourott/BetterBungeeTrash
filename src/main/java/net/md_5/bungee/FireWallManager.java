package net.md_5.bungee;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FireWallManager {

	static String betterbungee = "http://betterbungeeapi.skydb.de";

	public static String uuid = "";

	public static String key = "";

	public static String session = "";

	public static boolean syncing = false;

	public static Set<String> blacklist = ConcurrentHashMap.newKeySet();

	public static Set<String> whitelist = ConcurrentHashMap.newKeySet();

	public static Runtime run = Runtime.getRuntime();

	public static ThreadPoolExecutor threads = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("java -jar "
					+ new File(FireWallManager.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName()
					+ " <uuid> <key>");
			return;
		}

		uuid = args[0];

		key = args[1];

		try {
			run.exec("sudo apt install ipset iptables sudo -y");

			sleep();
			
			run.exec("sudo ipset destroy whitelist");
			sleep(200);
			run.exec("sudo ipset flush whitelist");
			sleep(200);
			run.exec("sudo ipset create whitelist nethash");
			sleep(200);

			run.exec("sudo ipset destroy blacklist");
			sleep(200);
			run.exec("sudo ipset flush blacklist");
			sleep(200);
			run.exec("sudo ipset create blacklist nethash");

			sleep(200);
			run.exec("sudo iptables -A INPUT -m set --match-set whitelist src -j ACCEPT");
			sleep(200);
			run.exec("sudo iptables -A FORWARD -m set --match-set whitelist src -j ACCEPT");
			
			sleep(200);
			run.exec("sudo iptables -A INPUT -m set --match-set blacklist src -j DROP");
			sleep(200);
			run.exec("sudo iptables -A FORWARD -m set --match-set blacklist src -j DROP");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		run.addShutdownHook(new Thread(() -> {
			try {
				run.exec("sudo iptables -D INPUT -m set --match-set blacklist src -j DROP");
				sleep(200);
				run.exec("sudo iptables -D FORWARD -m set --match-set blacklist src -j DROP");
				sleep(200);
				run.exec("sudo iptables -D INPUT -m set --match-set whitelist src -j DROP");
				sleep(200);
				run.exec("sudo iptables -D FORWARD -m set --match-set whitelist src -j DROP");
				sleep(200);
				run.exec("sudo ipset destroy blacklist");
				sleep(200);
				run.exec("sudo ipset destroy whitelist");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

	}


	public static void sleep() {
		sleep(7500);
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
