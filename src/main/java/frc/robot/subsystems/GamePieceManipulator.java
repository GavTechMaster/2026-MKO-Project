package frc.robot.subsystems;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GamePieceManipulator extends SubsystemBase {
    /*
     * The first and second intake motors will be on the opposite sides of each other, while also
     * holding rolling pins to provide intake and outtake
     */

    // angle rotations = {rotations}/360
    private final double minPositionRotations = -0.25;
    private final double maxPositionRotations = 0.25;

    // Neo550 Motor
    private final SparkMax axisMotor = new SparkMax(1, MotorType.kBrushless);
    private final SparkClosedLoopController axisController = axisMotor.getClosedLoopController();
    public final SparkAbsoluteEncoder axisAbsoluteEncoder = axisMotor.getAbsoluteEncoder();
    private final SparkMaxConfig axisConfig = new SparkMaxConfig();

    // Neo 2
    private final SparkMax intakeMotorLeft = new SparkMax(2, MotorType.kBrushless);

    // Neo Vortex
    private final SparkMax intakeMotorRight = new SparkMax(3, MotorType.kBrushless);
    private final SparkMaxConfig motorRightConfig = new SparkMaxConfig();

    // Motor initialization
    private GamePieceManipulator() {
        axisConfig.closedLoop.p(0.5).i(0.0).d(0.05).outputRange(-0.5, 0.5);
        axisConfig.softLimit.forwardSoftLimitEnabled(true).forwardSoftLimit(maxPositionRotations)
            .reverseSoftLimitEnabled(true).reverseSoftLimit(minPositionRotations);
        axisConfig.absoluteEncoder.positionConversionFactor(360.0).zeroOffset(0.0)
            .velocityConversionFactor(360.0 / 60);
        axisMotor.configure(axisConfig, ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);
        motorRightConfig.inverted(true);
        intakeMotorRight.configure(motorRightConfig, ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);
    }

    /*
     * Changes the axis of the tube intake system. Also can be used to setup the ground intake
     * system.
     */
    public Command changeAxis(double position) {
        return Commands.run(() -> axisController.setSetpoint(position, ControlType.kPosition));
    }

    public void runMotors(double power){
        intakeMotorLeft.set(power);
        intakeMotorRight.set(power);
    }

    // Intake system specifically for two tubes.
    public Command tubeIntake() {
        return Commands.runEnd(() -> runMotors(1.0), () -> runMotors(0));
    }

    // Outake system specifically for two tubes.
    public Command tubeOutake() {
        return Commands.runEnd(() -> runMotors(-1.0), () -> runMotors(0));
    }
}
