package fuzzySystem;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.Gpr;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.IOException;

public class Running {
	
	private final static boolean showGraphics = true;
	private final static double allowedError = 0.2;

	public static void main(String[] args) {
		// Load FCL
		String fclFile = new String("canon.fcl");
		FIS fis = FIS.load(fclFile);
		
		// Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fclFile + "'");
            return;
        }
        
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        
        if (showGraphics) {
    		// Show ruleset
            JFuzzyChart.get().chart(functionBlock);
            
    		// Print ruleSet
    		System.out.println(functionBlock);
        }
        

		// Feed and control system:
		// Initial setup:
		int distance = 9; // 7
		int height = 4; // 2
		int cannonHeight = 6; // 0
		CannonSystem cannon = new CannonSystem(distance, height, cannonHeight);
		
		Distance distanceFromTarget = new Distance(0, 0);
		double cannonAngle = 60; // 45
		double shotSpeed = 3; // 3

		functionBlock.setVariable("distance", distance);
		functionBlock.setVariable("height", height);
		
		// Set inputs
        /*distance : REAL;
    	height : REAL;
        xr : REAL;
        yr : REAL;
        */


		int  i= 0;
		
		do {
			// Activate system
			cannon.discreteShot(cannonAngle, shotSpeed, distanceFromTarget);
			
			// Shot stats:
			System.out.println("Shot: " + i);
			System.out.println("Shot angle: " + cannonAngle);
			System.out.println("Shot speed: " + shotSpeed);
			System.out.println("Shot X distance: " + distanceFromTarget.getDx());
			System.out.println("Shot Y distance: " + distanceFromTarget.getDy());
			System.out.println(" - - -  - ");
			
			i++;
			
			//feed fuzz to control system
			functionBlock.setVariable("xr", distanceFromTarget.getDx());
			functionBlock.setVariable("yr", distanceFromTarget.getDy());

			// Evaluate 
			functionBlock.evaluate();

			// Show output variable's chart
			if (showGraphics && i == 1) {
				Variable angle = functionBlock.getVariable("angle");
				Variable strenght = functionBlock.getVariable("strenght");
				JFuzzyChart.get().chart(angle, angle.getDefuzzifier(), true);
				JFuzzyChart.get().chart(strenght, strenght.getDefuzzifier(), true);
			}
			//Gpr.debug("poor[service]: " + functionBlock.getVariable("service").getMembership("poor"));
			
			System.out.println(functionBlock.getVariable("angle").getValue());
			cannonAngle = cannonAngle + functionBlock.getVariable("angle").getValue();
			shotSpeed = shotSpeed + functionBlock.getVariable("strenght").getValue();
			System.out.println(functionBlock.getVariable("strenght").getValue());

		} while ((Math.abs(distanceFromTarget.getDy()) > allowedError || distanceFromTarget.getDx() >= 0) && i < 10);
		
		if (Math.abs(distanceFromTarget.getDy()) < allowedError && distanceFromTarget.getDx() <= 0) {
			System.out.println("You hit the target!");
		} else{
			System.out.println("Out of tries");
		}
		
		
		
		


	}
	
}
