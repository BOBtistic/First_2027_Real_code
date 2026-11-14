// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.subsystems;

import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
import org.wpilib.hardware.bus.CANPort;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.CANBus;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class SwerveDrive extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public SwerveDrive(int Sid, int Did, int SCid ) {
    SparkFlex Steer = new SparkFlex(CANPort.CAN_D0, Sid, MotorType.kBrushless);
    SparkFlex Drive = new SparkFlex(CANPort.CAN_D0, Did, MotorType.kBrushless);
    CANcoder cancoder = new CANcoder(SCid, new CANBus(CANPort.CAN_D0));
    SparkAbsoluteEncoder SpeedEncoder = Drive.getAbsoluteEncoder();

    var Direction = cancoder.getAbsolutePosition();
    var speed = SpeedEncoder.getVelocity();

    
  }
 
  
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
