package game;

import java.util.Hashtable;

public class TileTypeTable<K,V> extends Hashtable<K, V> {

	private static final long serialVersionUID = 1L;
	public Hashtable<K, Hashtable<String, Boolean>> flags = 
			new Hashtable<K, Hashtable<String, Boolean>>();
	
	public boolean putFlag(K key, String name, boolean flag){
		if (!this.containsKey(key)) return false;//Can't set a flag for a key that is not a tile type
		else if (flags.containsKey(key) && flags.get(key) != null){//if it has already been set...
			flags.get(key).put(name, flag);
			return true;
		}else{//If this is a new flag...
			Hashtable<String, Boolean> value = new Hashtable<String, Boolean>();
			value.put(name, flag);
			flags.put(key, value); 
			return true;
		}
	}
	
	/**
	 * 
	 * @param key 
	 * @param flagname
	 * @return 1 if the flag is set to true, -1 if the flag is set to false, and 0 if
	 * no flag with that name is present.
	 */
	public int getFlag(K key, String flagname){
		if (!flags.containsKey(key) || !flags.get(key).containsKey(flagname)) return 0;
		else if (flags.get(key).get(flagname) == true) return 1;
		else return -1;
	}
}
