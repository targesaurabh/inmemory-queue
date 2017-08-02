package inmemory.queue;

public class Consumer implements Observer {
	
	private String  name;
	private Subject topic;
	
	public Consumer(String cName){
		this.name = cName;
	}

	@Override
	public void consume(String valueToBeConsumed) {

		// String msg = (String) topic.getRelavantData(this);
		
		// if(msg == null){
		// 	System.out.println(name+":: Empty queue found");
		// }else{
		// 	System.out.println(name+":: "+msg);
		// }
		 	System.out.println(name+":: "+valueToBeConsumed);
	}

	@Override
	public void setTopic(Subject sub) {
		this.topic = sub;
	}

}