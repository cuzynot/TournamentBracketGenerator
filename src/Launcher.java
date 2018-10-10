import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Launcher {

	public static void main(String[] args) throws AWTException, InterruptedException {
//		new ManagementSystem();
		
		Robot r = new Robot();
		
		Thread.sleep(2000);
		
		for (int i = 0; i < 10000; i++) {
			// Thread.sleep(5);
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
	}

}