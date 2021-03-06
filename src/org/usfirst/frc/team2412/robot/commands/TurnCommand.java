package org.usfirst.frc.team2412.robot.commands;

import org.usfirst.frc.team2412.robot.PlateColorChecker;

public class TurnCommand extends CommandBase {
	private boolean firstRun = true;
	private final double Kp = 0.65;
	private double angleToTurn = 90;
	
	public TurnCommand() {
		requires(driveBase);
	}
	
	@Override
	protected void execute() {
		if(exitEarly()) {
			return;
		}
		if(firstRun) {
			driveBase.resetAngle();
			if(PlateColorChecker.getStartingPosition().equals("Left")) {
				angleToTurn = Math.abs(angleToTurn);
			} else if(PlateColorChecker.getStartingPosition().equals("Right")) {
				angleToTurn = -Math.abs(angleToTurn);
			}
			firstRun = false;
		}
		double angle = getError();
		driveBase.drive(0, angle*Kp/90, false);
		System.out.println("Turning...");
	}
	
	@Override
	protected boolean isFinished() {
		return exitEarly() || Math.abs(getError()) < 1;
	}
	
	private boolean exitEarly() {
		return PlateColorChecker.getStartingPosition().equals("Center") || (!PlateColorChecker.isScaleCorrectColor() && !PlateColorChecker.isSwitchCorrectColor());
	}
	
	protected void end() {
		firstRun = true;
	}
	
	private double getError() {
		return angleToTurn - driveBase.getAngle();
	}
}
