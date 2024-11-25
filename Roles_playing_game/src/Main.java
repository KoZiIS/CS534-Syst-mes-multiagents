
import madkit.kernel.Madkit;

public class Main {

	public static void main(String[] args) {
		new Madkit("--launchAgents", CounterAgent.class.getName() + ",true,1;", EmitterAgent.class.getName()+ ",true,1;",ControllerAgent.class.getName()+",true,1;");
	}
}
