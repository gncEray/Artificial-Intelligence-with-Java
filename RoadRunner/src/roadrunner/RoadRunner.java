package roadrunner;

/**
 *
 * @author Eray
 */
public class RoadRunner {

    
    public static void main(String[] args) throws Exception {
        
        int width = 25;
		int height = 7;
		int primeCount = 25;
		int[] seedNumbers = {11, 22, 33, 44, 55, 66, 77, 88, 99};
		//Agent agent = new RandomAgent();
		Agent agent = new SearchAgent();
		ScenarioExecuter scenarioExecuter = new ScenarioExecuter(width, height, primeCount, seedNumbers, agent);
		int environmentCount = scenarioExecuter.getEnvironmentCount();
		for (int environmentIndex=0; environmentIndex<environmentCount; environmentIndex++) {
			scenarioExecuter.dumpEnvironment(environmentIndex, false);
			System.out.println("-");
			scenarioExecuter.dumpEnvironment(environmentIndex, true);
			System.out.println("Score = " + scenarioExecuter.getSingleScore(environmentIndex));
			System.out.println();
		}
		System.out.println("Total score = " + scenarioExecuter.getTotalScore());
        
    }
    
}
