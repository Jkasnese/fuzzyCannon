package fuzzySystem;

public class CannonSystem {

	private int xa, ya, yc;
	private final int timeStep = 1;
	private final double gravity = 9.8;


	public CannonSystem(int xa, int ya, int yc){
		this.xa = xa;
		this.ya = ya;
		this.yc = yc;
	}

	public void discreteShot(final double cannonAngle, final double shotSpeed, Double dx, Double dy){
		double radAngle = Math.toDegrees(cannonAngle);
		double vx = shotSpeed * Math.cos(radAngle);
		double vy = shotSpeed * Math.sin(radAngle);

		// Discrete analysis:
		
		// Find where Yp = 0:
		// Yc + Vy*t - gt²/2 = 0
		// time = solve above
		double a = gravity/2;
		
		double delta = Math.sqrt(vy*vy + 4*(a)*yc);
		double timeYp = ((vy*-1) + delta) / 2*a;
		
		// Find where xp is at that time. 
		double xp = vx*timeYp;
		// Save dx == xa - xp.
		dx = new Double(xa - xp);
		
		// If xp < xa, dy = -1 and return.
		if (xa > xp) {
			dy = new Double(-1);
			return;
		} else { // Else, calculate dy:
			double timeXp = ya/vx;
			double yp = yc + vy*timeXp - (gravity*(timeXp*timeXp))/2;
			
			dy = new Double(ya - yp);
			
			return;
		}
	}
	
	public void simulateShot(final int cannonAngle, final int shotSpeed, Double dx, Double dy){
		double radAngle = Math.toDegrees(cannonAngle);
		double vx = shotSpeed * Math.cos(radAngle);
		double vy = shotSpeed * Math.sin(radAngle);
		int time = 0;
		
		double xp = 0;
		double yp = yc;
				
		// Simulate shot analysis:
		do {
			xp = vx*time;
			yp += vy*time - gravity/2*(time*time);
			if (xp == xa){
				dy = new Double(ya-yp);
			} 
			time = time+timeStep;
		} while (yp != 0);
		dx = new Double(xa - xp);
	}
	
	
}
