package madkit.bees;

import static madkit.bees.BeeLauncher.HORNET_ROLE;
import static madkit.bees.BeeLauncher.BEE_ROLE;
import static madkit.bees.BeeLauncher.COMMUNITY;
import static madkit.bees.BeeLauncher.FOLLOWER_ROLE;
import static madkit.bees.BeeLauncher.SIMU_GROUP;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import madkit.kernel.AbstractAgent;
import madkit.kernel.AgentAddress;
import madkit.message.ObjectMessage;
public class Hornet extends AbstractBee {
	
	
	private static final long serialVersionUID = -2393301998743816186L;

	
	
	@Override
    public void activate() {
		requestRole(COMMUNITY, SIMU_GROUP, HORNET_ROLE, null);
		requestRole(COMMUNITY, SIMU_GROUP, BEE_ROLE, null);
		
    }

	
	 @Override
	    public void buzz() {
		 final List<AbstractBee> listNearBees;
		 
		 synchronized (BeeLauncher.bees) {
			    listNearBees = BeeLauncher.bees.stream()
			        .filter(e -> computeDistance(e.myInformation.getCurrentPosition(), this.myInformation.getCurrentPosition()) < 10)
			        .collect(Collectors.toList());
			}
		 int beeCount=listNearBees.size();
		 if (beeCount > 7) {
	            // Frelon entouré par trop d'abeilles, il meurt
	            getLogger().info(()->"Le frelon est entouré par plus de 7 abeilles ! Mort dans 5 secondes...");
	            killAgent(this,5);
	        } else if (beeCount > 4) {
	            // Frelon entouré par 5 à 7 abeilles, il ne peut plus attaquer
	            getLogger().info(()->"Merde, il y en a trop ! !");
	        } else if (beeCount > 0) {
	        	getLogger().info(()->"Le frelon va tuer !"+listNearBees.get(0));
	            // Frelon attaque une abeille proche
	            killAgent(listNearBees.get(0),3);
	        }
		 
		super.buzz();
	    }
	
	@Override
    protected void end() {
		getLogger().info(()->"Dommage j'en aurais encore bouffé quelques unes...");
    }
	
	
	@Override
	protected int getMaxVelocity() {
		if (beeWorld != null) {
		    return beeWorld.getHornetVelocity().getValue();
		}
		return 0;
	}

	@Override
	protected void computeNewVelocities() {
		if (beeWorld != null) {
		    int acc = beeWorld.getHornetAcceleration().getValue();
		    dX += randomFromRange(acc);
		    dY += randomFromRange(acc);
		}
		
	}
	 private double computeDistance(Point p1, Point p2) {
	        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	    }

}
