package org.usfirst.frc.team5493.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
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
	Button liftup;
	Button liftdown;
	Relay relay = new Relay(3);
	private static final int DRIVERIGHT = 5;
	private static final int DRIVELEFT = 1;
	private static final int LIFTUP = 3;
	private static final int LIFTDOWN = 2;

	public Robot() {
		myRobot = new RobotDrive(1, 2);
		myRobot.setExpiration(0.1);
		leftStick = new Joystick(1);
		liftup = new JoystickButton(leftStick, LIFTUP);
		liftdown = new JoystickButton(leftStick, LIFTDOWN);

		liftup.toggleWhenPressed(new Command() {

			boolean active = false;
			Direction direction = Direction.kForward;

			@Override
			protected void initialize() {
				if (!active) {
					setTimeout(3);
				}
			}

			@Override
			protected void execute() {
				if (!active) {
					active = true;
					Robot.this.relay.setDirection(direction);
					Robot.this.relay.set(Value.kOn);
				}
			}

			@Override
			protected boolean isFinished() {
				return isTimedOut() && !active;
			}

			@Override
			protected void end() {
				if (active) {
					Robot.this.relay.set(Value.kOff);
					active = false;
				}

			}

			@Override
			protected void interrupted() {
				end();
			}
		});

		liftdown.toggleWhenPressed(new Command() {

			boolean active = false;
			Direction direction = Direction.kReverse;

			@Override
			protected void initialize() {
				if (!active) {
					setTimeout(3);
				}
			}

			@Override
			protected void execute() {
				if (!active) {
					active = true;
					Robot.this.relay.setDirection(direction);
					Robot.this.relay.set(Value.kOn);
				}
			}

			@Override
			protected boolean isFinished() {
				return isTimedOut() && !active;
			}

			@Override
			protected void end() {
				if (active) {
					Robot.this.relay.set(Value.kOff);
					active = false;
				}
			}

			@Override
			protected void interrupted() {
				end();
			}
		});

	}

	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		while (true && isOperatorControl() && isEnabled()) {
			myRobot.tankDrive(leftStick.getRawAxis(DRIVERIGHT),
					leftStick.getRawAxis(DRIVELEFT));

			Timer.delay(0.005); // wait for a motor update time

			// if(liftup.whenPressed();){
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
}
