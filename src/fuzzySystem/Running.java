package fuzzySystem;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.Gpr;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.IOException;

public class Running {

	public static void main(String[] args) {
		// Load FCL
		String fclFile = new String("canon.fcl");
		FIS fis = FIS.load(fclFile);
		
		// Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fclFile + "'");
            return;
        }
        
		// Show ruleset
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        JFuzzyChart.get().chart(functionBlock);

		// Set inputs
        /*distance : REAL;
    	height : REAL;
        xr : REAL;
        yr : REAL;
        */
		functionBlock.setVariable("distance", 3);
		functionBlock.setVariable("height", 7);
		functionBlock.setVariable("xr", -2);
		functionBlock.setVariable("yr", -2);

		// Evaluate 
		functionBlock.evaluate();

		// Show output variable's chart
		Variable angle = functionBlock.getVariable("angle");
		Variable strenght = functionBlock.getVariable("strenght");
		JFuzzyChart.get().chart(angle, angle.getDefuzzifier(), true);
		JFuzzyChart.get().chart(strenght, strenght.getDefuzzifier(), true);

		//Gpr.debug("poor[service]: " + functionBlock.getVariable("service").getMembership("poor"));

		// Print ruleSet
		System.out.println(functionBlock);
		System.out.println("Angle:" + functionBlock.getVariable("angle").getValue());
		System.out.println("Strenght:" + functionBlock.getVariable("strenght").getValue());
        
		/*
		CannonSystem cannon = new CannonSystem(23, 52, 0);
		Double dx = null;
		Double dy = null;

		double cannonAngle = 45;
		double shotSpeed = 30;

		do {
			cannon.discreteShot(cannonAngle, shotSpeed, dx, dy);
			
			//feed fuzz 

		} while (dy.doubleValue() != 0);
		*/
	}
	
}
