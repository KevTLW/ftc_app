package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum", group="TeleOp")
public class Tele_Mecanum extends LinearOpMode {
    // Import objects used in robot
    MecanumHardware robot = new MecanumHardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Drive variables
        double leftSpeed, rightSpeed;

        boolean leftStrafe, rightStrafe;

        double front = 0;

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run until "STOP" is pressed
        while (opModeIsActive()) {
            /* Player 1 */

            // Drive speeds (Regular axis)
            leftSpeed = gamepad1.left_stick_y;
            rightSpeed = gamepad1.right_stick_y;
            robot.frontLeftDrive.setPower(leftSpeed * .75);
            robot.frontRightDrive.setPower(rightSpeed * .75);
            robot.backLeftDrive.setPower(leftSpeed * .75);
            robot.backRightDrive.setPower(rightSpeed * .75);

            // Drive speeds (Strafing)
            leftStrafe = gamepad1.dpad_left;
            rightStrafe = gamepad1.dpad_right;
            if (leftStrafe) {
                robot.frontLeftDrive.setPower(.75);
                robot.frontRightDrive.setPower(-.75);
                robot.backLeftDrive.setPower(-.75);
                robot.backRightDrive.setPower(.75);
            }
            if (rightStrafe) {
                robot.frontLeftDrive.setPower(-.75);
                robot.frontRightDrive.setPower(.75);
                robot.backLeftDrive.setPower(.75);
                robot.backRightDrive.setPower(-.75);
            }

            // Arm positioning
            robot.arm.setPosition(.8);

            /* Player 2 */

            // Harvester
            if (gamepad2.left_bumper) {
                robot.harvesterLeft.setPower(-.25);
                robot.harvesterRight.setPower(-.25);
            } else if (gamepad2.right_bumper) {
                robot.harvesterLeft.setPower(.25);
                robot.harvesterRight.setPower(.25);
            } else {
                robot.harvesterLeft.setPower(0);
                robot.harvesterRight.setPower(0);
            }

            // Flip structures
            if (gamepad2.y) {
                robot.backLeftStructure.setPosition(-1);
                robot.backRightStructure.setPosition(1);
            } else if (gamepad2.a) {
                robot.backLeftStructure.setPosition(1);
                robot.backRightStructure.setPosition(-1);
            } else if (gamepad2.b) {
                front += .025;
//                robot.frontStructure.setPosition(0);
            } else if (gamepad2.x) {
                front -= .025;
            }

            robot.frontStructure.setPosition(front);

            telemetry.addData("F", front);
            telemetry.update();

            sleep(40);
        }
    }
}