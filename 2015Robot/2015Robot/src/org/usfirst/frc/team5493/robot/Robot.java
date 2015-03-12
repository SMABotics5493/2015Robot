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
import edu.wpi.first.wpilibj.TalonSRX;
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
	Relay relay = new Relay(3);
	private static final int DRIVERIGHT = 5;
	private static final int DRIVELEFT = 1;
	CANTalon leftFront = new CANTalon(1);
	CANTalon leftRear = new CANTalon(2);
	CANTalon rightFront = new CANTalon(3);
	CANTalon rightRear = new CANTalon(4);
	TalonSRX put = new TalonSRX(1);
	final DriverStation ds = DriverStation.getInstance();
	boolean engaged = false;

	public Robot() {
		myRobot = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
		myRobot.setExpiration(0.1);
		leftStick = new Joystick(1);


	}

	public void operatorControl() {
		myRobot.isSafetyEnabled();
		while (true && isOperatorControl() && isEnabled()) {
			myRobot.tankDrive(leftStick.getRawAxis(DRIVERIGHT),
					leftStick.getRawAxis(DRIVELEFT));

			Timer.delay(0.005); // wait for a motor update time

			if (leftStick.getRawButton(5)) {
				DriverStation.reportError("DOWN", false);
				put.set(.5);
			}else if(leftStick.getRawButton(6)) {
				DriverStation.reportError("UP", false);
				put.set(-.5);
			}else if(leftStick.getRawButton(1)) {
				DriverStation.reportError("ENGAGED", false);
				put.set(-.2);
			}else{
				DriverStation.reportError("Neg Button not clicked", false);
						put.set(0);
					}
			}
	}	
	}

