package inmemory.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import org.json.simple.JSONObject;
import java.util.Random;

public class Producer{

	private List<Topic> 		topics;			//Topics for which data is to be generated
	private Queue<JSONObject> 	messageQueue;	//Queue to store the data produced by producer 

	private int 				queueSize;		//Size or capacity of the queue

	Object lock = new Object();					//Lock to be used to synchronize produce and consume functions

	public Producer(){
		this.topics 		= new ArrayList<>();
		this.messageQueue	= new LinkedList<JSONObject>();
		queueSize			= 3;				//Initial queue size. It can be changed.
	}

	//Add a topic for which the producer will produce the data
	public void addTopic(Topic tp) {
	
		if(tp == null){
			throw new NullPointerException("Null topic");
		}
		
		if(!topics.contains(tp)) {
			topics.add(tp);
		}
	
	}

	//Consume the data from Queue and notify the observers of the topic
	public void checkForTopics() throws InterruptedException{

		JSONObject jsonObject = null;

		while(true){

			//Lock is used to handle concurrent read writes
			synchronized(lock){

				//Wait till Queue has some data to be consumed
				while (messageQueue.size() == 0){
					System.out.println("Waiting for producer");
					lock.wait();					
				}

				//Read and remove the data from the Queue and notify the producer thread to start producing data
				jsonObject = (JSONObject) messageQueue.poll();
	            lock.notify();

				//Sleep period is added so that print statements can be seen on console/terminal
	            Thread.sleep(1000);

			}

			//Get topic object and value produced by producer
	        Topic topic 			 = (Topic) jsonObject.get("topic");
	        String value			 = (String) jsonObject.get("value");

	        //Retrieve the observers of the topic
	        List<Observer> observers = topic.observers;

	        //Used when there is a dependency of consumers
	        List<Observer> alreadyConsumed = new ArrayList();
	       
	        Consumer conObj = null;
	        
	        for (Observer observerObj : observers) {

	        	//If this observer/consumer has not already consumed the data then proceed
	        	if(!alreadyConsumed.contains(observerObj)){

	        		conObj = (Consumer) observerObj;

	        		//Iterate through the dependencies of the consumer and allow the dependency to consume data first
	        		for (Observer prerequisitesObj : conObj.getPrerequisites()) {	  

						prerequisitesObj.consume(value);
						alreadyConsumed.add(prerequisitesObj);

					}

					observerObj.consume(value);	
	        
	        	}
	        	
			}             

		}
             
	}
	
	//Produce data for given topics and that data will be then consumed by consumers registered on the given topics
	public void produce() throws InterruptedException{	

        while (true){   

        	//Lock is used to handle concurrent read writes
			synchronized (lock){

				//Wait until the Queue is free to add new data
				while (messageQueue.size() == queueSize){
					System.out.println("Waiting for consumers");
					lock.wait();
				}

				//Random number generator to generate an index for topic list
				Random rand   = new Random();
			    int randomNum = rand.nextInt((2 - 0) + 0) + 0;

	            System.out.println("Producer  :: "+randomNum);

				//Store JSON data in the Queue
	            JSONObject jsonObject = new JSONObject();
                jsonObject.put("topic", this.topics.get(randomNum));
                jsonObject.put("value", "Just Do it.");

	            this.messageQueue.add(jsonObject);

	            //Notify the consumer to consume the data
	            lock.notify();

	            //Sleep period is added so that print statements can be seen on console/terminal
	            Thread.sleep(1000);

	        }

        }
		
	}


}