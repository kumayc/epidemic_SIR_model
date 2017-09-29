import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineGraph {
	
	private int numberOfStrain;
//	private XYSeries seriesOfSusceptiblePeople = new XYSeries("SUSCEPTIBLE_PEOPLE");
	private XYSeries[] seriesOfInfectiousPeople;
	
	
	public LineGraph(int numberOfStrain){
		this.numberOfStrain = numberOfStrain;
		seriesOfInfectiousPeople = new XYSeries[numberOfStrain];
		for (int i = 0; i < numberOfStrain; i ++){
			seriesOfInfectiousPeople[i] = new XYSeries("Strain " + i);
		}
	}
	
	public void showLineGraph(){
		XYSeriesCollection datasetOfSIR = getCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("SIR-Population Chart", "Time_Step", "Population", datasetOfSIR, PlotOrientation.VERTICAL, true, true, false);
		ChartFrame cf = new ChartFrame("Line-Graph", chart);
		cf.pack();
		cf.setVisible(true);
	}
	
	public XYSeriesCollection getCollection(){
		XYSeriesCollection datasetOfSIR = new XYSeriesCollection();
//		datasetOfSIR.addSeries(seriesOfSusceptiblePeople);
		for (int i = 0; i < numberOfStrain; i ++){
			datasetOfSIR.addSeries(seriesOfInfectiousPeople[i]);
		}
		return datasetOfSIR;
	}
	
	public void editLineGraph(int timeStep, int notInfectious, int[] infectious){
//		seriesOfSusceptiblePeople.add(timeStep, notInfectious);
		for (int i = 0; i < infectious.length; i ++){
			seriesOfInfectiousPeople[i].add(timeStep, infectious[i]);
		}
	}
	
//	public XYSeries getSeriesOfSusceptiblePeople(){
//		return seriesOfSusceptiblePeople;
//	}
	
	public XYSeries[] getSeriesOfInfectiousPeople() {
		return seriesOfInfectiousPeople;
	}

}
