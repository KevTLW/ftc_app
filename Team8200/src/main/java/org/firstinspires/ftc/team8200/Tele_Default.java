package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Default", group="TeleOp")
public class Tele_Default extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Drive variables
        double leftSpeed, rightSpeed;

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run until "STOP" is pressed
        while (opModeIsActive()) {
            // Drive speeds
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad1.right_stick_y;
            robot.frontLeftDrive.setPower(leftSpeed);
            robot.backLeftDrive.setPower(leftSpeed);
            robot.frontRightDrive.setPower(rightSpeed);
            robot.backRightDrive.setPower(rightSpeed);

            // Glyph Holder
            if (gamepad1.left_bumper) {
                robot.holdBottomLeft.setPosition(.25);
                robot.holdBottomRight.setPosition(.9);
            } else if (gamepad1.right_bumper) {
                robot.holdBottomLeft.setPosition(.7);
                robot.holdBottomRight.setPosition(.25);
            }

            // Arm
            if (gamepad1.a) {
                robot.arm.setPosition(.8);
            } else if (gamepad1.b) {
                robot.arm.setPosition(0);
            }

            // Balance on board
            if (gamepad1.dpad_down) {
                robot.frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
