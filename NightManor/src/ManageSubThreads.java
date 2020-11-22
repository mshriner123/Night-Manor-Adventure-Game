import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ManageSubThreads contains methods to terminate all sub-threads when the main thread of this program is terminated and construct
 * a ManageSubThreads object. This class will eventually be tweaked so that individual sub-threads can be terminated, 
 * but that was beyond the scope of the assignment submission on August 2, 2020.
 * 
 * @author Michael Shriner
 */
public class ManageSubThreads {

	AtomicBoolean running;
	Thread worker;

	/**
	 * INTENT: Constructs a ManageSubThreads object.
	 * 
	 * PRECONDITION (started): aThread was started. 
	 * 
	 * POSTCONDITION: running was true and worker referred to aThread. 
	 */
	public ManageSubThreads (Thread aThread){
		running = new AtomicBoolean (true);
		worker = aThread;
	}

	/**
	 * INTENT: Interrupt each sub-thread.
	 * 
	 * PRECONDITION (non-trivial): The ArrayList subThreads is non-empty.
	 * 
	 * POSTCONDITION: Each sub-thread in the ArrayList subThreads was interrupted.
	 */
	public static void interruptAll() {
		for (int i = 0; i < NightManor.subThreads.size(); i++) {
			NightManor.subThreads.get(i).terminateRun();
		}
	}

	/**
	 * INTENT: Interrupts this sub-thread and sets running to false for this ManageSubThreads object.
	 * 
	 * POSTCONDITION: This sub-thread was interrupted and running was set to false for this ManageSubThreads object.
	 */
	public void terminateRun() {
		running.set(false);
		worker.interrupt();
	}

}
