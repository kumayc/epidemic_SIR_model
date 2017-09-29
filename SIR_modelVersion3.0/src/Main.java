import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static Agent[] patches;
	public static int NUMBER_OF_PEOPLE;
	public static int NUMBER_OF_PEOPLE_NOT_INFECTIOUS = 1000;
	public static int NUMBER_OF_STRAIN0 = 100;
	public static int NUMBER_OF_STRAIN1 = 100;
	public static int TIME_STEP = 0;
	public static LineGraph lg;
	public static int NUMBER_OF_STEP = 1000;
	public static int NUMBER_OF_STEP_FOR_EACH_WEEK = 50;
	public static int NUMBER_OF_CONTACT_EACH_STEP = 500;
	public static int NUMBER_OF_STRAIN = 2;
	public static Strain[] strain;
	public static int[] NUMBER_OF_INFECTIOUS_PEOPLE;
	public static Scanner scanner;
	public static double [][] influenceTable;
	
	public static void main(String[] args){
		scanner = new Scanner(System.in);
		initialization();		
		for (int i = 0; i < NUMBER_OF_STEP; i ++){
			eachStep();
		}
		lg.showLineGraph();
		show();
		scanner.close();
	}
	
	public static void initialization(){
		System.out.println("Please input how many strains you want in this model.");
//		NUMBER_OF_STRAIN = Integer.parseInt(scanner.nextLine());
		NUMBER_OF_PEOPLE = NUMBER_OF_PEOPLE_NOT_INFECTIOUS + NUMBER_OF_STRAIN0 + NUMBER_OF_STRAIN1;
		lg = new LineGraph(NUMBER_OF_STRAIN);
		strain = new Strain[NUMBER_OF_STRAIN];
		NUMBER_OF_INFECTIOUS_PEOPLE = new int[NUMBER_OF_STRAIN];
		influenceTable = new double [NUMBER_OF_STRAIN][NUMBER_OF_STRAIN];
		
		strain[0] = new Strain(0, 0.3, 1.0, 1.0);
		strain[1] = new Strain(1, 0.3, 1.0, 1.0);
		NUMBER_OF_INFECTIOUS_PEOPLE[0] = NUMBER_OF_STRAIN0;
		NUMBER_OF_INFECTIOUS_PEOPLE[1] = NUMBER_OF_STRAIN1;

		
//		for (int i = 0; i < NUMBER_OF_STRAIN; i ++){
//			strain[i] = new Strain(i);
//			NUMBER_OF_INFECTIOUS_PEOPLE[i] = NUMBER_OF_INFECTIOUS_PEOPLE_FOR_EACH_STRAIN;
//		}
		System.out.println("Please input the influence table you want in this model.");
		for (int i = 0; i < NUMBER_OF_STRAIN; i ++){
			for (int j = 0 ; j < NUMBER_OF_STRAIN; j++){
				influenceTable[i][j] = Double.parseDouble(scanner.nextLine());
			}
		}
		
		System.out.println("Input completed....");	
		Strain.initialInfluenceTable(NUMBER_OF_STRAIN);
		Strain.setInfluenceTable(influenceTable);		
		Strain.initialInfluencePriorityTable();
		
		patches = new Agent[NUMBER_OF_PEOPLE];
		for(int i = 0; i < NUMBER_OF_PEOPLE_NOT_INFECTIOUS; i ++){
			patches[i] = new Agent(i);
		}
		
		for(int i = NUMBER_OF_PEOPLE_NOT_INFECTIOUS; i < NUMBER_OF_PEOPLE_NOT_INFECTIOUS + NUMBER_OF_STRAIN0; i ++){
			patches[i] = new Agent(i, strain[0]);
		}
		
		for(int i = NUMBER_OF_PEOPLE_NOT_INFECTIOUS + NUMBER_OF_STRAIN0; i < NUMBER_OF_PEOPLE_NOT_INFECTIOUS + NUMBER_OF_STRAIN0 + NUMBER_OF_STRAIN1; i ++){
			patches[i] = new Agent(i, strain[1]);
		}
		
		System.out.println("initial with: ");
		System.out.println("susceptible people: " + NUMBER_OF_PEOPLE_NOT_INFECTIOUS);
		
		System.out.println("infectious people strain0" +": " + NUMBER_OF_STRAIN0);
		System.out.println("infectious people strain1" +": " + NUMBER_OF_STRAIN1);

		System.out.println("");
	}
	
	public static void eachStep(){
		TIME_STEP ++;
		upDateStates();
		randomContact();		
		editLineGraph();
	}
	

	public static void randomContact(){
		Random r = new Random(); 
		ArrayList<Agent> contactionList = new ArrayList<Agent>();
		Collections.addAll(contactionList, patches);
		
		for(int i = 0; i < NUMBER_OF_CONTACT_EACH_STEP; i ++){
			if (contactionList.size() < 2){
				System.out.println("no more agent can join the contact");
				return;
			}
			
			int agentOne;
			int agentTwo;
			
            //Any agent cannot contact with itself.
			while((agentOne = r.nextInt(contactionList.size())) == (agentTwo = r.nextInt(contactionList.size()))){
			}
			Agent.contact(patches[contactionList.get(agentOne).getId()], patches[contactionList.get(agentTwo).getId()]);
			if (agentOne > agentTwo){
				contactionList.remove(contactionList.get(agentOne));
				contactionList.remove(contactionList.get(agentTwo));
			} else{
				contactionList.remove(contactionList.get(agentTwo));
				contactionList.remove(contactionList.get(agentOne));
			}
		} 
	}
	
	public static void upDateStates(){
		for (int i = 0; i < NUMBER_OF_PEOPLE; i ++){
			patches[i].Update();
		}
	}
	
	public static void editLineGraph(){
		lg.editLineGraph(TIME_STEP, NUMBER_OF_PEOPLE_NOT_INFECTIOUS, NUMBER_OF_INFECTIOUS_PEOPLE);
	}
	
	public static void show(){
		System.out.println("susceptible people: " + NUMBER_OF_PEOPLE_NOT_INFECTIOUS);
		for (int i = 0; i < NUMBER_OF_STRAIN; i ++){
			System.out.println("infectious people strain" + i +": " + NUMBER_OF_INFECTIOUS_PEOPLE[i]);
		}
		System.out.println("Rnning successd!");
	}
}
