// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.subsystems;

import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.math.util.MathUtil;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.CANBus;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SwerveDrive extends SubsystemBase {
  // pulls aligned outside to use in robot container
  private boolean aligned;

  // Creates a new SwerveDrive.
  public SwerveDrive(int Sid, int Did, int SCid, boolean aligned) {
    this.aligned = aligned;

    // Defining motors and encoders
    SparkFlex Steer = new SparkFlex(CANPort.CAN_D0, Sid, MotorType.kBrushless);
    SparkFlex Drive = new SparkFlex(CANPort.CAN_D0, Did, MotorType.kBrushless);
    CANcoder cancoder = new CANcoder(SCid, new CANBus(CANPort.CAN_D0));
    SparkAbsoluteEncoder SpeedEncoder = Drive.getAbsoluteEncoder();

    // Getting encoder values
    var Direction = cancoder.getAbsolutePosition().getValueAsDouble();
    var speed = SpeedEncoder.getVelocity();

    // adding tolerance
    double error = MathUtil.inputModulus(Direction, -1.0, 1.0);

    // Alighning wheel to face forward on boot
    if (this.aligned) {
      if (Math.abs(error) >= 0.01) {
        if (error > 0) {
          Steer.setThrottle(-.1);
        } else if (error < 0) {
          Steer.setThrottle(.1);
        } else {
          Steer.setThrottle(0);
        }
      } else {
        Steer.setThrottle(0);
        aligned = false;
      }

    }

  }

  public boolean isAligned() {
    return aligned;
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
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
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
