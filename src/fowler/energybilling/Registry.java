package fowler.energybilling;

import java.util.HashMap;


//JK - this class is needed to make the code run, the disability charge method cares about the zones!

public class Registry {
	private static HashMap<String,Zone> zones  = new HashMap<String,Zone>();
	
	public Registry() {
		zones = new HashMap<String,Zone>();	
	}

	public static void add(Zone zone) {
			String s = zone.getName();
			zones.put(s, zone);	
	}

	public static Zone get(String name) {		
		return zones.get(name);
	}
	
	public static Boolean isEmpty(){
		return zones.isEmpty();
	}
	
	public static int size(){
		return zones.size();
	}

}
