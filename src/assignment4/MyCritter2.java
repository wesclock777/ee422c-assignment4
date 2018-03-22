package assignment4;
/* CRITTERS MyCritter2.java
 * EE422C Project 4 submission by
 * Wesley Klock
 * wtk332
 * 15455
 * Slip days used: <0>
 * Fall 2016
 */

import assignment4.Critter;

/**
 * MyCritter2 is an aggressive bug who loves to fight. This Critter will
 * run around and never run away. However, after 5 fights this Critter will get PTSD and try
 * and run endlessly, resulting in death.
 * @author Wesley Klock
 *
 */
public class MyCritter2 extends Critter{
	// fighting bug
	int willtofight = 5;
	public String toString() { return "2"; }
	
	public boolean fight(String name) {
		// when the fighting bugs loses his will to fight he kills himself
		if(willtofight==0) {
			while(getEnergy()>0) {
				run(Critter.getRandomInt(8));
			}
		}
		// because of honor refuses to fight algae
		if(name=="@") {
			return false;
		}
		willtofight--;
		return true; 
	}
	
	@Override
	public void doTimeStep() {
		// the fighting bug has no time for walking!
		this.run(Critter.getRandomInt(8));
	}
}
