package inmemory.queue;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Observer {
	
	private String  name;
	
	private List<Observer> prerequisites;
	
	public Consumer(String cName){
		this.name = cName;
		this.prerequisites = new ArrayList();
	}

	public void addPrerequisites(Observer cObj){
		this.prerequisites.add(cObj);
	}

	public List<Observer> getPrerequisites(){
		return this.prerequisites;
	}

	@Override
	public void consume(String valueToBeConsumed) {

		System.out.println(name+":: "+valueToBeConsumed);		             

	}

}