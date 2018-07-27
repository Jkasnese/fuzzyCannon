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

	public void shot(final int cannonAngle, final int shotSpeed, Integer dx, Integer dy){
		int radAngle = Math.toDegrees(cannonAngle);
		int vx = shotSpeed * Math.cos(radAngle);
		int vy = shotSpeed * Math.sin(radAngle);

		int xp = 0;
		int yp = yc; 
		int time = 0;

		
		do {

			xp = vx*time;
			yp += vy*time - gravity/2*(time*time);

			if (xp == xa){
				dy.setValue(ya-yp);
			} 

			time = time+timeStep;
		} while (yp != 0);
		dx.setValue(xa - xp);
	}
}
