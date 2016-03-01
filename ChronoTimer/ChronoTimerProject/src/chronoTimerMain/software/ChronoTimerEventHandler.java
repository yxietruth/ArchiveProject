package chronoTimerMain.software;

import java.util.StringTokenizer;

public class ChronoTimerEventHandler {
	private Timer timer;
	private Race race;
	
	public void timeEvent(String s){
		StringTokenizer st = new StringTokenizer(s);
		
		parseCommand(st);
	};
	private void parseCommand(StringTokenizer st) {
		String token = st.nextToken();
		//find the correct command
		if(isTime(token)) {
			timer.time(st.nextToken());
		}
		else if (isNum(token)) {
			try {
			race.addRacer(new Racer(Integer.parseInt(st.nextToken())));	
			} catch (NumberFormatException e) {
				System.out.println("Error - invalid number.");
			}
		}
		else if (isClr(token)) {
			try {
				race.removeRacer(race.getCorrectRacer(Integer.parseInt(st.nextToken())));	
				} catch (NumberFormatException e) {
					System.out.println("Error - invalid number.");
				}
		}
		else if (isSwap(token)) {
			race.swap();
		}
		else if (isStart(token)) {
			race.start();
		}
		else if (isFinish(token)) {
			race.finish();
		}
		else if (isTrig(token)) {
			try {
			race.trig(Integer.parseInt(st.nextToken()));
			}catch (NumberFormatException e) {
				System.out.println("Error - invalid number.");
			}
		}
		else if (isDNF(token)) {
			race.markRacerDNF();
		}
		else if (isNewRun(token)) {
			this.newRun();
		}
		else if (isEndRun(token)) {
			this.endRun();
		}
		else if (isEvent(token)) {
			this.event(st.nextToken());
		}
		else if (isPrint(token)) {
			this.print();
		}
	}
	
	public void event(String string){
		System.out.println("Creating new event " + string);
		if(string.equalsIgnoreCase("IND")) {
			race = new RaceIND(timer);
		}
		//TODO add other races in upcoming sprints.
	};
	
	public void newRun(){
		race.setRunNumber(race.getRunNumber()+1);
		System.out.println("A new race has begun.");
	};
	public void endRun(){
		System.out.println("The race has ended.");
	};
	/**
	 * print displays the racer numbers followed by a time as
	 * (number time optionalFlag).
	 *  optionalFlag is >, R, or F. time is system, elapsed, or total. 
	 * 
	 * Implementation? Access Racers in racerList (Race class) and retrieve/print each racer number and time
	 */
	public void print(){
		System.out.println("Printing run data.");
		race.print();
	};
	
	//boolean helper methods
	private boolean isPrint(String token) {
		return token.equalsIgnoreCase("print");
	}
	private boolean isEvent(String token) {
		return token.equalsIgnoreCase("event");
	}
	private boolean isEndRun(String token) {
		return token.equalsIgnoreCase("endrun");
	}
	private boolean isNewRun(String token) {
		return token.equalsIgnoreCase("newrun");
	}
	private boolean isDNF(String token) {
		return token.equalsIgnoreCase("dnf");
	}
	private boolean isTrig(String token) {
		return token.equalsIgnoreCase("trig") || token.equalsIgnoreCase("trigger");
	}
	private boolean isFinish(String token) {
		return token.equalsIgnoreCase("finish");
	}
	private boolean isStart(String token) {
		return token.equalsIgnoreCase("start");
	}
	private boolean isSwap(String token) {
		return token.equalsIgnoreCase("swap");
	}
	private boolean isClr(String token) {
		return token.equalsIgnoreCase("clr");
	}
	private boolean isNum(String token) {
		return token.equalsIgnoreCase("num");
	}
	private boolean isTime(String token) {
		return token.equalsIgnoreCase("time");
	}
	
}
