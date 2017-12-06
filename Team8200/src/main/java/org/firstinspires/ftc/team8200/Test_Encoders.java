package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Encoders Test", group = "Testing")
public class Test_Encoders extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    // Static variables for general use
    static final double DRIVE_SPEED = -.5;
    static final double DRIVE_SLOW_SPEED = .1;
    static final double TURN_SPEED = -.5;

    // Static variables for encoders
    static final double COUNTS_PER_MOTOR_REV = 28.0; // Source: NeveRest 40 Specifications Sheet
    static final double DRIVE_GEAR_REDUCTION = 40.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0; // For figuring circumference
    static final double PI = 3.1415;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);
        
        // Reset encoders
        stopAndResetEncoders();

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run methods in sequence
//        move(DRIVE_SPEED, 24, 24, 10);
//        sleep(5000);
//        turn(90);
//        sleep(5000);
//        move(DRIVE_SPEED, -24, -24,10);
//        sleep(5000);
//        turn(-90);
        turn(-90);
        sleep(1000);
        turn(90);
        sleep(1000);
        turn(-180);
        sleep(1000);
        turn(180);
        sleep(1000);
        turn(-270);
        sleep(1000);
        turn(270);
        sleep(1000);
        turn(-360);
        sleep(1000);
        turn(360);
    }

    // Move with encoders
    public void move(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.frontRightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.frontLeftDrive.setTargetPosition(newLeftTarget);
            robot.frontRightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeftDrive.setPower(Math.abs(speed));
            robot.frontRightDrive.setPower(Math.abs(speed));

            // Keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy())) {
                telemetry.addData("Destination", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Current Position", "Running at %7d :%7d", robot.frontLeftDrive.getCurrentPosition(), robot.frontRightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion
            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }

    // Reset encoders and kill motors
    public void stopAndResetEncoders() {
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50); // Wait 50ms to make sure it fully processes

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Current Position",  "Left: %7d Right:%7d", robot.frontLeftDrive.getCurrentPosition(), robot.frontRightDrive.getCurrentPosition());
        telemetry.update();
    }

    // Rotate with encoders ( > 0 goes CW, < 0 goes CCW )
    public void turn(double degrees) {
        stopAndResetEncoders(); // Reset encoders
        // TODO Modify this
        double circumferenceWheel = 4 * PI;
        double circumferenceRobot = 18 * PI;
        double angle = (circumferenceWheel / circumferenceRobot) * (degrees / 360);

    }
}
