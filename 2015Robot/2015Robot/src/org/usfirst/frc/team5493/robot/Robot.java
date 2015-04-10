package org.usfirst.frc.team5493.robot; //frc.team5493

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * A This is a demo program showing the use of the RobotDrive class,
 * specifically it contains the code necessary to operate a robot with tank
 * drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	RobotDrive myRobot; // class that handles basic drive operations
	Joystick xboxStick; // set to ID 1 in DriverStation
	// Joystick logiTech; // set to ID 2 in DriverStation
	private static final int DRIVERIGHT = 0;
	private static final int DRIVELEFT = 2;
	Talon driveLeft = new Talon(DRIVELEFT);
	Talon driveRight = new Talon(DRIVERIGHT);
	Talon put = new Talon(1);
	Jaguar wheel1 = new Jaguar(3);
	Jaguar wheel2 = new Jaguar(4);
	
	final DriverStation ds = DriverStation.getInstance();
	boolean engaged = false;
	//DigitalInput limitSwitch = new DigitalInput(1);
	boolean latch1, latch2;

	public Robot() {
		myRobot = new RobotDrive(driveLeft, driveRight);
		myRobot.setMaxOutput(0.7);
		//myRobot.setExpiration(0.1);
		xboxStick = new Joystick(1);
		// logiTech = new Joystick(2);
	}

	@Override
	public void autonomous() {
		myRobot.setSafetyEnabled(false);

		boolean drive = false;
		while (isAutonomous() && isEnabled()) {

			if (!drive) {
//				myRobot.tankDrive(1.0, 1.0);
				myRobot.drive(0.5, 0.0);
				/*
				 * Timer.delay(1.0); myRobot.drive(0.0, 0.0); Timer.delay(0.5);
				 * put.set(-.5); Timer.delay(0.2); put.set(0.0);
				 * Timer.delay(0.5); myRobot.drive(-1.0, 0.0);
				 */
			
			drive = true;
			
			}
			Timer.delay(3.0);
			myRobot.drive(0.0, 0.0);

		}

	}

	public void operatorControl() {
		myRobot.isSafetyEnabled();
		while (true && isOperatorControl() && isEnabled()) {
			
			myRobot.tankDrive(xboxStick.getRawAxis(1), xboxStick.getRawAxis(5));

			Timer.delay(0.005); // wait for a motor update time
			
			wheel1.isSafetyEnabled();
			wheel2.isSafetyEnabled();
			
			if(xboxStick.getRawButton(3)){
				wheel1.set(.5);
			}else{
				wheel1.set(0.0);
			}
			
			if(xboxStick.getRawButton(3)){
				wheel2.set(.5);
			}else{
				wheel2.set(0.0);
			}

			if (xboxStick.getRawButton(1)) {
				put.set(-.15);
				latch1 = true;
				DriverStation.reportError("Latch LIGHT Hit", false);
			} else if (xboxStick.getRawButton(2)){
				put.set(-.2);
				latch2 = true;
				DriverStation.reportError("Latch HEAVY Hit", false);
			} else if (xboxStick.getRawButton(5) /*&& limitSwitch.get() == false*/) {
				if (latch1 == true || latch2 == true) {
					latch1 = false;
					latch2 = false;
					put.set(.5);
				} else {
					DriverStation.reportError("DOWN", false);
					put.set(.5);
				}
			} else if (xboxStick.getRawButton(6)) {
				if (latch1 == true || latch2 == true) {
					latch1 = false;
					latch2 = false;
					put.set(-.5);
				} else {
					DriverStation.reportError("UP", false);
					put.set(-.5);
				}
			} else if (latch1 == false && latch2 == false && xboxStick.getRawButton(1) == false
					&& xboxStick.getRawButton(6) == false
					&& xboxStick.getRawButton(5) == false) {
				put.set(0);
			}

		}
	}
}
