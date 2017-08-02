package inmemory.queue;

public interface Observer {
	
	//method to update the observer, used by subject
	public void consume(String value);
	
	//attach with subject to observe
	public void setTopic(Subject sub);
}