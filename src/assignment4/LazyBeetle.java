package assignment4;

import assignment4.Critter;

public class LazyBeetle extends Critter{
	int slowcount = 0;
	public String toString() { return "B"; }
	
	public boolean fight(String name) {
		// refuses to fight anything when low energy or anything thats not algae
		if(getEnergy()<100||(name!="@")) {
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
