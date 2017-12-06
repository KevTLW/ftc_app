package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    // Universal OpMode Properties
    public DcMotor elevator,
                   frontLeftDrive, frontRightDrive,
                   backLeftDrive, backRightDrive,
                   harvesterLeftMotor, harvesterRightMotor;

    public Servo arm,
                 harvesterLeftServo, harvesterRightServo,
                 holdTopLeft, holdTopRight,
                 holdBottomLeft, holdBottomRight,
                 liftLeft, liftRight;

    // Local OpMode Properties
    HardwareMap hwMap;

    // Constructor
    public Hardware() {}

    // Initialize Hardware Interfaces
    public void init(HardwareMap hardwareMap) {
        // Reference the hardware map
        hwMap = hardwareMap;

        // Names for Hardware Configuration
//        elevator = hwMap.get(DcMotor.class, "elevator");
        frontLeftDrive = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = hwMap.get(DcMotor.class, "backRightDrive");
//        harvesterLeftMotor = hwMap.get(DcMotor.class, "harvesterLeftMotor");
//        harvesterRightMotor = hwMap.get(DcMotor.class, "harvesterRightMotor");

        arm = hwMap.get(Servo.class, "arm");
//        harvesterLeftServo = hwMap.get(Servo.class, "harvesterServoLeft");
//        harvesterRightServo = hwMap.get(Servo.class, "harvesterServoRight");
//        holdTopLeft = hwMap.get(Servo.class, "holdTopLeft");
//        holdTopRight = hwMap.get(Servo.class, "holdTopRight");
        holdBottomLeft = hwMap.get(Servo.class, "holdBottomLeft");
        holdBottomRight = hwMap.get(Servo.class, "holdBottomRight");
//        liftLeft = hwMap.get(Servo.class, "liftLeft");
//        liftRight = hwMap.get(Servo.class, "liftRight");

        // Motor Direction
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set motors and servos to initial position
//        elevator.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
//        harvesterLeftMotor.setPower(0);
//        harvesterRightMotor.setPower(0);

        arm.setPosition(.8);
//        harvesterLeftServo.setPosition(0);
//        harvesterRightServo.setPosition(0);
//        holdTopLeft.setPosition(0);
//        holdTopRight.setPosition(0);
        holdBottomLeft.setPosition(.6);
        holdBottomRight.setPosition(.35);
//        liftLeft.setPosition(0);
//        liftRight.setPosition(0);

        // Enable encoders
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
