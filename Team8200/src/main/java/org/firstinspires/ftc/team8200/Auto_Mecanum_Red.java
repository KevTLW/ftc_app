package org.firstinspires.ftc.team8200;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Red Mecanum", group = "Autonomous")
public class Auto_Mecanum_Red extends LinearOpMode {
    // Import objects used in robot
    MecanumHardware robot = new MecanumHardware();
    private ElapsedTime runtime = new ElapsedTime();
    ColorSensor colorSensor;

    // Static variables for general use
    static final double SPEED = -.5;

    // Static variables for encoders
    static final double PULSES_PER_REVOLUTION = 280;
    static final double COUNTS_PER_INCH = PULSES_PER_REVOLUTION / Math.PI;
    static final double CIRCUMFERENCE = 92.5; //TODO

    // Variables for sensors
    private String color = "";

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Names for Hardware Configuration
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor");

        // Reset encoders
        reset();

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run methods in sequence
        rotate(360); //TODO
        /* TODO STRATEGY */
//        hitGem();
//        move(-28);
//        rotate(90);
//        dropGlyphs();
//        strafe(-8);
//        move(32);
//        collectGlyphs();
//        move(-32);
//        dropGlyphs();
//        strafe(-8);
//        move(32);
//        collectGlyphs();
//        move(-32);
//        dropGlyphs();
    }

    public void hitGem() {
        //Arm out TODO
        readColor();
        if (color.equals("blue")) {
            move(-4);
        } else if (color.equals("red")) {
            move(4);
        } else {
            //Arm in TODO
            return;
        }
        //Arm in TODO
        if (color.equals("blue")) {
            move(4);
        } else if (color.equals("red")) {
            move(-4);
        }
    }

    // Move inputted amount of inches
    public void move(double inches) {
        int frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget;

        // Set new position
        frontLeftTarget = robot.frontLeftDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        frontRightTarget = robot.frontRightDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        backLeftTarget = robot.backLeftDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        backRightTarget = robot.backRightDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
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
        reset();
    }

    // Rotate inputted amount of degrees TODO
    public void rotate(double degrees) {
        reset();
        double arc = degrees / 360.0;
        double rotateInches = CIRCUMFERENCE * arc;

        int frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget;

        // Set new position
        frontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(rotateInches * COUNTS_PER_INCH);
        frontRightTarget = robot.frontRightDrive.getCurrentPosition() - (int)(rotateInches * COUNTS_PER_INCH);
        backLeftTarget = robot.backLeftDrive.getCurrentPosition() + (int)(rotateInches * COUNTS_PER_INCH);
        backRightTarget = robot.backRightDrive.getCurrentPosition() - (int)(rotateInches * COUNTS_PER_INCH);
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
        reset();
    }

    // Strafe inputted amount of inches
    public void strafe(double inches) {
        int frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget;

        // Set new position
        frontLeftTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        frontRightTarget = robot.frontRightDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        backLeftTarget = robot.backLeftDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
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
        reset();
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

    // Run harvester
    public void collectGlyphs() {
        robot.frontStructure.setPosition(0);
        runtime.reset();
        while (runtime.seconds() < 2) {
            robot.harvesterLeft.setPower(-.5);
            robot.harvesterRight.setPower(-.5);
        }
    }

    // Flip back structure
    public void dropGlyphs() {
        robot.backLeftStructure.setPosition(-1);
        robot.backRightStructure.setPosition(1);

        sleep(1000);

        robot.backLeftStructure.setPosition(1);
        robot.backRightStructure.setPosition(-1);
    }

    // Return color detected by the sensor
    public void readColor() {
        // Store HSV Values
        float hsvValues[] = {0F, 0F, 0F};

        // Scale to convert RGB to HSV
        final double SCALE_FACTOR = 255;

        runtime.reset();
        while (opModeIsActive()) {
            // Convert from RGB to HSV
            Color.RGBToHSV((int)(colorSensor.red() * SCALE_FACTOR), (int)(colorSensor.green() * SCALE_FACTOR), (int)(colorSensor.blue() * SCALE_FACTOR), hsvValues);

            // Show values at Driver Station
            telemetry.addData("Alpha", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.update();

            // Considered adding a timer just to confirm that the color is accurate
            if (colorSensor.red() > colorSensor.blue()) { // Condition for RED
                if (runtime.seconds() > 1) {
                    color = "red";
                }
            } else if (colorSensor.blue() > colorSensor.red()) { // Condition for BLUE
                if (runtime.seconds() > 1) {
                    color = "blue";
                }
            }
        }
    }
}