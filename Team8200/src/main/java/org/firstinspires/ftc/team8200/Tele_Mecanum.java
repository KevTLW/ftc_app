package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum", group="TeleOp")
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
            /* Player 1 */

            // Drive speeds (Regular axis)
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad1.right_stick_y;
            robot.frontLeftDrive.setPower(leftSpeed * .75);
            robot.backLeftDrive.setPower(leftSpeed * .75);
            robot.frontRightDrive.setPower(rightSpeed * .75);
            robot.backRightDrive.setPower(rightSpeed * .75);
            
            // Drive speeds (Strafing)
            /* TODO: implement the strafing */
            
            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
