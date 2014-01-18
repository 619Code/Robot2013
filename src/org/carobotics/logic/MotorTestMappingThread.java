package org.carobotics.logic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.carobotics.hardware.Joystick;
import org.carobotics.hardware.Talon;
import org.carobotics.hardware.Victor;
import org.carobotics.subsystems.DriverStation;
import org.carobotics.subsystems.FourStickDriverStation;

/**
 * Map joystick values to motor values
 * @author CaRobotics
 */
public class MotorTestMappingThread extends RobotThread {
    protected Victor victor1;
    protected Victor victor2;
    protected Talon talon;
    protected FourStickDriverStation driverStation;
    private boolean firstError = true;
    private final static boolean DEBUG = true;
    private long lastTime = 0;

    public MotorTestMappingThread(Victor victor1, Victor victor2, Talon talon,
            FourStickDriverStation driverStation, int period, ThreadManager threadManager) {
        super(period, threadManager);
        this.victor1 = victor1;
        this.victor2 = victor2;
        this.driverStation = driverStation;
    }

    protected void cycle() {
        double scalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
        if(scalePercent < 0.3) {
            scalePercent = 0.3;
        }

        double percent1 = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double percent2 = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;
        double percent3 = driverStation.getThirdJoystick().getAxis(Joystick.Axis.AXIS_Y) * scalePercent;

        try {
            victor1.set(percent1);
            victor2.set(percent2);
            talon.set(percent3);
        } catch(Exception e) {
            if(firstError || DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
