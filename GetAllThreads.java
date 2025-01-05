import java.util.Set;

import org.apache.commons.lang3.ThreadUtils;

public class GetAllThreads {

	public static void main(String[] args) {
		
		System.out.printf("%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
		for(Thread t : ThreadUtils.getAllThreads()) {
			 System.out.printf("%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
		}
		
		Set<Thread> threads = Thread.getAllStackTraces().keySet();
		System.out.printf("%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
		
		for(Thread t : threads) {
			System.out.printf("%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
		}
	}
	// Signal Dispatcher: this thread handles signals sent by the operating system to the JVM.
	// Finalizer: this thread performs finalizations for objects that no longer need to release system resources.
	// Reference Handler: this thread puts objects that are no longer needed into the queue to be processed by the Finalizer thread.
}
