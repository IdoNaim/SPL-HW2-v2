package bgu.spl.mics;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


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
	public synchronized static MessageBusImpl getInstance(){
		return SingletonHolder.instance;
	}

	@Override
	public synchronized <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
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
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
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
	public synchronized <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub
		Future f=  eventsResults.get(e);
		if(f != null){
			f.resolve(result);
		}
		//eventsResults.remove(e);
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub
		BlockingQueue<MicroService> queue = broadcastsSubscribers.get(b.getClass());
		for(MicroService service: queue){
			BlockingQueue<Message> queue2 = services.get(service);
			if(queue2!=null){
				try {
					queue2.put(b);
				}catch(InterruptedException e){
					//TODO: think what t put here
				}
			}
		}

	}

	
	@Override
	public synchronized <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		BlockingQueue<MicroService> queue = eventsSubscribers.get(e.getClass());
		try {
			MicroService m = queue.poll();
			if(m != null) {
				services.get(m).put(e);
				queue.put(m);
				Future<T> future = new Future<>();
				eventsResults.putIfAbsent(e, future);
				return future;
			}
			return null;
		}catch (InterruptedException E){}
		return null; //TODO understand what to to put here
	}

	@Override
	public synchronized void register(MicroService m) {
		// TODO Auto-generated method stub
		BlockingQueue<Message> newQueue = new LinkedBlockingQueue<Message>();
		services.putIfAbsent(m,newQueue);

	}

	@Override
	public synchronized void unregister(MicroService m) {
		// TODO Auto-generated method stub
		BlockingQueue<Message> queue=services.remove(m);
		if(queue != null) {
			for (Message message : queue) {
				if (message instanceof Event) {
					complete((Event) message, null);
				}
			}
		}

	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		try{
			Message message = services.get(m).take();
			return message;
		}catch (InterruptedException e){
			throw new InterruptedException();
		}
	}

}
