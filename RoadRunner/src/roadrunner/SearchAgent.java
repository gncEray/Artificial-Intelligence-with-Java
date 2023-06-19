package roadrunner;

import java.util.*;

public class SearchAgent extends Agent {

    long maximumAgentScore;
    Environment bestEnvironment;

    @Override
    Environment getBestPath(Environment environment) {
        environment.resetAgent();

        maximumAgentScore = Integer.MIN_VALUE;
        bestEnvironment = null;

        nextMove(environment);

        return bestEnvironment;
    }


    
    void nextMove(Environment environment) {

        // recursion process stop when there is nowhere to go
        if (!environment.isMovable()) {
            // assigning the environment which has maximum score to maximumAgentScore variable
            if (environment.getAgentScore() > maximumAgentScore) {
                maximumAgentScore = environment.getAgentScore();
                bestEnvironment = environment;
                // System.out.println("Environment assigned to Score: " + maximumAgentScore + " and the world:\n" + bestEnvironment);
                // you can remove comment line above in order to see step by step  
            }

        } else {

            // taking all possible movements into a list
            List<Integer> list = environment.possibleMovements();
            
            // DFS algorithm checks all possibilities on grid
            for (Integer deltaY : list) {
                environment = new Environment(environment, deltaY);
                nextMove(environment);
            }
            
        }
        
    }
    
}
