package inmemory.queue;

//Main class to start the execution
public class QueueSystem {

	public static void main(String[] args) {
		
		//Producer object creation
		final Producer producer = new Producer();

		//Create topics to which observers/consumers will listen for new messages
		Topic tp1 = new Topic("war");
		Topic tp2 = new Topic("play");
		Topic tp3 = new Topic("study");

		//Create consumers those want to listen to war related topic
		Consumer c1  = new Consumer("commando1");
		Consumer c2  = new Consumer("commando2");
		Consumer c3  = new Consumer("commando3");

		//Dependency of c1 is added as c2 and c3 just like [c1->(c2,c3)]
		c1.addPrerequisites(c2);
		c1.addPrerequisites(c3);

		//Create consumers those want to listen to play related topic
		Consumer c4 = new Consumer("player1");
		Consumer c5 = new Consumer("player2");

		//Create consumers those want to listen to study related topic
		Consumer c6 = new Consumer("student1");
		Consumer c7 = new Consumer("student2");

		//Register consumers to the topic
		tp1.register(c1);
		tp1.register(c2);
		tp1.register(c3);

		tp2.register(c4);
		tp2.register(c5);

		tp3.register(c6);
		tp3.register(c7);

		//Add topics to the producer so that producer can create messages/data related to the topics
		producer.addTopic(tp1);
		producer.addTopic(tp2);
		producer.addTopic(tp3);
			
		//Create producer thread
        Thread producerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {                
				try
                {
					producer.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        // Create a thread to listen to the messages produced by producer
        Thread topicsThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {                
				try
                {
					producer.checkForTopics();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        //Start the thread executions
        producerThread.start();

        topicsThread.start();

	}

}