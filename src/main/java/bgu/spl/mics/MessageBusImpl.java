package bgu.spl.mics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only one public method (in addition to getters which can be public solely for unit testing) may be added to this class
 * All other methods and members you add the class must be private.
 */
public class MessageBusImpl implements MessageBus {
	ConcurrentHashMap<Event<?>, Future<?>> eventsResults;
	ConcurrentHashMap<Class<? extends Event<?>>,BlockingQueue<MicroService>> eventsSubscribers;
	ConcurrentHashMap<Class<? extends Broadcast>, BlockingQueue<MicroService>> broadcastsSubscribers;
	ConcurrentHashMap<MicroService, BlockingQueue<Message>> services;
	private static class SingletonHolder{
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	public static MessageBusImpl getInstance(){
		return SingletonHolder.instance;
	}
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub
		BlockingQueue<MicroService> queue = eventsSubscribers.get(type);
		if(queue != null) {
			try {
				queue.put(m);
			} catch (InterruptedException e) {
			}
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub
		BlockingQueue<MicroService> queue = broadcastsSubscribers.get(type);
		if(queue != null) {
			try {
				queue.put(m);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub
		Future f=  eventsResults.get(e);
		if(f != null){
			f.resolve(result);
		}
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
