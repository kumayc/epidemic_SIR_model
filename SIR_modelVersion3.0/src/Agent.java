import java.util.ArrayList;
import java.util.Random;

public class Agent {
	
	/*this parameter shows that the agent is infected by which strain
	 * when the agent is in state "S", strain = null;
	 * when it is in state "I" or state "R" strain != null;
	 * */
	private Strain strain = null;
	
	/*record the agent's infectious history
	 * */
	private ArrayList<String> historyRecord = new ArrayList<String>();
	
	/*start in 0, as a unique identification of each agent
	 * */
	private int id = 0;

	
	public Agent(int id, Strain strain){
		this.id = id;
		this.strain = strain;;
	}
	
	public Agent(int id){
		this.id = id;
	}
	
	public Agent(){
	}
	
	/*get and set function
	 * */
	
	public ArrayList<String> getHistoryRecord() {
		return historyRecord;
	}

	public void setHistoryRecord(ArrayList<String> historyRecord) {
		this.historyRecord = historyRecord;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId( int id ){
		this.id = id;
	}
	
	public static void contact(Agent agentOne, Agent agentTwo){
		if (agentOne.getStrain() != null && agentTwo.getStrain() == null){
			tryToinfect(agentOne, agentTwo);
		} else if (agentOne.getStrain() == null && agentTwo.getStrain() != null){
			tryToinfect(agentTwo, agentOne);
		}
	}
	
	public static void tryToinfect(Agent infectious, Agent susceptiable){
		Random r = new Random();
		double posibility = infectious.strain.getBeta();
		int targetRecord = 100; 
		double influenceRate = 100.0;
		for (int i = 0; i < Strain.getInfluencePriorityTable().length; i ++){
			targetRecord = Strain.getInfluencePriorityTable()[infectious.getStrain().getStrainId()][i];
			if (susceptiable.historyRecord.contains(String.valueOf(targetRecord))){
				influenceRate = Strain.getInfluenceTable()[infectious.getStrain().getStrainId()][targetRecord];
				posibility = posibility * influenceRate;
				break;
			}
		}

		if (r.nextDouble() < posibility){
			Main.patches[susceptiable.getId()].setStrain(infectious.getStrain());
			Main.NUMBER_OF_PEOPLE_NOT_INFECTIOUS --;
			Main.NUMBER_OF_INFECTIOUS_PEOPLE[infectious.strain.getStrainId()] ++;
		}
	}	
	
	public void Update(){
		Random r = new Random();
		if (strain != null){
			if (r.nextDouble() < strain.getGamma()/Main.NUMBER_OF_STEP_FOR_EACH_WEEK){
				historyRecord.add(String.valueOf(strain.getStrainId()));
				Main.NUMBER_OF_PEOPLE_NOT_INFECTIOUS ++;
				Main.NUMBER_OF_INFECTIOUS_PEOPLE[strain.getStrainId()] --;
				strain = null;
			}
		} else {
			for (int i = 0; i < historyRecord.size(); i ++){
				int strainId = Integer.valueOf(historyRecord.get(i)).intValue();
				if (r.nextDouble() < Main.strain[strainId].getTheta()/ Main.NUMBER_OF_STEP_FOR_EACH_WEEK){
					historyRecord.remove(i);
				}
			}
		}
	}

	public Strain getStrain() {
		return strain;
	}

	public void setStrain(Strain strain) {
		this.strain = strain;
	}
}
