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
        double leftSpeed, rightSpeed, strafe;

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
            strafe = gamepad1.left_stick_x;
            robot.frontLeftDrive.setPower(-strafe * .75);
            robot.frontRightDrive.setPower(strafe * .75);
            robot.backLeftDrive.setPower(strafe * .75);
            robot.backRightDrive.setPower(-strafe * .75);

            telemetry.addData("LS", leftSpeed * .75);
            telemetry.addData("RS", rightSpeed * .75);
            telemetry.addData("SS", strafe * .75);
            telemetry.update();
        }
    }
}
