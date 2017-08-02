package inmemory.queue;

public class QueueSystem {

	public static void main(String[] args) {

		final Producer producer = new Producer();

		Topic tp1 = new Topic("attack");
		Topic tp2 = new Topic("play");
		Topic tp3 = new Topic("study");


		Consumer c1 = new Consumer("commando1");
		// c1.setTopic(tp1);
		Consumer c2 = new Consumer("commando2");
		// c2.setTopic(tp1);
		Consumer c21 = new Consumer("commando3");
		// c21.setTopic(tp1);

		c1.addPrerequisites(c2);
		c1.addPrerequisites(c21);

		Consumer c3 = new Consumer("player1");
		// c3.setTopic(tp2);
		Consumer c4 = new Consumer("player2");
		// c4.setTopic(tp2);

		Consumer c5 = new Consumer("student1");
		// c5.setTopic(tp3);
		Consumer c6 = new Consumer("student2");
		// c6.setTopic(tp3);

		tp1.register(c1);
		tp1.register(c2);
		tp1.register(c21);

		tp2.register(c3);
		tp2.register(c4);

		tp3.register(c5);
		tp3.register(c6);

		producer.addTopic(tp1);
		producer.addTopic(tp2);
		producer.addTopic(tp3);
			
		// Create producer thread
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

        // Create producer thread
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

        producerThread.start();

        topicsThread.start();

	}

}