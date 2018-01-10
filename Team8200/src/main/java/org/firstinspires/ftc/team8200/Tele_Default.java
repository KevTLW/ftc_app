package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Default", group="TeleOp")
public class Tele_Default extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();


    double topServo = 0, bottomServo = 0;

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
            /* Player 1 */

            // Drive speeds
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad1.right_stick_y;
            robot.frontLeftDrive.setPower(leftSpeed * .75);
            robot.backLeftDrive.setPower(leftSpeed * .75);
            robot.frontRightDrive.setPower(rightSpeed * .75);
            robot.backRightDrive.setPower(rightSpeed * .75);

            if (robot.liftLeft.getPosition() == 1 || robot.liftRight.getPosition() == 0) {
                robot.frontLeftDrive.setPower(leftSpeed / 5);
                robot.backLeftDrive.setPower(leftSpeed / 5);
                robot.frontRightDrive.setPower(rightSpeed / 5);
                robot.backRightDrive.setPower(rightSpeed / 5);
            }

            // Balance on board
            if (gamepad1.dpad_down) {
                robot.liftLeft.setPosition(1);
                robot.liftRight.setPosition(0);
            } else if (gamepad1.dpad_up) {
                robot.liftLeft.setPosition(0);
                robot.liftRight.setPosition(1);
            }

            // Harvester
            if (gamepad1.left_bumper) {
                robot.harvesterLeftMotor.setPower(1);
                robot.harvesterRightMotor.setPower(1);
            } else if (gamepad1.right_bumper) {
                robot.harvesterLeftMotor.setPower(-1);
                robot.harvesterRightMotor.setPower(-1);
            } else if (gamepad1.x) {
                robot.harvesterLeftMotor.setPower(1);
                robot.harvesterRightMotor.setPower(-1);
            } else if (gamepad1.y) {
                robot.harvesterLeftMotor.setPower(-1);
                robot.harvesterRightMotor.setPower(1);
            } else if (gamepad1.a) {
                robot.harvesterLeftMotor.setPower(0);
                robot.harvesterRightMotor.setPower(0);
            }

            /* Player 2 */

            // Elevator
            double elevatorSpeed = 0;
            if (gamepad2.right_trigger > 0.5) {
                elevatorSpeed = .4;
            } else if (gamepad2.left_trigger > 0.5) {
                elevatorSpeed = -.2;
            }
            robot.elevator.setPower(elevatorSpeed);

            // Glyph Holder
            if (gamepad2.left_bumper) {
                robot.holdLeft.setPosition(.59);
                robot.holdRight.setPosition(.41);
            } else if (gamepad2.right_bumper) {
                robot.holdLeft.setPosition(.24);
                robot.holdRight.setPosition(.74);
            }

            // Test Arm Servos

            if (gamepad2.a) {
                topServo += .025;
            } else if (gamepad2.b) {
                topServo -= .025;
            } else if (gamepad2.x) {
                bottomServo += .025;
            } else if (gamepad2.y) {
                bottomServo -= .025;
            }
            robot.armTopRight.setPosition(topServo);
            robot.armBottomRight.setPosition(bottomServo);
            telemetry.addData("top:", topServo);
            telemetry.addData("bottom:", bottomServo);
            telemetry.update();

            if (gamepad2.dpad_up) {
                robot.harvesterLeftServo.setPosition(1);
                robot.harvesterRightServo.setPosition(0);
            } else if (gamepad2.dpad_down) {
                robot.harvesterLeftServo.setPosition(0);
                robot.harvesterRightServo.setPosition(1);
            }
            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
