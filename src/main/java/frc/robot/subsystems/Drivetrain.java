package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Drivetrain extends SubsystemBase {
    // Falcon 500
    private final TalonFX leftLeaderMotor = new TalonFX(1);
    private final TalonFX leftFollowerMotor = new TalonFX(2);
    private final TalonFXConfiguration leftMotorConfigs = new TalonFXConfiguration();

    // Kraken x60
    private final TalonFX rightLeaderMotor = new TalonFX(3);
    private final TalonFX rightFollowerMotor = new TalonFX(4);
    private final TalonFXConfiguration rightMotorConfigs = new TalonFXConfiguration();
    private final DifferentialDrive motorDrivetrain = new DifferentialDrive(leftLeaderMotor::set, rightLeaderMotor::set);

    private Drivetrain() {
        rightMotorConfigs.Slot0.kP = 2.0;
        rightMotorConfigs.Slot0.kI = 0.0;
        rightMotorConfigs.Slot0.kD = 1.0;
        rightMotorConfigs.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        rightLeaderMotor.getConfigurator().apply(rightMotorConfigs);
        rightFollowerMotor.getConfigurator().apply(rightMotorConfigs);
        rightFollowerMotor.setControl(new Follower(rightLeaderMotor.getDeviceID(), null));

        leftMotorConfigs.Slot0.kP = 2.0;
        leftMotorConfigs.Slot0.kI = 0.0;
        leftMotorConfigs.Slot0.kD = 1.0;
        leftLeaderMotor.getConfigurator().apply(leftMotorConfigs);
        leftFollowerMotor.getConfigurator().apply(leftMotorConfigs);
        leftFollowerMotor.setControl(new Follower(leftLeaderMotor.getDeviceID(), null));
    }

    public Command robotDrive(CommandXboxController controller) {
        if (frc.robot.Robot.teleopMode){
            return Commands.run(() -> motorDrivetrain.arcadeDrive(controller.getLeftY(), controller.getLeftX()));
        }
    }
    
}