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

@Autonomous(name = "Red", group = "Autonomous")
public class Auto_PlaceCube_Red extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    VuforiaLocalizer vuforia;
    ColorSensor colorSensor;
    private ElapsedTime runtime = new ElapsedTime();

    // Static variables for general use
    static final double SPEED = -.5;

    // Static variables for encoders
    static final double COUNTS_PER_MOTOR_REV = 28; // Source: NeveRest 40 Specifications Sheet
    static final double DRIVE_GEAR_REDUCTION = 40;
    static final double WHEEL_DIAMETER_INCHES = 4; // For figuring circumference
    static final double PI = 3.1415;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);

    // Static variables for turning with encoders
    static final double CIRCUMFERENCE = 54; // Amount of inches that rotate the robot 360 degrees

    // Static variables for sensors
    private String vuMarkPattern = "";
    private String color = "";

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Names for Hardware Configuration
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor");

        // Reset encoders
        stopAndResetEncoders();

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run methods in sequence
        holdGlyph();
        sleep(1000);
        goToCryptobox();
        sleep(1000);
        dropGlyph();
    }

    public void holdGlyph() {
        robot.holdLeft.setPosition(.24);
        robot.holdRight.setPosition(.74);
    }

    // Move forward to Gems
    public void hitGem() {}

    // Go to Cryptobox
    public void goToCryptobox() {
        move(SPEED, 28, 28, 5);
        turn(-90);
//        if (vuMarkPattern.equals("left") || vuMarkPattern.equals("")) {
//            telemetry.addData("Column", "l");
//            move(SPEED, -3, -3, 5);
//        } else if (vuMarkPattern.equals("center")) {
//            telemetry.addData("Column", "c");
//            move(SPEED, 8, 8, 5);
//        } else if (vuMarkPattern.equals("right")) {
//            telemetry.addData("Column", "r");
//            move(SPEED, 12, 12, 5);
//        }
//        telemetry.update();
        move(SPEED, -3, -3, 5);
        turn(90);
    }
    // Drop Glyph
    public void dropGlyph() {
        robot.holdLeft.setPosition(.59);
        robot.holdRight.setPosition(.41);
        move(SPEED, 6, 6, 5);
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
        double arc = degrees / 360.0;
        double turnInches = CIRCUMFERENCE * arc;
        move(SPEED, turnInches, -turnInches, 5);
    }

    public String readVuMark() {
        // Store parameters used to initialize the Vuforia engine
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /* UNCOMMENT THIS IF WANTING TO TEST WITH A VIEW ON SCREEN AND COMMENT LINE ABOVE
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        */

        // Add license key
        parameters.vuforiaLicenseKey = "ARH6A3z/////AAAAGZ4wVweTjU73lDoZeCr/rzwpfNAFaWSGUn4qhsRk/g7XznybiUzddzhqWfAWncGfPY8Q8fqY3lozXjMIMdWiZYPQkmYSmb2NkIry1JizLHG3PvtS5yr3fYCT0Tpia25pg03b3lQeoEVYRQUTnAFXQnO4wSwGOmz2wWWMg0rNDBN6gxnUipEKrLaLajvGvwtmkl/EB0P3Rib3zTgQzJXxgi3nHVV4m06LZ3twCd0l4p4EA7W2Js1V+iR7ue94ObAH4FUfJ0qcOsnlnM+DDq5LdJOAP5HbgldfzsncBeqyRA8O4u4TZ6ABu+4u8T1T/tY1dG7doWkIDjFD/z40F4bEQYGrEo1VuEnsURpIZugF9Ahc";

        // Select the camera the robot will use
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // Start Vuforia engine with the 2 parameters that were taken
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // Load tracking dataset
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        // Activate tracker
        relicTrackables.activate();

        runtime.reset();
        while (opModeIsActive()) {
            // Variable that stores value from what's seen
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                vuMarkPattern = "left";
                return vuMarkPattern;
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                vuMarkPattern = "left";
                return vuMarkPattern;
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                vuMarkPattern = "right";
                return vuMarkPattern;
            } else if (runtime.seconds() > 5) {
                vuMarkPattern = "";
            } else {
                vuMarkPattern = "";
            }
        }
        return vuMarkPattern;
    }

    public String readColor() {
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
            if (colorSensor.red() > colorSensor.green() && colorSensor.red() > colorSensor.blue()) { // Condition for RED
                if (runtime.seconds() > 2) {
                    color = "red";
                    return color;
                }
            } else if (colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) { // Condition for BLUE
                if (runtime.seconds() > 2) {
                    color = "blue";
                    return color;
                }
            }
        }
        return color;
    }
}