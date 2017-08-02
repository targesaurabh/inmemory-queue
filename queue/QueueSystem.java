package inmemory.queue;

public class QueueSystem {

	public static void main(String[] args) {
		//create subject
		Producer producer = new Producer();
		
		//create observers
		Observer obs1 = new Consumer("Consumer A");
		Observer obs2 = new Consumer("Consumer B");
		Observer obs3 = new Consumer("Consumer C");
		
		//register observers to the subject
		producer.register(obs1);
		producer.register(obs2);
		producer.register(obs3);
		
		//attach observer to subject
		obs1.setSubject(producer);
		obs2.setSubject(producer);
		obs3.setSubject(producer);	
		
		//now send message to subject
		producer.produce("Hello");
	}

}