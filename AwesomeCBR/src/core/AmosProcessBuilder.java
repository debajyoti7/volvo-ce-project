package core;

import javax.swing.*;

public class AmosProcessBuilder {

	protected JTextArea output;
	private AmosProcessWorker forkWorker;
	private ProcessBuilder builder;

	public AmosProcessBuilder(JTextArea o) {
		output = o;
	}

	public void doStart(String arg0) {
		output.append("Connected from AMOS\n");
		builder = new ProcessBuilder("amos2/bin/amos2.exe", arg0);
		builder.redirectErrorStream(true);
		forkWorker = new AmosProcessWorker(output,builder);
		forkWorker.execute();
	}

	public void doStop() {
		//if (forkWorker != null) {
		try {
			output.append("\nDisconnected from AMOS\n");
			forkWorker.exec_cmd("quit;");
			forkWorker.cancel(true);
		} catch (Exception e) { output.append("\n"+e.getMessage()+"\n"); }
		//}
	}
	
	public void execute(String cmd) {
		//if(forkWorker != null) {
		try {
			forkWorker.exec_cmd(cmd);
		} catch(Exception e) { output.append("\n"+e.getMessage()+"\n"); }
		//}
	}
}

