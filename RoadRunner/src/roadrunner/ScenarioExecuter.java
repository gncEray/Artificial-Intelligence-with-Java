
package roadrunner;

import java.util.*;

public class ScenarioExecuter {
	private List<Environment> environments = new ArrayList<>();
	private long totalScore = 0;

	ScenarioExecuter(int width, int height, int primeCount, int[] seedNumbers, Agent agent) throws Exception {
		for(int seedNumber : seedNumbers) {
			Environment environment = new Environment(width, height, primeCount, seedNumber, agent);
			environment = agent.getBestPath(environment);
			environments.add(environment);
			totalScore += environment.getAgentScore();
		}
	}

	public int getEnvironmentCount() {
		return environments.size();
                
	}
	
	public long getSingleScore(int environmentIndex) {
		Environment environment = environments.get(environmentIndex);
		
		return environment.getAgentScore();
	}
	
	public long getTotalScore() {
		return totalScore;
	}
		
	void dumpEnvironment(int index, boolean showAgentMovements) {
		Environment environment = environments.get(index);
		
		environment.showAgentMovements = showAgentMovements;
		System.out.println(environment);
				
		environment.showAgentMovements = true;
	}
	
}
