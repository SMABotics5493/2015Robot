package org.usfirst.frc.team5493.robot; //frc.team5493

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
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
	Joystick logiTech; // set to ID 2 in DriverStation
	private static final int DRIVERIGHT = 3;
	private static final int DRIVELEFT = 1;
	Talon driveLeft = new Talon(0);
	Talon driveRight = new Talon(1);
	Jaguar put = new Jaguar(2);
	Jaguar wheelright = new Jaguar(3);
	Jaguar wheelleft = new Jaguar(4);
	Servo rbput = new Servo(5);

	boolean intakeState = true;

	final DriverStation ds = DriverStation.getInstance();
	boolean engaged = false;
	// DigitalInput limitSwitch = new DigitalInput(1);
	boolean latch1, latch2;
	private double latch;
	private boolean initWheelsOn = true;
	public final int intakeWheelspowerlogitech = 5;
	public final int intakeWheelsdirlogitech = 6;
	public final int putUpxbox = 1;
	// public int putDownxbox = 6;
	public final int putinclatchxbox = 1;
	public final int putdeclatchxbox = 2;
	public final int rbputxbox = 5;
	public final double latchincrement = 0.04;
	private int wheelDir = 1;

	public Robot() {
		myRobot = new RobotDrive(driveLeft, driveRight);
		myRobot.setMaxOutput(.5);
		// myRobot.setExpiration(0.1);
		xboxStick = new Joystick(1);
		logiTech = new Joystick(2);
	}

	@Override
	public void autonomous() {
		myRobot.setSafetyEnabled(false);

		boolean drive = false;
		while (isAutonomous() && isEnabled()) {
			recyclingBinStart();
			//recyclingBinLandfill();
			//Tote();
			//Move();


		}

	}
	
	private void recyclingBinStart(){
		put.set(0.5);
		Timer.delay(1.0);
		put.set(0.0);
		myRobot.drive(0.7, 0);
		Timer.delay(3.0);
		myRobot.drive(0, 0);
	}
	
	private void recyclingBinLandfill(){
		myRobot.drive(0.5, 0);
		Timer.delay(.75);
		myRobot.drive(0,0);
		put.set(0.5);
		Timer.delay(1.0);
		put.set(0.0);
		myRobot.drive(-0.5, 0);
		Timer.delay(3.0);
		myRobot.drive(0, 0);
	}
	
	private void Tote(){
		myRobot.drive(.7, 0);
		Timer.delay(3.0);
		myRobot.drive(0, 0);
	}
	
	private void Move(){
		myRobot.drive(0.7, 0);
		Timer.delay(2.0);
		myRobot.drive(0,0);
	}
	
	
	public void operatorControl() {

		while (true && isOperatorControl() && isEnabled()) {
			
			if(xboxStick.getRawButton(4)){
				rbput.set(1.0);
			}else{
				rbput.set(0.0);
			}


			if (this.initWheelsOn) {
				initWheelsOn = false;
				wheelright.set(-0.5);
				wheelleft.set(0.5);
			} else {

				boolean powerbutton = logiTech
						.getRawButton(intakeWheelspowerlogitech);
				boolean dirbutton = logiTech
						.getRawButton(intakeWheelsdirlogitech);
				boolean didChange = false;
				if (dirbutton) {
					// change direction
					wheelDir = wheelDir * -1;
					wheelright.set(0.0);
					wheelleft.set(0.0);
					Timer.delay(0.09); // wait for a motor update time

					didChange = true;
					intakeState = false;
					DriverStation.reportError("DIRECTION", false);
				}

				if (powerbutton && intakeState) {
					DriverStation.reportError(
							"********** power off **********", false);
					this.intakeState = false;
					wheelright.set(0.0);
					wheelleft.set(0.0);
					Timer.delay(0.09); // wait for a motor update time

				} else if ((powerbutton || didChange) && !intakeState) {
					DriverStation.reportError("POWER ONONONONONON", false);
					wheelright.set(-0.5 * wheelDir);
					wheelleft.set(0.5 * wheelDir);
					Timer.delay(0.09); // wait for a motor update time

					this.intakeState = true;
				}
			}

			boolean putEngaged = false;
			if (xboxStick.getRawAxis(putUpxbox) > -0.2
					&& xboxStick.getRawAxis(putUpxbox) < 0.2) {
				put.set(0.0);
				putEngaged = false;
			} else {
				latch = 0.0;
				put.set(xboxStick.getRawAxis(putUpxbox) * .5);
				putEngaged = true;
			}

			if (putEngaged == false) {
				if (xboxStick.getRawButton(putinclatchxbox)) {
					latch = latch + latchincrement;
				} else if (xboxStick.getRawButton(putdeclatchxbox) && latch > 0) {
					latch = latch - latchincrement;
				}
				if (latch > 0) {
					put.set(latch * -1);
				} else {
					put.set(0.0);
				}
			}

			myRobot.tankDrive(logiTech.getRawAxis(DRIVELEFT) * -1,
					logiTech.getRawAxis(DRIVERIGHT) * -1);

			Timer.delay(0.005); // wait for a motor update time
		}
	}
}
