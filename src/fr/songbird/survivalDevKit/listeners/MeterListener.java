package fr.songbird.survivalDevKit.listeners;

import java.util.EventListener;

public interface MeterListener extends EventListener{

	/**
	 * Ecoute si une seconde est passee
	 */
	void whenTick(int tick);
	void whenTick(long tick);
	
}
