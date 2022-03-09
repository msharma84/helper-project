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

}
