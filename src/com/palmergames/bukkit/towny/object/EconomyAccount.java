package com.palmergames.bukkit.towny.object;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.economy.Account;
import com.palmergames.bukkit.towny.object.economy.TownyServerAccount;
import com.palmergames.bukkit.util.BukkitTools;

import org.bukkit.World;

/**
 * Economy object which provides an interface with the Economy Handler.
 *
 * @author ElgarL
 * @author Shade
 * @author Suneet Tipirneni (Siris)
 */
public class EconomyAccount extends Account {
	public static final TownyServerAccount SERVER_ACCOUNT = new TownyServerAccount();
	private World world;
	
	protected EconomyAccount(String name, World world) {
		super(name);
		this.world = world;
	}

	@Override
	protected boolean addMoney(double amount) {
		return TownyEconomyHandler.add(getName(), amount, getWorld());
	}

	@Override
	protected boolean subtractMoney(double amount) {
		return TownyEconomyHandler.subtract(getName(), amount, getWorld());
	}

	protected EconomyAccount(String name) {
		super(name);
	}

	public World getWorld() {
		World world = BukkitTools.getWorlds().get(0);
		if (TownySettings.perWorldCurrencyEnabled()) {
			if (BukkitTools.isOnline(getName()))
				return BukkitTools.getPlayerExact(getName()).getWorld();
			Resident res = TownyAPI.getInstance().getResident(getName());
			return (res != null && res.hasTown()) ? res.getTownOrNull().getWorld() : world;

		}
		return world;
	}
}
