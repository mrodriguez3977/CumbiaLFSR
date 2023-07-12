import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class CumbiaLFSR 
{
	public static void main(String[] args)
	{
		try 
		{
			FileWriter myWriter = new FileWriter("The path to the output file you want");
			//generate 100 bitstreams of length 1 million each
			for(int i = 0; i < 100; i++)
				myWriter.write(generateSequence(1000000).toString());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} 
		catch (IOException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		//System.out.print(generateSequence(1000000).toString());
	}
	
	public static StringBuilder generateSequence(int n)
	{
		StringBuilder s = new StringBuilder();
		//x^29 + x^20 + x^16 + x^11 + x^8 + x^4 + x^3 + x^2 + 1
		ArrayList<Integer> reg29 = new ArrayList<Integer>();
		//x^31 + x^27 + x^23 + x^19 + x^15 + x^11 + x^7 + x^3 + 1
		ArrayList<Integer> reg31 = new ArrayList<Integer>();
		//x^23 + x^18 + x^16 + x^13 + x^11 + x^8 + x^5 + x^2 + 1
		ArrayList<Integer> reg23 = new ArrayList<Integer>();
		//x^27 + x^22 + x^17 + x^15 + x^14 + x^13 + x^6 + x^1 + 1
		ArrayList<Integer> reg27 = new ArrayList<Integer>();
		ArrayList<Integer> temp;
		ArrayList<Integer> generator1 = reg29;
		ArrayList<Integer> generator2 = reg31;
		ArrayList<Integer> generator3 = reg23;
		ArrayList<Integer> generator4 = reg27;
		boolean swapped2 = false;
		boolean swapped3 = false;
		boolean swapped4 = false;
		
		//very much not random initial fill
		//but we certainly want it to be different each time for testing
		for(int i = 0; i < 29; i++)
		{
			reg29.add((int) (Math.random() + 0.5));
		}
		for(int i = 0; i < 31; i++)
		{
			reg31.add((int) (Math.random() + 0.5));
		}
		for(int i = 0; i < 23; i++)
		{
			reg23.add((int) (Math.random() + 0.5));
		}
		for(int i = 0; i < 27; i++)
		{
			reg27.add((int) (Math.random() + 0.5));
		}
		
		for(int i = 0; i < n + 10000; i++)
		{
			//throw out the first 10k outputs so that we have good initial fill 
			if(i > 9999)
				//System.out.println(generator1.get(0) ^ generator2.get(0) ^ generator3.get(0) ^ 1);
				s.append(generator1.get(0) ^ generator2.get(0) ^ generator3.get(0) ^ 1);
			
			//then swap or find "partners"
			if(generator1.get(7) == generator2.get(11))
			{
				temp = generator1;
				generator1 = generator2;
				generator2 = temp;
				swapped2 = true;
			}
			else if(generator1.get(7) == generator2.get(13))
			{
				temp = generator1;
				generator1 = generator3;
				generator3 = temp;
				swapped3 = true;
			}
			else if(generator1.get(7) == generator2.get(17))
			{
				temp = generator1;
				generator1 = generator4;
				generator4 = temp;
				swapped4 = true;
			}
			if(generator2.get(11) == generator3.get(13) && !swapped2 && !swapped3)
			{
				temp = generator2;
				generator2 = generator3;
				generator3 = temp;
				swapped2 = true;
				swapped3 = true;
			}
			else if(generator1.get(11) == generator4.get(17) && !swapped2 && !swapped4)
			{
				temp = generator2;
				generator2 = generator4;
				generator4 = temp;
				swapped2 = true;
				swapped4 = true;
			}
			if(generator1.get(13) == generator2.get(17) && !swapped3 && !swapped4)
			{
				temp = generator3;
				generator3 = generator4;
				generator4 = temp;
			}
			swapped2 = false;
			swapped3 = false;
			swapped4 = false;
			
			//then shift each register accordingly and add at the end
			//this is where the speed of the implementation is greatly reduced,
			//since there is a bunch of if statements
			if(generator1.equals(reg29))
			{
				//x^29 + x^20 + x^16 + x^11 + x^8 + x^4 + x^3 + x^2 + 1
				//29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator1.add(generator1.get(0) ^ generator1.get(9) ^ generator1.get(13) ^
						generator1.get(18) ^ generator1.get(21) ^ generator1.get(25) ^ 
						generator1.get(26) ^ generator1.get(27));
				generator1.remove(0);
			}
			else if(generator1.equals(reg31))
			{
				//x^31 + x^27 + x^23 + x^19 + x^15 + x^11 + x^7 + x^3 + 1
				//31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator1.add(generator1.get(0) ^ generator1.get(4) ^ generator1.get(8) ^
						generator1.get(12) ^ generator1.get(16) ^ generator1.get(20) ^
						generator1.get(24) ^ generator1.get(28));
				generator1.remove(0);
			}
			else if(generator1.equals(reg23))
			{
				//x^23 + x^18 + x^16 + x^13 + x^11 + x^8 + x^5 + x^2 + 1
				//23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator1.add(generator1.get(0) ^ generator1.get(5) ^ generator1.get(7) ^
						generator1.get(10) ^ generator1.get(12) ^ generator1.get(15) ^
						generator1.get(18) ^ generator1.get(21));
				generator1.remove(0);
			}
			else if(generator1.equals(reg27))
			{
				//x^27 + x^22 + x^17 + x^15 + x^14 + x^13 + x^6 + x^1 + 1
				//27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator1.add(generator1.get(0) ^ generator1.get(5) ^ generator1.get(10) ^
						generator1.get(12) ^ generator1.get(13) ^ generator1.get(14) ^
						generator1.get(21) ^ generator1.get(26));
				generator1.remove(0);
			}
			
			if(generator2.equals(reg29))
			{
				//x^29 + x^20 + x^16 + x^11 + x^8 + x^4 + x^3 + x^2 + 1
				//29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator2.add(generator2.get(0) ^ generator2.get(9) ^ generator2.get(13) ^
						generator2.get(18) ^ generator2.get(21) ^ generator2.get(25) ^ 
						generator2.get(26) ^ generator2.get(27));
				generator2.remove(0);
			}
			else if(generator2.equals(reg31))
			{
				//x^31 + x^27 + x^23 + x^19 + x^15 + x^11 + x^7 + x^3 + 1
				//31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator2.add(generator2.get(0) ^ generator2.get(4) ^ generator2.get(8) ^
						generator2.get(12) ^ generator2.get(16) ^ generator2.get(20) ^
						generator2.get(24) ^ generator2.get(28));
				generator2.remove(0);
			}
			else if(generator2.equals(reg23))
			{
				//x^23 + x^18 + x^16 + x^13 + x^11 + x^8 + x^5 + x^2 + 1
				//23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator2.add(generator2.get(0) ^ generator2.get(5) ^ generator2.get(7) ^
						generator2.get(10) ^ generator2.get(12) ^ generator2.get(15) ^
						generator2.get(18) ^ generator2.get(21));
				generator2.remove(0);
			}
			else if(generator2.equals(reg27))
			{
				//x^27 + x^22 + x^17 + x^15 + x^14 + x^13 + x^6 + x^1 + 1
				//27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator2.add(generator2.get(0) ^ generator2.get(5) ^ generator2.get(10) ^
						generator2.get(12) ^ generator2.get(13) ^ generator2.get(14) ^
						generator2.get(21) ^ generator2.get(26));
				generator2.remove(0);
			}
			
			if(generator3.equals(reg29))
			{
				//x^29 + x^20 + x^16 + x^11 + x^8 + x^4 + x^3 + x^2 + 1
				//29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator3.add(generator3.get(0) ^ generator3.get(9) ^ generator3.get(13) ^
						generator3.get(18) ^ generator3.get(21) ^ generator3.get(25) ^ 
						generator3.get(26) ^ generator3.get(27));
				generator3.remove(0);
			}
			else if(generator3.equals(reg31))
			{
				//x^31 + x^27 + x^23 + x^19 + x^15 + x^11 + x^7 + x^3 + 1
				//31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator3.add(generator3.get(0) ^ generator3.get(4) ^ generator3.get(8) ^
						generator3.get(12) ^ generator3.get(16) ^ generator3.get(20) ^
						generator3.get(24) ^ generator3.get(28));
				generator3.remove(0);
			}
			else if(generator3.equals(reg23))
			{
				//x^23 + x^18 + x^16 + x^13 + x^11 + x^8 + x^5 + x^2 + 1
				//23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator3.add(generator3.get(0) ^ generator3.get(5) ^ generator3.get(7) ^
						generator3.get(10) ^ generator3.get(12) ^ generator3.get(15) ^
						generator3.get(18) ^ generator3.get(21));
				generator3.remove(0);
			}
			else if(generator3.equals(reg27))
			{
				//x^27 + x^22 + x^17 + x^15 + x^14 + x^13 + x^6 + x^1 + 1
				//27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator3.add(generator3.get(0) ^ generator3.get(5) ^ generator3.get(10) ^
						generator3.get(12) ^ generator3.get(13) ^ generator3.get(14) ^
						generator3.get(21) ^ generator3.get(26));
				generator3.remove(0);
			}
			
			if(generator4.equals(reg29))
			{
				//x^29 + x^20 + x^16 + x^11 + x^8 + x^4 + x^3 + x^2 + 1
				//29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator4.add(generator4.get(0) ^ generator4.get(9) ^ generator4.get(13) ^
						generator4.get(18) ^ generator4.get(21) ^ generator4.get(25) ^ 
						generator4.get(26) ^ generator4.get(27));
				generator4.remove(0);
			}
			else if(generator4.equals(reg31))
			{
				//x^31 + x^27 + x^23 + x^19 + x^15 + x^11 + x^7 + x^3 + 1
				//31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator4.add(generator4.get(0) ^ generator4.get(4) ^ generator4.get(8) ^
						generator4.get(12) ^ generator4.get(16) ^ generator4.get(20) ^
						generator4.get(24) ^ generator4.get(28));
				generator4.remove(0);
			}
			else if(generator4.equals(reg23))
			{
				//x^23 + x^18 + x^16 + x^13 + x^11 + x^8 + x^5 + x^2 + 1
				//23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator4.add(generator4.get(0) ^ generator4.get(5) ^ generator4.get(7) ^
						generator4.get(10) ^ generator4.get(12) ^ generator4.get(15) ^
						generator4.get(18) ^ generator4.get(21));
				generator4.remove(0);
			}
			else if(generator4.equals(reg27))
			{
				//x^27 + x^22 + x^17 + x^15 + x^14 + x^13 + x^6 + x^1 + 1
				//27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2
				generator4.add(generator4.get(0) ^ generator4.get(5) ^ generator4.get(10) ^
						generator4.get(12) ^ generator4.get(13) ^ generator4.get(14) ^
						generator4.get(21) ^ generator4.get(26));
				generator4.remove(0);
			}
		}
		return s;
	}
}
