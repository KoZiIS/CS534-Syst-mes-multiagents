import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
import madkit.kernel.Message;
import madkit.message.IntegerMessage;

/**
 * Shows how agents exchange messages.
 * 
 * 
 * 
 * 
 * 
 * 
 * To exchange messages, agents have to exist in an artificial society. That is, agents have to create communities
 * containing groups and take roles within to interact with others. Doing so, agents get agent addresses which could be
 * used to send them messages. Here, one agent (Agent1) is communicating with another agent (Agent2) by getting its
 * agent address in the artificial society and using it with sendMessage.
 */
public class EmitterAgent extends Agent {

    /*
     * Firstly, take a position in the artificial society.
     */
	
	@Override
    protected void activate() {
        // rejoindre le groupe et le rôle nécessaires
		getLogger().setLevel(Level.FINEST);

		createGroup("communication", "RolePlay");// Create the group GroupTest in the community communication.
		requestRole("communication", "RolePlay", "EmitterAgent");// Request the role RoleTest1.
		pause(500);
    }
    
    @Override
    protected void live() {
    	int randomCount=(int) (Math.random() * 10);
    	AgentAddress other = null;
    	while (other == null ) {
        	other = getAgentWithRole("communication", "RolePlay", "CounterAgent");
    	}
  
        // envoyer un nombre aléatoire de messages aux agents Compteur
        for (int i = 0; i < randomCount; i++) {
            sendMessage(other, new IntegerMessage(1));
            pause((int) (Math.random() * 1000 + 100));
        }
        // terminaison de l'agent après l'envoi des messages
        
        
    }

    @Override
    protected void end() {
    	getLogger().info("Fin de l'agent emetteur...");
    }

   

    @Override
    public void setupFrame(AgentFrame frame) {
	super.setupFrame(frame);
	frame.setLocation(100, 320 * (hashCode() - 2));
    }

    /**
     * @param argss
     *            Running Agent1 will launch 2 instances of both Agent1 and Agent2. They will send themselves 1 message.
     */
    @SuppressWarnings("unused")
    public static void main(String[] argss) {
	}
}