package org.firstinspires.ftc.team8200;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumHardware {
    // Universal OpMode Properties
    public DcMotor frontLeftDrive, frontRightDrive,
                   backLeftDrive, backRightDrive,
                   harvesterLeft, harvesterRight;

    public Servo armTop, armBottom,
                 frontStructure, backStructure;

    // Local OpMode Properties
    HardwareMap hwMap;

    // Constructor
    public MecanumHardware() {}

    // Initialize Hardware Interfaces
    public void init(HardwareMap hardwareMap) {
        // Reference the hardware map
        hwMap = hardwareMap;

        // Names for Hardware Configuration
        frontLeftDrive = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = hwMap.get(DcMotor.class, "backRightDrive");
        harvesterLeft = hwMap.get(DcMotor.class, "harvesterLeft");
        harvesterRight = hwMap.get(DcMotor.class, "harvesterRight");

        armTop = hwMap.get(Servo.class, "armTop");
        armBottom = hwMap.get(Servo.class, "armBottom");
        frontStructure = hwMap.get(Servo.class, "frontStructure");
        backStructure = hwMap.get(Servo.class, "backStructure");

        // Motor Direction
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        harvesterRight.setDirection(DcMotor.Direction.REVERSE);

        // Set motors and servos to initial position
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        harvesterLeft.setPower(0);
        harvesterRight.setPower(0);

        armTop.setPosition(0);
        armBottom.setPosition(0);
        frontStructure.setPosition(0);
        backStructure.setPosition(0);

        // Disable encoders
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        harvesterLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        harvesterRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
