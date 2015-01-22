package org.usfirst.frc.team5493.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it 
 * contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Joystick rightStick;
    private static final int RIGHTDRIVE = 5;
    private static final int LEFTDRIVE = 1;
    public Robot() {
        myRobot = new RobotDrive(1, 2);
        myRobot.setExpiration(0.1);
        leftStick = new Joystick(1);
       // rightStick = new Joystick(1);
        
        
//        getWatchdog().setEnabled(false);
        //myRobot.setInvertedMotor(MotorType.kFrontLeft, true);
    }

    
    /**
     * Runs the motors with tank steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        System.out.println("OperatorControl called");
        while (true && isOperatorControl() && isEnabled()) {
        	myRobot.tankDrive(leftStick.getRawAxis(RIGHTDRIVE), leftStick.getRawAxis(LEFTDRIVE));
            
        	Timer.delay(0.005);		// wait for a motor update time
            System.out.println("in while");
        }
    }

}
