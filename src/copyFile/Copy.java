package copyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import com.google.common.collect.Lists;


public class Copy {

	private String oldURL;
	private String newURL;
	private List<File> allDirFiles = new ArrayList<>();
	private List<File> allOldDirFiles = new ArrayList<>();
	private List<List<File>> subAllDirFiles = new ArrayList<>();
	private List<List<File>> subAllOldDirFiles = new ArrayList<>();
	
	
	public Copy(String oldURL, String newURL) throws IOException {
		this.setOldURL(oldURL);
		this.setNewURL(newURL);
		
		createArrayListOfAllDirFiles(new File(this.oldURL));
		
		
		//frame.dispose();*/
		int coresXs2 =  (Runtime.getRuntime().availableProcessors()*2);
		//System.out.println(coresXs2);
		this.subAllDirFiles = 
				Lists.partition(this.allDirFiles, coresXs2);
		this.subAllOldDirFiles = 
				Lists.partition(this.allOldDirFiles, coresXs2);
		ExecutorService e = Executors.newFixedThreadPool(coresXs2);
		long start = System.currentTimeMillis();
		for (int i = 0; i < this.subAllDirFiles.size(); i++) {
			CopyThread newCopyThread = 
					new CopyThread(this.subAllOldDirFiles.get(i), this.subAllDirFiles.get(i));
			e.execute(newCopyThread);
		}
		e.shutdown();
		try {
		    // Tasks are now running concurrently. Wait until all work is done, with a timeout
		    boolean isDone = e.awaitTermination(15, TimeUnit.SECONDS);
		} catch (InterruptedException exc) { exc.printStackTrace(); }
		long finish = System.currentTimeMillis();
		JOptionPane.showMessageDialog(null, "Time to copy: " + ((finish - start)/1000) + " seconds");
	}
	


	private void createArrayListOfAllDirFiles(File in) {
		
		File[] ins = in.listFiles();
		
		//File out = new File(outURL);
		if(ins == null/*is a file itself*/){
			String newAbsoluteURL = in.getAbsolutePath().replace(this.oldURL, this.newURL);;
			File newFile = new File(newAbsoluteURL);
			this.allOldDirFiles.add(in);
			this.allDirFiles.add(newFile);
		} else {
			for (File file : ins) {
				createArrayListOfAllDirFiles(file);
			}
		}
		
	}
	
	public String getOldURL() {
		return oldURL;
	}

	public void setOldURL(String oldURL) {
		this.oldURL = oldURL;
	}

	public String getNewURL() {
		return newURL;
	}

	public void setNewURL(String newURL) {
		this.newURL = newURL;
	}

}













