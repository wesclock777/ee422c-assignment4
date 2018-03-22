package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		energy-=Params.walk_energy_cost;
		move(direction,1);
	}
	
	protected final void run(int direction) {
		energy-=Params.run_energy_cost;
		if(energy<=0) {
			population.remove(this);
			return;
		}
		move(direction,1);
		move(direction,1);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(energy<Params.min_reproduce_energy) {
			return;
		}
		else {
			offspring.energy = energy/2;
			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			offspring.move(direction, 1);
			babies.add(offspring);
			energy = energy-energy/2;
		}
	}
	
	private void move(int direction, int amount) {
		for(int i=0; i<amount; i++) {
			if(direction>0&&direction<4){
				y_coord+=1;
			}
			if(direction>4) {
				y_coord-=1;
			}
			if(direction>2&&direction<6){
				x_coord-=1;
			}
			if(direction<2||direction>6) {
				x_coord+=1;
			}
			if(x_coord>Params.world_width-1) {
				x_coord=0;
			}
			if(y_coord>Params.world_height-1) {
				y_coord=0;
			}
			if(x_coord<0) {
				x_coord=Params.world_width-1;
			}
			if(y_coord<0) {
				y_coord=Params.world_height-1;
			}
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class<?> c = Class.forName(myPackage+"."+critter_class_name);
			Object b = c.newInstance();
			if(b instanceof Critter) {
				Critter bug = (Critter) b;
				bug.x_coord= Critter.getRandomInt(Params.world_width);
				bug.y_coord= Critter.getRandomInt(Params.world_height);
				bug.energy = Params.start_energy;
				population.add(bug);
			}
		} 
		catch(Exception e) {
			throw new InvalidCritterException("Error Class not found");
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class<?> query = Class.forName(myPackage+"."+critter_class_name);
			for(Critter bug: population) {
				if(query.isInstance(bug)) {
					result.add(bug);
				}
			}
		} 
		catch(Exception e) {
			throw new InvalidCritterException("Error Class not found");
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population = new java.util.ArrayList<Critter>();
		babies = new java.util.ArrayList<Critter>();
	}
	
	public static void worldTimeStep() {
		// time step
		List<Critter> moved = new java.util.ArrayList<Critter>();
		List<Critter> newpopulation= new java.util.ArrayList<Critter>();
		for(Critter c: population) {
			c.doTimeStep();
			if(c.energy>0) {
				newpopulation.add(c);
			}
		}
		population = newpopulation;
		
		// encounters
		List<Critter> encountered= new java.util.ArrayList<Critter>();
		for(Critter c: population) {
			for(Critter b: population) {
				if(!c.equals(b)&&c.y_coord==b.y_coord&&c.x_coord==b.x_coord&&(!encountered.contains(b))&&(!encountered.contains(c))){
					encountered.add(c);
					encountered.add(b);
					encounter(c,b);
				}
			}
		}
		
		// subtracting rest energy
		List<Critter> newpopulation2= new java.util.ArrayList<Critter>();
		for(Critter c: population) {
			c.energy-=Params.rest_energy_cost;
			if(c.energy>0) {
				newpopulation2.add(c);
			}
		}
		population = newpopulation2;
		
		// adding babies to world
		for(Critter baby: babies) {
			population.add(baby);
		}
		babies = new java.util.ArrayList<Critter>();
		
		// spawning additional algae
		for(int i = 0; i<Params.refresh_algae_count; i++) {
			try {
				Critter.makeCritter("Algae");
			} 
			catch (InvalidCritterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	private static void encounter(Critter a, Critter b) {
		boolean aattack = a.fight(b.toString());
		boolean battack = b.fight(a.toString());
		if(a.x_coord==b.x_coord&&a.y_coord==b.y_coord&&a.energy>0&&b.energy>0) {
			int admg=0;
			if(aattack==true) {
				admg = Critter.getRandomInt(a.energy);
			}
			int bdmg=0;
			if(battack==true) {
				bdmg = Critter.getRandomInt(b.energy);
			}
			if(admg>=bdmg) {
				a.energy+=b.energy/2;
				b.energy=0;
				System.out.println(a.toString()+" vs "+b.toString());
			}
			else {
				b.energy+=a.energy/2;
				a.energy=0;
				System.out.println(b.toString()+" vs "+a.toString());
			}
		}
	}
	
	public static void displayWorld() {
		String border = "+";
		for(int i = 0; i < Params.world_width; i++) {
			border= border+"-";
		}
		border=border+"+";
		
		System.out.println(border);
		
		for(int i =0; i< Params.world_height; i++) {
			String row = "|";
			for(int k=0; k< Params.world_width; k++) {
				boolean occupied = false;
				for(Critter c:population) {
					if(c.x_coord==k&&c.y_coord==i) {
						row+=c.toString();
						occupied = true;
						break;
					}
				}
				if(occupied==false) {
					row+=" ";
				}
			}
			row+="|";
			System.out.println(row);
		}
		System.out.println(border);		
		return;
	}
}
