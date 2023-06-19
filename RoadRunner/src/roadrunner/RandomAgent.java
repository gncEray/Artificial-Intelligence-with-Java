
package roadrunner;

import java.util.Random;

public class RandomAgent extends Agent {
	private Random random = new Random();
			
	@Override
	Environment getBestPath(Environment environment) {
		environment.resetAgent();
		
		while (environment.isMovable()) {
			int deltaY = random.nextInt(-1, 2);
			environment.moveAgent(deltaY);
		}		
		
		return environment;
	}
}
