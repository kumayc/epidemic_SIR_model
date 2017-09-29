
public class Strain {
	
	private static double [][] influenceTable = {{0.0,0.9},{0.9,0.0}};
	private static int [][] influencePriorityTable = {{0,1},{1,0}};
	
	private int strainId;
	/*the rate of e effective contact between susceptible and infectious people
	 * */
	private double beta = 0.3;
	/*the rate of recovery of infectious people in one week
	 * */
	private double gamma = 1.0;
	/*the rate of lost of immunity of recovered people in four week
	 * */
	private double theta = 1.0/4.0;
	
//	private double changeRateOfBeta = 0.9;

	
	public Strain(int strainId, double beta, double gamma, double theta){
		this.strainId = strainId;
		this.beta = beta;
		this.gamma = gamma;
		this.theta = theta;
	}
	
	public static void initialInfluenceTable (int numbeOfStrain){
		setInfluenceTable(new double[numbeOfStrain][numbeOfStrain]);
	}
	
	public static void initialInfluencePriorityTable (){		
		setInfluencePriorityTable(new int[influenceTable.length][influenceTable[0].length]);
		for (int i = 0; i < influenceTable.length; i ++){
			for (int j = 0; j < influenceTable[0].length; j ++){
				influencePriorityTable[i][j] = j;
			}
		}
		double [][] tempArray = new double [influenceTable.length][influenceTable[0].length];
		
		for(int i = 0; i < influenceTable.length; i++){
			for (int j = 0; j < influenceTable[0].length; j ++){
				tempArray[i][j] = influenceTable[i][j];
			}
		}
		double tempValue;
		int tempLocation;
		for(int i = 0; i < tempArray.length; i ++){
			for(int j = 0; j < tempArray[0].length - 1; j++){
				for (int k = 0; k < tempArray[0].length - j - 1; k ++){
					if(tempArray[i][k] > tempArray[i][k + 1]){
						tempValue = tempArray[i][k];
						tempArray[i][k] = tempArray[i][k + 1];
						tempArray[i][k + 1] = tempValue;
						tempLocation = influencePriorityTable[i][k];
						influencePriorityTable[i][k] = influencePriorityTable[i][k + 1];
						influencePriorityTable[i][k + 1] = tempLocation;
					}
				}
			}
		}
		for(int i = 0; i < influenceTable.length; i++){
			for (int j = 0; j < influenceTable[0].length; j ++){
				System.out.println(influencePriorityTable[i][j]);
			}
		}
		
	}
	
	public int getStrainId() {
		return strainId;
	}
	public void setStrainId(int strainId) {
		this.strainId = strainId;
	}
	public double getTheta() {
		return theta;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}
	public double getGamma() {
		return gamma;
	}
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
	public double getBeta() {
		return beta;
	}
	public void setBeta(double beta) {
		this.beta = beta;
	}

	public static double [][] getInfluenceTable() {
		return influenceTable;
	}

	public static void setInfluenceTable(double [][] influenceTable) {
		Strain.influenceTable = influenceTable;
	}

	public static int [][] getInfluencePriorityTable() {
		return influencePriorityTable;
	}

	public static void setInfluencePriorityTable(int [][] influencePriorityTable) {
		Strain.influencePriorityTable = influencePriorityTable;
	}

	
}
