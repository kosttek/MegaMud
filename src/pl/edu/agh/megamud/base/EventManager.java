/**
marcinko
*/
package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class EventManager extends Thread {
	private static EventManager instance;
	private Map <Long,Behaviour> map = new HashMap<Long, Behaviour>();
	
	private EventManager(){
		start();
	}
	
	public static EventManager getInstance(){
		if(instance == null)
			instance = new EventManager();
		return instance;
	}
	
	public void put(Long delay, Behaviour behaviour){
		Long timeStamp = createTimeStamp(delay);
		synchronized(map){
			map.put(timeStamp,behaviour);
		}
		notifyWhenTimeIsBefore(timeStamp);
	}
	
	private Long createTimeStamp(Long delay){
		Long timeStamp = System.currentTimeMillis() + delay;
		while(map.containsKey(timeStamp))
			timeStamp+=1;
		return timeStamp;
	}
	
	private void notifyWhenTimeIsBefore(Long time){
		if(isTimeBeforeClosestBehaviourTime(time))
			synchronized (this){
				notify();
			}
	}
	
	private boolean isTimeBeforeClosestBehaviourTime(Long time){
		Long closetTime = getClosestBehaviourTime();
		if(closetTime > time ){
			return true;
		}
		return false;
	}
	
	private Long getClosestBehaviourTime(){
		synchronized(map){
			SortedSet<Long> keys = new TreeSet<Long>(map.keySet());
			return keys.first();
		}
	}
	
	@Override
	public void run() {
		while(true){
			Long closestBehaviourTime;
			synchronized(map){
				closestBehaviourTime = getClosestBehaviourTime();
			}
			if(closestBehaviourTime == null){
				synchronized(this){
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else if(closestBehaviourTime <= System.currentTimeMillis()){
				synchronized(map){
					Behaviour behaviour = map.get(closestBehaviourTime);
					behaviour.makeAction();
					map.remove(closestBehaviourTime);
				}
			}else{
				synchronized(this){
					try {
						wait(closestBehaviourTime - System.currentTimeMillis() );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}


