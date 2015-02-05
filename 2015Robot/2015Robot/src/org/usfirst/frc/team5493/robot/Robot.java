package org.usfirst.frc.team5493.robot; //frc.team5493
//import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CANTalon.ControlMode;
//import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

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
	Joystick leftStick; // set to ID 1 in DriverStation
	Joystick rightStick;
	Trigger liftup;
	Button liftdown;
	Relay relay = new Relay(3);
	private static final int DRIVERIGHT = 5;
	private static final int DRIVELEFT = 1;
	private static final int LIFTUP = 3;
	// private static final int LIFTDOWN = 2;
	CANTalon talon = new CANTalon(2);
	final DriverStation ds = DriverStation.getInstance();

	public Robot() {
		myRobot = new RobotDrive(1, 2);
		myRobot.setExpiration(0.1);
		leftStick = new Joystick(1);
		// liftup = new Trigger();
		// liftdown = new JoystickButton(leftStick, LIFTDOWN);

	}

	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		while (true && isOperatorControl() && isEnabled()) {
			myRobot.tankDrive(leftStick.getRawAxis(DRIVERIGHT),
					leftStick.getRawAxis(DRIVELEFT));

			Timer.delay(0.005); // wait for a motor update time

			if (leftStick.getRawButton(3)) {
				ds.reportError("Button Clicked", false);
				talon.changeControlMode(ControlMode.PercentVbus);
				talon.set(.1);
				Timer.delay(3);
				talon.set(0);
				Timer.delay(1);
				talon.set(-.1);
				Timer.delay(3);
				talon.set(0);
			}
			// liftup.whenPressed(new Command(){
			// boolean isbusy = false;
			// @Override
			// protected void initialize() {
			// // TODO Auto-generated method stub
			// talon.changeControlMode(ControlMode.PercentVbus);
			// talon.set(1);
			// }
			//
			// @Override
			// protected void execute() {
			// // TODO Auto-generated method stub
			// isbusy = true;
			// double selectedsensorspeed = talon.getSpeed();
			// System.out.println("in execute");
			// ds.reportError("in execute", false);
			// Timer.delay(0.5);
			// isbusy = false;
			// }
			//
			// @Override
			// protected boolean isFinished() {
			// // TODO Auto-generated method stub
			// return isbusy;
			// }
			//
			// @Override
			// protected void end() {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// protected void interrupted() {
			// // TODO Auto-generated method stub
			//
			// }});

		}

		// }else if(leftStick.getRawButton(LIFTDOWN)){
		// relay.set(Value.kOn);
		// relay.setDirection(Direction.kReverse);
		// Timer.delay(3);
		// relay.set(Value.kOff);
		// }
		//
		// }
	}
}
