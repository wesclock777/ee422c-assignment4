package assignment4;

import assignment4.Critter;

public class FightingBug extends Critter{
	int willtofight = 5;
	public String toString() { return "F"; }
	
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
