package inmemory.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Topic implements Subject {

	private String topicName;

	public List<Observer> observers;

	private final Object MUTEX = new Object();
	
	public Topic(String topicName){
		this.topicName = topicName;
		this.observers = new ArrayList<>();
	}


	@Override
	public void register(Observer obj) {
		if(obj == null){
			throw new NullPointerException("Null Observer");
		}
		synchronized (MUTEX) {
			if(!observers.contains(obj)) {
				observers.add(obj);
			}
		}
	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			observers.remove(obj);
		}
	}

	
}