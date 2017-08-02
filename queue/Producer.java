package inmemory.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import org.json.simple.JSONObject;
import java.util.Random;

public class Producer{

	private List<Topic> 		topics;
	public Queue<JSONObject> 	messageQueue;

	private int 				queueSize;

	Object lock = new Object();

	public Producer(){
		this.topics 		= new ArrayList<>();
		this.messageQueue	= new LinkedList();
		queueSize			= 3;
	}

	public void addTopic(Topic tp) {
	
		if(tp == null){
			throw new NullPointerException("Null topic");
		}
		
		if(!topics.contains(tp)) {
			topics.add(tp);
		}
	
	}

	public void checkForTopics() throws InterruptedException{

		JSONObject jsonObject = null;

		while(true){

			synchronized(lock){

				while (messageQueue.size() == 0){
					System.out.println("Waiting for producer");
					lock.wait();					
				}

				jsonObject = (JSONObject) messageQueue.poll();
	            lock.notify();

			}

	        Topic topic 			 = (Topic) jsonObject.get("topic");
	        String value			 = (String) jsonObject.get("value");

	        List<Observer> observers = topic.observers;
	        List<Observer> alreadyConsumed = new ArrayList();
	       
	        Consumer conObj = null;
	        
	        for (Observer observerObj : observers) {

	        	if(!alreadyConsumed.contains(observerObj)){

	        		conObj = (Consumer) observerObj;

	        		for (Observer prerequisitesObj : conObj.getPrerequisites()) {	  

						prerequisitesObj.consume(value);
						alreadyConsumed.add(prerequisitesObj);

					}

					observerObj.consume(value);	
	        
	        	}
	        	
			}             

		}
             
	}
	
	public void produce() throws InterruptedException{

		int value = 0;		

        while (true){   

			synchronized (lock){

				while (messageQueue.size() == queueSize){
					System.out.println("Waiting for consumers");
					lock.wait();
				}

				Random rand   = new Random();

			    int randomNum = rand.nextInt((2 - 0) + 0) + 0;

	            System.out.println("Producer  :: "+randomNum);

	            JSONObject jsonObject = new JSONObject();
                jsonObject.put("topic", this.topics.get(randomNum));
                jsonObject.put("value", "Just Do it.");

	            this.messageQueue.add(jsonObject);

	            lock.notify();

	        }

        }
		
	}


}