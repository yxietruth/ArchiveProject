package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;
/**
 * creates a newRun, ending the previous run first
 */
public class NewRun implements EventCommand {
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public NewRun(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " New Run Started.");
		cTEH.race.endRun(); // make sure previous run has ended
		if (cTEH.raceType.equals("IND")){
			cTEH.raceList.add(cTEH.race);
			cTEH.race = new RaceIND(++cTEH.runNumber, cTEH.timer);
			
		}
		else if (cTEH.raceType.equals("PARIND")){
			cTEH.raceList.add(cTEH.race);
			cTEH.race = new RacePARIND(++cTEH.runNumber, cTEH.timer);
			
		}
		//TODO Jason add other race types
	}
	
}