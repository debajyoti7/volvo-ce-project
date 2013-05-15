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
		builder = new ProcessBuilder("amos2/bin/amos2.exe", arg0);
		builder.redirectErrorStream(true);
		forkWorker = new AmosProcessWorker(output,builder);
		forkWorker.execute();
	}

	public void doStop() {
		if (forkWorker != null) {
			forkWorker.exec_cmd("quit;");
			forkWorker.cancel(true);
		}
	}
	
	public void execute(String cmd) {
		forkWorker.exec_cmd(cmd);
	}
}

