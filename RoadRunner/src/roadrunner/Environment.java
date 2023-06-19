/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roadrunner;

/**
 *
 * @author Eray
 */
import java.util.*;

public class Environment {

    final PrimeNumbers primeNumbers;
    final private long[][] world;
    private final Agent agent;
    private final List<Integer> agentPositions;
    boolean showAgentMovements = true;

    Environment(Environment environment) {
        primeNumbers = environment.primeNumbers;
        world = environment.world;
        agent = environment.agent;
        agentPositions = new ArrayList<Integer>(environment.agentPositions);
        showAgentMovements = environment.showAgentMovements;
    }

    Environment(Environment environment, int deltaY) {
        primeNumbers = environment.primeNumbers;
        world = environment.world;
        agent = environment.agent;
        agentPositions = new ArrayList<Integer>(environment.agentPositions);
        showAgentMovements = environment.showAgentMovements;

        moveAgent(deltaY);
    }

    Environment(int width, int height, int primeCount, int randomSeed, Agent agent) throws Exception {
        final int minimumWorldSize = 3;
        if (width < minimumWorldSize || height < minimumWorldSize) {
            throw new Exception("Invalid world size. World size me at least " + minimumWorldSize + "x" + minimumWorldSize);
        }

        primeNumbers = new PrimeNumbers(primeCount);

        world = createWorld(width, height, randomSeed, primeNumbers);

        this.agent = agent;

        agentPositions = new ArrayList<>();
        agentPositions.add(height / 2);
    }

    private static long[][] createWorld(int width, int height, int randomSeed, PrimeNumbers primeNumbers) {
        Random random = new Random(randomSeed);

        int index = 0;
        long[][] world = new long[height][];
        for (int y = 0; y < height; y++) {
            world[y] = new long[width];
        }

        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int fullPercentage = 100;
                int randomChoice = random.nextInt(fullPercentage);

                final int emptyPercentage = 80;
                if (randomChoice > emptyPercentage) {
                    final int primePercentage = 10;
                    if (randomChoice > emptyPercentage + primePercentage) {
                        // primes are positive points for the agent
                        world[y][x] = primeNumbers.getPrime(index);
                        index++;
                    } else {
                        // some random number (can be prime or not)
                        world[y][x] = random.nextInt(x + 1) + 1;
                    }
                }
            }
        }

        return world;
    }

    final public int getWidth() {
        return world[0].length;
    }

    final public int getHeight() {
        return world.length;
    }

    final public int agentX() {
        return agentPositions.size() - 1;
    }

    final public int agentY() {
        return agentPositions.get(agentPositions.size() - 1);
    }

    public boolean isMovable() {
        return (agent != null && agentX() < getWidth() - 1);
    }

    public boolean isMovable(int deltaY) {
        return (agent != null && agentX() < getWidth() - 1 && (agentY() + deltaY >= 0 && agentY() + deltaY < getHeight()));
    }

    public Integer nextY(int deltaY) {
        if (isMovable()) {
            final int y = Math.min(Math.max(agentY() + deltaY, 0), getWidth() - 1);
            return y;
        } else {
            return null;
        }
    }

    public List<Integer> possibleMovements() {
        List<Integer> movements = new ArrayList<>();

        if (isMovable()) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                if (isMovable(deltaY)) {
                    movements.add(deltaY);
                }
            }
        }

        return movements;
    }

    public boolean moveAgent(int deltaY) {
        if (isMovable(deltaY)) {
            agentPositions.add(nextY(deltaY));

            return true;
        } else {
            return false;
        }
    }

    public void resetAgent() {
        agentPositions.clear();
        agentPositions.add(getHeight() / 2);
    }

    public long getAgentScore() {
        long agentScore = 0;

        for (int x = 0; x < agentPositions.size(); x++) {
            int y = agentPositions.get(x);
            long cellValue = world[y][x];
            if (cellValue > 0) {
                if (primeNumbers.isInPrimeList(cellValue)) {
                    agentScore += cellValue;
                } else {
                    agentScore -= cellValue;
                }
            }
        }
        return agentScore;
    }

    @Override
    public String toString() {
        int cellSize = (int) Math.floor(Math.log10(primeNumbers.getLastPrime())) + 1;

        String empytCell = "[" + " ".repeat(cellSize) + "]";
        String agentCell = "[" + "*".repeat(cellSize) + "]";
        String format = "%" + cellSize + "d";

        StringBuilder worldDump = new StringBuilder();

        for (int y = 0; y < world.length; y++) {
            for (int x = 0; x < world[y].length; x++) {
                long cellValue = world[y][x];

                String cellString;

                if (cellValue <= 0) {
                    cellString = empytCell;
                } else {
                    cellString = "[" + String.format(format, cellValue) + "]";
                }

                if (showAgentMovements && agent != null && x < agentPositions.size() && y == agentPositions.get(x)) {
                    cellString = agentCell;
                }

                worldDump.append(cellString);
            }

            if (y < world.length - 1) {
                worldDump.append("\n");
            }
        }

        return worldDump.toString();
    }

}
