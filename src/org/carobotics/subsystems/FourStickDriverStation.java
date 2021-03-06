package org.carobotics.subsystems;

import org.carobotics.hardware.Joystick;

/**
 *
 * @author CaRobotics
 */
public class FourStickDriverStation extends DriverStation{
    private Joystick thirdJoystick, fourthJoystick;
    
    public FourStickDriverStation(int leftId, int rightId, int thirdId, int fourthId){
        super(leftId, rightId);
        thirdJoystick = new Joystick(thirdId);
        fourthJoystick = new Joystick(fourthId);
    }

    public Joystick getFourthJoystick() {
        return fourthJoystick;
    }

    public Joystick getThirdJoystick() {
        return thirdJoystick;
    }
}
