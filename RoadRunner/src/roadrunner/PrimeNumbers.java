
package roadrunner;

import java.util.*;

public class PrimeNumbers {
	private long[] primeNumbers;
	
	PrimeNumbers(int n) throws Exception {
		primeNumbers = createFirstNPrimeNumbers(n);
	}
	
	private static long[] createFirstNPrimeNumbers(int n) throws Exception {
		long[] primes = new long[n];
		
		final int maximumPrimeCount = 1000000;
		if (n < 1 || n > maximumPrimeCount) {
			throw new Exception("Prime number count must be between 1 and " + maximumPrimeCount);
		}
		
		int currentNumber = 2; 
		int foundPrimeCount = 0;
		while (foundPrimeCount < n) {
			// if you test all the numbers up to the square root, you can rest assured that the number is prime
			long checkNumber = (long)Math.sqrt(currentNumber);
			
			boolean isPrime = true;
			for (int i=0; i<foundPrimeCount; i++) {
				long number = primes[i];

				if (currentNumber % number == 0) {
					isPrime = false;
					break;
				}
				
				if (number > checkNumber) {
					break;
				}
			}
			
			if (isPrime) {
				primes[foundPrimeCount] = currentNumber;
				foundPrimeCount++;
			}

			currentNumber++;
		}
		
		return primes;
	}
	
	public long getPrime(int index) {
		index = Math.min(Math.max(index, 0), primeNumbers.length-1);
		
		return primeNumbers[index];
	}
	
	public long getLastPrime() {
		return primeNumbers[primeNumbers.length-1];
	}
	
	public boolean isInPrimeList(long number) {
		return Arrays.binarySearch(primeNumbers, number) >= 0;
	}
		
	@Override
	public String toString()
	{
		return Arrays.toString(primeNumbers);
	}
	
}
