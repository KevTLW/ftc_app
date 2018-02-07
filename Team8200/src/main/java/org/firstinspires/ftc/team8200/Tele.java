package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOp", group="TeleOp")
public class Tele extends LinearOpMode {
    // Import objects used in robot
    Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize Hardware
        robot.init(hardwareMap);

        // Wait for "PLAY" to be pressed
        waitForStart();

        // Run until "STOP" is pressed
        while (opModeIsActive()) {
            /**
             * Use of Mecanum class to program mecanum wheels
             */
            Mecanum.Motion motion = Mecanum.joystickToMotion(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);

            // Convert desired motion to wheel powers, with power clamping.
            Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
            robot.frontLeftDrive.setPower(wheels.frontLeft);
            robot.frontRightDrive.setPower(wheels.frontRight);
            robot.backLeftDrive.setPower(wheels.backLeft);
            robot.backRightDrive.setPower(wheels.backRight);
        }
    }
}

