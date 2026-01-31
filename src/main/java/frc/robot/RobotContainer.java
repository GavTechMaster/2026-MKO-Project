package frc.robot;

<<<<<<< HEAD
=======
import java.time.Instant;

>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Robot.RobotRunType;
<<<<<<< HEAD
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.drive.DrivetrainIO;
import frc.robot.subsystems.drive.DrivetrainReal;
=======
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.GamePieceManipulator;
import frc.robot.subsystems.Drivetrain;
>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
<<<<<<< HEAD
=======

    public static Boolean groundIntakeState = false;

>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)
    /* Controllers */
    private final CommandXboxController driver = new CommandXboxController(Constants.driverID);
    private final CommandXboxController operator = new CommandXboxController(Constants.operatorID);

    // Initialize AutoChooser Sendable
    private final SendableChooser<String> autoChooser = new SendableChooser<>();

    /* Subsystems */
    private Drivetrain drivetrain;
<<<<<<< HEAD
=======
    private GamePieceManipulator gamePieceManipulator;
    private Arm arm;
>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(RobotRunType runtimeType) {
        SmartDashboard.putData("Choose Auto: ", autoChooser);
        autoChooser.setDefaultOption("Wait 1 Second", "wait");
        switch (runtimeType) {
            case kReal:
<<<<<<< HEAD
                drivetrain = new Drivetrain(new DrivetrainReal());
=======
                
>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)
                break;
            case kSimulation:
                // drivetrain = new Drivetrain(new DrivetrainSim() {});
                break;
            default:
<<<<<<< HEAD
                drivetrain = new Drivetrain(new DrivetrainIO() {});
=======
                
>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)
        }
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
<<<<<<< HEAD
    private void configureButtonBindings() {}
=======
    private void configureButtonBindings() {
        // Setting up the arm to move based on the position of the right stick
        arm.setDefaultCommand(arm.armCMD(driver));

        // Setting up the drivetrain to move based on the position of the left stick
        drivetrain.setDefaultCommand(drivetrain.robotDrive(driver));
        
        // Tube Intake
        driver.a().whileTrue(new InstantCommand(() -> { 
            if (frc.robot.Robot.teleopMode) {
                gamePieceManipulator.tubeIntake();
            }
        }));

        // Tube Outake
        driver.b().whileTrue(new InstantCommand(() -> {
            if (frc.robot.Robot.teleopMode){
                gamePieceManipulator.tubeOutake();
            }
        }));

        // Ground Intake
        driver.y().onTrue(new InstantCommand(() -> {
            if ((gamePieceManipulator.axisAbsoluteEncoder.getPosition() != (0.25))
                && groundIntakeState && frc.robot.Robot.teleopMode) {
                gamePieceManipulator.changeAxis(-45);
            }
        }));

        // Ground Outake
        driver.x().onTrue(new InstantCommand(() -> {
            if (gamePieceManipulator.axisAbsoluteEncoder.getPosition() == (0.25)
                && groundIntakeState && frc.robot.Robot.teleopMode) {
                gamePieceManipulator.changeAxis(90);
            }
        }));

        // Changing intake/outake state to groundIntake
        driver.leftTrigger().onTrue(new InstantCommand(() -> {
            if (groundIntakeState != true && frc.robot.Robot.teleopMode){
                arm.armMove(25/360); // Moving arm to 25 degrees
                gamePieceManipulator.changeAxis(-45);
                groundIntakeState = true;
            }
        }));

        // Changing intake/outake state to tubeIntake
        driver.rightTrigger().onTrue(new InstantCommand(() -> {
            if (groundIntakeState && frc.robot.Robot.teleopMode) {
                arm.armMove(45/360); // Moving arm to 45 degrees
                gamePieceManipulator.changeAxis(0);
                groundIntakeState = false;
            }
        }));

        // Setting the wrist to move left and right
        driver.povLeft().onTrue(new InstantCommand(() -> {
            if (frc.robot.Robot.teleopMode) {
                arm.armWristMove(arm.armWristEncoder.getPosition().getValueAsDouble() + 1/360);
            }
        }));

        driver.povRight().onTrue(new InstantCommand(() -> {
            if (frc.robot.Robot.teleopMode) {
                arm.armWristMove(arm.armWristEncoder.getPosition().getValueAsDouble() - 1/360);
            }
        }));
    }
>>>>>>> 5efbc34 (Setted up the Subsystems for Vision, Game Piece Manipulator, Arm, and Drivetrain.)

    /**
     * Gets the user's selected autonomous command.
     *
     * @return Returns autonomous command selected.
     */
    public Command getAutonomousCommand() {
        Command autocommand;
        String stuff = autoChooser.getSelected();
        switch (stuff) {
            case "wait":
                autocommand = new WaitCommand(1.0);
                break;
            default:
                autocommand = new InstantCommand();
        }
        return autocommand;
    }
}
