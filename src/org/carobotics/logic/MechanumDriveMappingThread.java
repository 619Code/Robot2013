/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carobotics.logic;

import org.carobotics.hardware.Joystick;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.MechanumDriveBase;

/**
 *
 * @author caadmin
 */
public class MechanumDriveMappingThread extends RobotThread {
    protected MechanumDriveBase mechanumDriveBase;
    protected DriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = false;
    private long lastTime = 0;

    public MechanumDriveMappingThread(MechanumDriveBase mechanumDriveBase,
            DriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.mechanumDriveBase = mechanumDriveBase;
        this.driverStation = driverStation;
    }

    //continuously runs through out the whole game
    protected void cycle() {
        
        ///System.out.println(System.currentTimeMillis()-lastTime);
        ///lastTime = System.currentTimeMillis();
        
        //gets the speed setting from the z-axis wheel
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);  //fix
        //if the z-axis wheel is all the way down (at 0) then set its percent to equal 0.3 instead of 0
        if(scalePercent < 0.3) scalePercent = 0.3;
        
        //percentage for going forwards/backwards y-axis
        double percent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        //percentage for going side-to-side (sliding) x-axis
        double sidepercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_X) * scalePercent;
        //percentage for turning left and right twist-axis
        double turnpercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_TWIST) * scalePercent;
        
        try {
            mechanumDriveBase.getTopleftTalon().set(percent);
            mechanumDriveBase.getToprightTalon().set(percent);
            mechanumDriveBase.getBottomleftTalon().set(percent);
            mechanumDriveBase.getBottomrightTalon().set(percent);
        } catch (Exception e) {
            if (firstError || DEBUG) {
                e.printStackTrace();
            }
        }
        
        if(DEBUG) 
            
            System.out.println("[MechanumDriveMappingThread] Percent: "+percent);
        
        shift();
    }
    
    protected void shift(){
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON11)){
            System.out.println("[MechanumDriveMappingThread] Shifting High");
            mechanumDriveBase.shiftHigh();
        }
        if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)){
            System.out.println("[MechanumDriveMappingThread] Shifting Low");
            mechanumDriveBase.shiftLow();
        }
    }
}
