package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Arm extends SubsystemBase {
    // Kraken x60 motor
    // Arm base will be upside-down, while arm wrist base will be rightside-up
    private final TalonFX armBase = new TalonFX(1);
    private final TalonFXConfiguration armBaseConfiguration = new TalonFXConfiguration();
    public final CANcoder armBaseEncoder = new CANcoder(1);
    private final CANcoderConfiguration armBaseEncoderConfig = new CANcoderConfiguration();

    // Kraken x60 motor
    private final TalonFX armWristBase = new TalonFX(2);
    private final TalonFXConfiguration armWristBaseConfiguration = new TalonFXConfiguration();
    public final CANcoder armWristBaseEncoder = new CANcoder(2);
    private final CANcoderConfiguration armWristBaseEncoderConfig = new CANcoderConfiguration();

    // Falcon 500 motor
    private final TalonFX armWrist = new TalonFX(3);
    private final TalonFXConfiguration armWristConfiguration = new TalonFXConfiguration();
    public final CANcoder armWristEncoder = new CANcoder(3);
    private final CANcoderConfiguration armWristEncoderConfig = new CANcoderConfiguration();

    private Arm() {
        armBaseEncoderConfig.MagnetSensor.SensorDirection =
            SensorDirectionValue.CounterClockwise_Positive;
        armBaseEncoderConfig.MagnetSensor.MagnetOffset = 0;
        armBaseEncoder.getConfigurator().apply(armBaseEncoderConfig);

        armWristBaseEncoderConfig.MagnetSensor.SensorDirection =
            SensorDirectionValue.Clockwise_Positive;
        armWristBaseEncoderConfig.MagnetSensor.MagnetOffset = 0;
        armWristBaseEncoder.getConfigurator().apply(armWristBaseEncoderConfig);

        armBaseConfiguration.Feedback.FeedbackRemoteSensorID = armBaseEncoder.getDeviceID();
        armBaseConfiguration.Feedback.FeedbackSensorSource =
            FeedbackSensorSourceValue.RemoteCANcoder;
        armBaseConfiguration.Feedback.SensorToMechanismRatio = 1.0;
        armBaseConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        armBaseConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0.125;
        armBaseConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        armBaseConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -0.125;
        armBaseConfiguration.Slot0.kP = 2.0;
        armBaseConfiguration.Slot0.kI = 0.0;
        armBaseConfiguration.Slot0.kD = 0.1;
        armBase.getConfigurator().apply(armBaseConfiguration);

        armWristBaseConfiguration.Feedback.FeedbackRemoteSensorID =
            armWristBaseEncoder.getDeviceID();
        armWristBaseConfiguration.Feedback.FeedbackSensorSource =
            FeedbackSensorSourceValue.RemoteCANcoder;
        armWristBaseConfiguration.Feedback.SensorToMechanismRatio = 1.0;
        armWristBaseConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        armWristBaseConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0.25;
        armWristBaseConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        armWristBaseConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -0.25;
        armWristBaseConfiguration.Slot0.kP = 2.0;
        armWristBaseConfiguration.Slot0.kI = 0.0;
        armWristBaseConfiguration.Slot0.kD = 0.1;
        armWristBase.getConfigurator().apply(armWristBaseConfiguration);

        armWristConfiguration.Slot0.kP = 0.5;
        armWristConfiguration.Slot0.kI = 0.0;
        armWristConfiguration.Slot0.kD = 0.05;
        armWrist.getConfigurator().apply(armWristConfiguration);

        armWristEncoderConfig.MagnetSensor.SensorDirection =
            SensorDirectionValue.CounterClockwise_Positive;
        armWristEncoderConfig.MagnetSensor.MagnetOffset = 0;
        armWristEncoder.getConfigurator().apply(armBaseEncoderConfig);
    }

    public void armMove(double position) {
        PositionVoltage armBasePositionRequest = new PositionVoltage(position).withSlot(0);
        PositionVoltage armWristBasePositionRequest = new PositionVoltage(position).withSlot(0);
        armBase.setControl(armBasePositionRequest);
        armWristBase.setControl(armWristBasePositionRequest);
    }

    public Command armBaseMove(double position) {
        PositionVoltage armBasePositionRequest = new PositionVoltage(position).withSlot(0);
        return Commands.run(() -> armBase.setControl(armBasePositionRequest));
    }

    public Command armWristBaseMove(double position) {
        PositionVoltage armWristBasePositionRequest = new PositionVoltage(position).withSlot(0);
        return Commands.run(() -> armWristBase.setControl(armWristBasePositionRequest));
    }

    public Command armWristMove(double position) {
        PositionVoltage armWristPositionRequest = new PositionVoltage(position).withSlot(0);
        return Commands.run(() -> armWrist.setControl(armWristPositionRequest));
    }

    public Command armCMD(CommandXboxController controller) {
        if (frc.robot.Robot.teleopMode) {
            return Commands.run(() -> armMove(controller.getRightX() / 360.0));
        }
    }
}