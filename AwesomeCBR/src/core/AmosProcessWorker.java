package core;

import javax.swing.*;
import java.io.*;
import java.util.*;
 
class AmosProcessWorker extends SwingWorker<String,String> {
  
  private JTextArea output; // Where to redirect STDERR & STDOUT to
  private ProcessBuilder builder;
  private Process process;
  private PrintWriter out;
  
  public AmosProcessWorker(JTextArea output, ProcessBuilder builder) {
    this.output=output;
    this.builder= builder;
  }
  
  protected void process(java.util.List<String> chunks) {
    // Done on the event thread
    Iterator<String> it = chunks.iterator();
    while (it.hasNext()) {
      output.append(it.next());
    }
  }
  
  public String doInBackground() {
    try {
      process = builder.start();
      InputStream res = process.getInputStream();
      //process.getE
      out = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
      byte[] buffer = new byte[100];
      int len;
      
      while ( (len=res.read(buffer,0,buffer.length))!=-1) {
        publish(new String(buffer,0,len));
        
        if (isCancelled()) {
          process.destroy();
          return "";
        }
        
      }
      
    }
    catch (Exception e) {
    	//publish(e.getMessage());
    }
    return "";  // Don't care
  }
  
  protected void done() {
    // Done on the swing event thread
    //output.append("\nALL DONE"); 
  }
  
  public void exec_cmd(String cmd) {
	  out.write(cmd);
	  out.flush();
  }
}

