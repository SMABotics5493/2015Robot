package org.usfirst.frc.team5493.robot; //frc.team5493

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Relay;
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
public class Robot2 { //  extends SampleRobot {
	RobotDrive myRobot; // class that handles basic drive operations
	Joystick xboxStick; // set to ID 1 in DriverStation
	// Joystick logiTech; // set to ID 2 in DriverStation
	// Relay relay = new Relay(3);
	private static final int DRIVERIGHT = 5;
	private static final int DRIVELEFT = 1;
	Talon driveRight = new Talon(0);
	Talon driveLeft = new Talon(2);
	Talon put = new Talon(1);
	final DriverStation ds = DriverStation.getInstance();
	boolean engaged = false;

	public Robot2() {
		myRobot = new RobotDrive(driveLeft, driveRight);
		myRobot.setExpiration(0.1);
		xboxStick = new Joystick(1);
		// logiTech = new Joystick(2);
	}

	public void operatorControl() {
		myRobot.isSafetyEnabled();
		while (true /*&& isOperatorControl() && isEnabled()*/) {
			myRobot.tankDrive(xboxStick.getRawAxis(DRIVELEFT),
					xboxStick.getRawAxis(DRIVERIGHT));

			Timer.delay(0.005); // wait for a motor update time

			// PUT control loop:
			if (xboxStick.getRawButton(5)) {
				DriverStation.reportError("DOWN", false);
				put.set(.5);
			} else if (xboxStick.getRawButton(6)) {
				DriverStation.reportError("UP", false);
				put.set(-.5);
			} else if (xboxStick.getRawButton(1)) {
				DriverStation.reportError("LATCH", false);
				put.set(-.2);
			} else {
				put.set(0);
			}
		}
	}
}
