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
        double leftSpeed, rightSpeed,
               harvesterIn, harvesterOut;

        boolean leftStrafe, rightStrafe;

        double arm = 0;

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

            // Arm positioning TODO
            if (gamepad1.y) {
                arm +=.05;
            } else if (gamepad1.a) {
                arm -= .05;
            }

            robot.arm.setPosition(arm);

            telemetry.addData("Arm: ", arm);
            telemetry.update();

            /* Player 2 */

            // Harvester
            harvesterIn = gamepad2.right_trigger;
            harvesterOut = gamepad2.left_trigger;
            if (harvesterIn != 0) {
                robot.harvesterLeft.setPower(harvesterIn * -.5);
                robot.harvesterRight.setPower(harvesterIn * -.5);
            } else if (harvesterOut != 0) {
                robot.harvesterLeft.setPower(harvesterIn * .5);
                robot.harvesterRight.setPower(harvesterIn * .5);
            }

            // Flip structures
            if (gamepad2.y) {
                robot.backLeftStructure.setPosition(-1);
                robot.backRightStructure.setPosition(1);
            } else if (gamepad2.a) {
                robot.backLeftStructure.setPosition(1);
                robot.backRightStructure.setPosition(-1);
            } else if (gamepad2.b) {
                robot.frontStructure.setPosition(0);
            }

            sleep(40);
        }
    }
}