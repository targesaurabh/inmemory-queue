package inmemory.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

//Consumers are subscribed to the messages that match a particular expression. 
//Here expression is nothing but the name of the topic.
public class Topic implements Subject {

	private String topicName;					//Name of the topic

	public List<Observer> observers;			//Consumers those are subscribed to a current topic

	private final Object MUTEX = new Object();	//Lock to be used while registering or unregistering the observers
	
	public Topic(String topicName){
		this.topicName = topicName;
		this.observers = new ArrayList<>();
	}

	//Register a consumer/observer to the topic
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

	//Unregister a consumer/observer to the topic
	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			observers.remove(obj);
		}
	}
	
}