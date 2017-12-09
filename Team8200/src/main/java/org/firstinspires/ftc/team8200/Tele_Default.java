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

            /* Player One */

            // Drive speeds
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad1.right_stick_y;
            robot.frontLeftDrive.setPower(leftSpeed);
            robot.backLeftDrive.setPower(leftSpeed);
            robot.frontRightDrive.setPower(rightSpeed);
            robot.backRightDrive.setPower(rightSpeed);

            // Balance on board
            if (gamepad1.dpad_down) {
                robot.liftLeft.setPosition(1);
                robot.liftRight.setPosition(1);
            }

            /* Player Two */

            // Glyph Holder
            if (gamepad2.left_bumper) {
                robot.holdLeft.setPosition(.59);
                robot.holdRight.setPosition(.41);
            } else if (gamepad2.right_bumper) {
                robot.holdLeft.setPosition(.24);
                robot.holdRight.setPosition(.74);
            }

            // Elevator
            double elevatorSpeed = 0;
            if (gamepad2.right_trigger > 0.5) {
                elevatorSpeed = .2;
            } else if (gamepad2.left_trigger > 0.5) {
                elevatorSpeed = -.2;
            }
            robot.elevator.setPower(elevatorSpeed);

            // Harvester servo
            if (gamepad2.y) {
                robot.harvesterLeftServo.setPosition(1);
                robot.harvesterRightServo.setPosition(1);
            }

            //Harvester motor
            if (gamepad2.x) {
                robot.harvesterLeftMotor.setPower(-1);
                robot.harvesterRightMotor.setPower(1);
            }


            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
