package org.firstinspires.ftc.team8200;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Red Mecanum", group = "Autonomous")
public class Auto_Mecanum_Red extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    // Static variables for general use
    static final double SPEED = -.5;

    // Static variables for encoders
    static final double COUNTS_PER_MOTOR_REV = 28; // Source: NeveRest 40 Specifications Sheet
    static final double DRIVE_GEAR_REDUCTION = 40;
    static final double WHEEL_DIAMETER_INCHES = 4; // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    /* Possible implementation
    static final double PULSES_PER_REVOLUTION = 280
    static final double COUNTS_PER_INCH = PULSES_PER_REVOLUTION / (WHEEL_DIAMETER_INCHES * Math.PI); // Find proper denominator
     */

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Reset encoders
        reset();

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run methods in sequence
        /*
            Get gem points
            (Use vuforia)
            Go straight
            Strafe until right column
            Go straight
            Drop cube and retract
         */
    }

    public void hitGem() {
        //Arm out
        //Use logic
    }

    public void move(double inches) {
        int frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget;

        // Set new position
        frontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        frontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        backLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        backRightTarget = robot.backRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        robot.frontLeftDrive.setTargetPosition(frontLeftTarget);
        robot.frontRightDrive.setTargetPosition(frontRightTarget);
        robot.backLeftDrive.setTargetPosition(backLeftTarget);
        robot.backRightDrive.setTargetPosition(backRightTarget);

        // Turn On RUN_TO_POSITION
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Move
        robot.frontLeftDrive.setPower(SPEED);
        robot.frontRightDrive.setPower(SPEED);
        robot.backLeftDrive.setPower(SPEED);
        robot.backRightDrive.setPower(SPEED);

        // Keep in motion (Show how much is still left)
        while (opModeIsActive() && robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() && robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy()) {
            telemetry.addData("Remaining (FL)", frontLeftTarget - robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("Remaining (FR)", frontRightTarget - robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("Remaining (BL)", backLeftTarget - robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("Remaining (BR)", backRightTarget - robot.backRightDrive.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafe(double inches) {
        int frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget;

        // Set new position
        frontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        frontRightTarget = robot.frontRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        backLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        backRightTarget = robot.backRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        robot.frontLeftDrive.setTargetPosition(frontLeftTarget);
        robot.frontRightDrive.setTargetPosition(frontRightTarget);
        robot.backLeftDrive.setTargetPosition(backLeftTarget);
        robot.backRightDrive.setTargetPosition(backRightTarget);

        // Turn On RUN_TO_POSITION
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Move (Might have TODO invert)
        robot.frontLeftDrive.setPower(-SPEED);
        robot.frontRightDrive.setPower(SPEED);
        robot.backLeftDrive.setPower(SPEED);
        robot.backRightDrive.setPower(-SPEED);

        // Keep in motion (Show how much is still left)
        while (opModeIsActive() && robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() && robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy()) {
            telemetry.addData("Remaining (FL)", frontLeftTarget - robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("Remaining (FR)", frontRightTarget - robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("Remaining (BL)", backLeftTarget - robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("Remaining (BR)", backRightTarget - robot.backRightDrive.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Reset encoders
    public void reset() {
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}