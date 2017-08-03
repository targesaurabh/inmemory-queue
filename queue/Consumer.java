package inmemory.queue;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Observer {
	
	private String  name;					//Name of the consumer

	private List<Observer> prerequisites;  //Manage dependency for a consumer like C->(A,B)

	public Consumer(String cName){
		this.name = cName;
		this.prerequisites = new ArrayList();
	}

	//Add a dependency for current consumer
	public void addPrerequisites(Observer cObj){
		this.prerequisites.add(cObj);
	}

	//Retrieve the dependencies for current consumer
	public List<Observer> getPrerequisites(){
		return this.prerequisites;
	}

	//Consume the produced data
	@Override
	public void consume(String valueToBeConsumed) {

		System.out.println(name+":: "+valueToBeConsumed);		             

	}

}