/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DrivetrainDriveCommand;

//a subsystem is a part of a robot (ex: drivetrain, lift, rollers, etc.)

//all of our subsystems must inherit from the Subsystem class
//Documentation for subsystem class:
//https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Subsystem.html

public class Drivetrain extends Subsystem {

  private WPI_TalonSRX leftFrontMotor;
  private WPI_TalonSRX leftRearMotor;
  private WPI_TalonSRX rightFrontMotor;
  private WPI_TalonSRX rightRearMotor;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive differentialDrive;

  private Encoder encoder;

  //this is a constructor
  //it is called when a new instance of this class is created
  public Drivetrain() {
    leftFrontMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_FRONT_MOTOR_PORT);
    leftRearMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_REAR_MOTOR_PORT);
    rightFrontMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_RIGHT_FRONT_MOTOR_PORT);
    rightRearMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_RIGHT_REAR_MOTOR_PORT);

    leftFrontMotor.setInverted(true);
    leftRearMotor.setInverted(true);
    rightFrontMotor.setInverted(true);
    rightRearMotor.setInverted(true);

    leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
    rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);

    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

    encoder = new Encoder(-1, -1);
    
    encoder.setDistancePerPulse(6*Math.PI/1024);
    
  }

  //each subsystem has its own methods for controlling it

  @Override
  public void initDefaultCommand() { //this command is always run on the drivetrain
    setDefaultCommand(new DrivetrainDriveCommand());
  }

  //this tankDrive method takes in two inputs for speed
  //it then passes on these inputs to the tankDrive method for differentialDrive
  public void tankDrive(double leftSpeed, double rightSpeed) {
    differentialDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void arcadeDrive(double speed, double turn) {
    differentialDrive.arcadeDrive(speed, turn);
  }

  public int getEncoderTicks() {
    return encoder.get();
  }

}
