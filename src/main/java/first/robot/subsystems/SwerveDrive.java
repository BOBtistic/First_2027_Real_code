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
import com.ctre.phoenix6.configs.CANcoderConfiguration;
//import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SwerveDrive extends SubsystemBase {
  // pulls aligned outside to use in robot container
  private boolean aligned;

  // Front Left module
  private final SparkFlex FLSteer = new SparkFlex(CANPort.CAN_D0, 1, MotorType.kBrushless);
  private final SparkFlex FLDrive = new SparkFlex(CANPort.CAN_D0, 2, MotorType.kBrushless);
  private final CANcoder FLcoder = new CANcoder(3, new CANBus(CANPort.CAN_D0));
  CANcoderConfiguration FLcoderConfig = new CANcoderConfiguration();

  // Front Right Module
  private final SparkFlex FRSteer = new SparkFlex(CANPort.CAN_D0, 4, MotorType.kBrushless);
  private final SparkFlex FRDrive = new SparkFlex(CANPort.CAN_D0, 5, MotorType.kBrushless);
  private final CANcoder FRcoder = new CANcoder(6, new CANBus(CANPort.CAN_D0));
  CANcoderConfiguration FRcoderConfig = new CANcoderConfiguration();

  // Back Left Module
  private final SparkFlex BLSteer = new SparkFlex(CANPort.CAN_D0, 7, MotorType.kBrushless);
  private final SparkFlex BLDrive = new SparkFlex(CANPort.CAN_D0, 8, MotorType.kBrushless);
  private final CANcoder BLcoder = new CANcoder(9, new CANBus(CANPort.CAN_D0));
  CANcoderConfiguration BLcoderConfig = new CANcoderConfiguration();

  // Back Right Module
  private final SparkFlex BRSteer = new SparkFlex(CANPort.CAN_D0, 10, MotorType.kBrushless);
  private final SparkFlex BRDrive = new SparkFlex(CANPort.CAN_D0, 11, MotorType.kBrushless);
  private final CANcoder BRcoder = new CANcoder(12, new CANBus(CANPort.CAN_D0));
  CANcoderConfiguration BRcoderConfig = new CANcoderConfiguration();

  public void CANCoderconfig() {
    // Set these when the robot's wheels are facing forward, so that the robot knows
    // what "forward" is
    FLcoderConfig.MagnetSensor.MagnetOffset = 0;
    FLcoder.getConfigurator().apply(FLcoderConfig);

    FRcoderConfig.MagnetSensor.MagnetOffset = 0;
    FRcoder.getConfigurator().apply(FRcoderConfig);

    BLcoderConfig.MagnetSensor.MagnetOffset = 0;
    BLcoder.getConfigurator().apply(BLcoderConfig);

    BRcoderConfig.MagnetSensor.MagnetOffset = 0;
    BRcoder.getConfigurator().apply(BRcoderConfig);

  }

  public void SwerveModule(int Module, int SCid, boolean aligned) {
    // using the module number to determine which motors to use.
    SparkFlex Steer = FLSteer;
    SparkFlex Drive = FLDrive;
    if (Module == 1) {
      Steer = FLSteer;
      Drive = FLDrive;
    } else if (Module == 2) {
      Steer = FRSteer;
      Drive = FRDrive;
    } else if (Module == 3) {
      Steer = BLSteer;
      Drive = BLDrive;
    } else if (Module == 4) {
      Steer = BRSteer;
      Drive = BRDrive;
    }

    // SparkAbsoluteEncoder SpeedEncoder = Drive.getAbsoluteEncoder();

    // Getting encoder values
    var Direction = FLcoder.getAbsolutePosition().getValueAsDouble();
    // var speed = SpeedEncoder.getVelocity();

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
