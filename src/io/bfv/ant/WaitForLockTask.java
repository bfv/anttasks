package io.bfv.ant;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class WaitForLockTask extends Task {
	
	private String file = "";
	public void setFile(String value){
		this.file = value;
	}
	
	private int sleep = 1;
	public void setSleep(int value) {
		this.sleep = value;
	}
	
	private boolean mustExist = true; 
	public void setMustExist(boolean value){ 
		this.mustExist = value;
	}
	

	public void execute () throws BuildException {
		
		int cycles = 0;
		
		if (this.file.equals("")) {
			throw new BuildException("file property must be set\n");
		}
		
		System.out.printf("Waiting for delete of %s\n", this.file);

		File f = new File(this.file);
		while(f.exists()) {
			
			try {
				Thread.sleep(this.sleep * 1000);
			} catch (InterruptedException e) {
				throw new BuildException(e.getMessage());
			}
			
			cycles++;
					
		}
		
		if (cycles == 0) {
			if (this.mustExist) {
				throw new BuildException("ERROR: File '" + this.file + "' not found and mustexist = true");
			}
			else {
				System.out.printf("File not found\n");
			}
		}
		
		System.out.printf("Waited for %d seconds\n", cycles);
	}
}
