package chronoTimerMain.simulator.hardwareHandler;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import chronoTimerMain.simulator.Sensor;
import chronoTimerMain.simulator.sensor.SensorElectricEye;
import chronoTimerMain.simulator.sensor.SensorGate;
import chronoTimerMain.simulator.sensor.SensorPad;
import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.eventHandler.ChronoTimerEventHandler;
import chronoTimerMain.software.racetypes.RacePARIND;
import junit.framework.TestCase;
/**
 * ChronoHardwareHandler is an adapter between the simulator and the rest of chronotimer.
 * This class implements all hardware-related commands that represent physical processes, such as turning the power on.
 * All non-hardware commands are sent to ChronoTimerEventHandler to be parsed and implemented there.
 * @author Jason
 *
 */
public class ChronoHardwareHandler {

	protected Timer time = new Timer();
	protected Sensor[] sensors = new Sensor[13];//no sensor stored in index 0. 12 max
	public Sensor[] getSensors() {
		return sensors;
	}
	protected boolean[] isEnabledChannel = new boolean[13];
	public boolean[] getIsEnabledChannel() {
		return isEnabledChannel;
	}
	protected  ArrayList<SingleEvent> eventLog = new ArrayList<SingleEvent>();
	protected boolean power = false;
	protected ChronoTimerEventHandler eventHandler;
	protected boolean printerPower = true;
	
	public boolean isPrinterPower() {
		return printerPower;
	}
	public String getRaceType() {
		return eventHandler.getRaceType();
	}

	/**
	 * Interaction between simulator and rest of ChronoTimer
	 * @param command
	 * @param timestamp 
	 * @return returns a string array with index 0 = gui display and index 1 = printer tape string. Returns empty string if unchanged.
	 **/
	public String [] inputFromSimulator(String command, String[] args, String timestamp) {//returns a String[] now for GUI display update
		if(timestamp == null) {
			eventLog.add(new SingleEvent(time.getCurrentChronoTime(), command, args));
		}
		else
			new SingleEvent(timestamp, command, args);//save the input to eventlog
		command = command.toUpperCase();
		
		switch (command){
		case "POWER":
			System.out.println(timestamp + " Toggling power switch.");
			power();
			break;
		case "ON":
			System.out.println(timestamp + " Powering on.");
			ON();
			break;
		case "OFF":
			System.out.println(timestamp + " Powering off");
			OFF();
			break;
		case "EXIT":
			System.out.println(timestamp + " Exiting Simulator. Have a nice day.");
			exit();
			break;
		}
		if(power){
			//TODO add succeed/fail messages
			//TODO move CONN out of power method
			switch(command) {
				case "CONN":
					System.out.println(timestamp +" Connecting sensor " + args[0] + " at channel " + args[1]);
					try{
						conn(args[0], Integer.parseInt(args[1]));
						eventHandler.passSensors(sensors);
					}catch (NumberFormatException e) {
						System.out.println("Error - Could not parse channel number");
					}
					break;
				case "DISC":
					System.out.println(timestamp + " Disconnecting channel " + args[0]);
					try{
						disc(Integer.parseInt(args[0]));
						eventHandler.passSensors(sensors);
					}catch (NumberFormatException e) {
						System.out.println(timestamp + " Error - Could not parse channel number");
					}
					break;
				case "TOGGLE":
				case "TOG":
					System.out.println(timestamp + " Toggling channel " + args[0]);
					try {
						toggle(Integer.parseInt(args[0]));
						eventHandler.passToggles(isEnabledChannel);
					}catch (NumberFormatException e) {
						System.out.println("Error - Could not parse channel number");
					}
					break;
					
				case "RESET":
					System.out.println(timestamp + " Resetting system.");
					reset();
					break;
				default:
					if(command.equalsIgnoreCase("print") && !printerPower) {//ignore print if printer power off
						break;
					}
					return eventHandler.timeEvent(command, args, timestamp);
			}
		}
		return new String[] {"",""};
	}
	
	/**
	 * Toggles the interaction between Hardware and Software
	 * 
	 * @return boolean representing the state of the machine
	 */
	public boolean power(){
		//do the opposite of power state
		if(this.power)
			this.OFF();
		else this.ON();
		//return power...
		return this.power;
	}
	
	/**
	 * turns on power
	 */
	public void ON(){
		//initialize stuff
		eventLog = new ArrayList<SingleEvent>();
		eventHandler = new ChronoTimerEventHandler(time);
		this.power = true;
	}
	
	/**
	 * turns off power
	 */
	public void OFF(){
		this.power = false;

	}
	/**
	 * exits the program
	 */
	public void exit(){
		OFF();
		System.exit(0);
	}
	/**
	 * resets chronotimer to initial states.
	 */
	public void reset() {
		time = new Timer();
		eventHandler = new ChronoTimerEventHandler(time);
		eventLog = new ArrayList<SingleEvent>();
	}
	
	/**
	 * turns channel on and off
	 * @param channel
	 */
	public boolean toggle(int channel) {
		boolean result = false;
		// check if power is on and channel is connected before toggling it
		if (power && channel <= 12 && channel > 0 && sensors[channel] != null) {
			isEnabledChannel[channel] = !isEnabledChannel[channel];
			result = true;
		}
		return result;
	}
	
	/**
	 * adds a sensor to specified channel
	 * @param type the type of sensor
	 * @param channel the channel associated with the sensor, 1-12
	 * @return int of channel added to or -10 (outside range) on failure
	 */
	public int conn(String type, int channel){
		int result = -10;
		if (channel <= 12 && channel > 0) {
			type = type.toUpperCase();
			switch(type) {
				case "EYE":
					sensors[channel] = new SensorElectricEye();
					result = channel;
					break;
				case "PAD":
					sensors[channel] = new SensorPad();
					result = channel;
					break;
				case "GATE":
					sensors[channel] = new SensorGate();
					result = channel;
					break;
				default: 
					System.out.println("Error. Invalid sensor type.");
					break;
			}
		}
		return result;
	}
	/**
	 * removes a sensor from channel
	 * @param channel the channel number to disconnect the sensor
	 */
	public int disc(int channel){
		int result = -10;
		if (channel <= 12 && channel > 0 && sensors[channel] != null) {
			sensors[channel] = null;
			result = channel;
		}
		return result;
	}
	/**
	 * changes printer power state to b
	 * @param b, true if power is on, false if off
	 */
	public void setPrinterPower(boolean b){
		printerPower=b;
	}
}