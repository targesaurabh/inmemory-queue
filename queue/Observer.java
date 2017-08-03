package inmemory.queue;

public interface Observer {
	
	//method to consume the produced data
	public void consume(String value);
	
}