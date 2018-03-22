package assignment4;
/* CRITTERS MyCritter1.java
 * EE422C Project 4 submission by
 * Wesley Klock
 * wtk332
 * 15455
 * Slip days used: <0>
 * Fall 2016
 */
import assignment4.Critter;
import assignment4.Critter.TestCritter;

/**
 * MyCritter1 is a lazy Beetle that moves very slowly and doesn't want to fight anything.
 * It only runs when threatened by an encounter.
 * If the critter gets below 20 energy it feels really scared and runs away from algae.
 * @author Wesley Klock
 *
 */
public class MyCritter1 extends TestCritter{
	int slowcount = 0;
	public String toString() { return "1"; }
	
	public boolean fight(String name) {
		// refuses to fight anything when low energy or anything thats not algae
		if(getEnergy()<20||(name!="@")) {
			run(Critter.getRandomInt(8));
			return false;
		}
		return true; 
	}
	
	@Override
	public void doTimeStep() {
		// the lazy Beetle only moves every 5 turns
		if(slowcount==5) {
			walk(Critter.getRandomInt(8));
		}
		else {
			slowcount++;
		}
	}

}
