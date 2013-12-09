package copyFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class CopyThread implements Runnable {

	private List<File> source;
	private List<File> dest;
	
	public CopyThread(List<File> source, List<File> dest) {
		super();
		this.source = source;
		this.dest = dest;
	}

	@Override
	public void run() {
		try {
			goOverAllFilesAndSave();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void goOverAllFilesAndSave() throws IOException {
		for (int i = 0; i < this.dest.size(); i++) {
			File outFile = this.dest.get(i);
			
			if (!outFile.getParentFile().canWrite()){
				outFile.getParentFile().mkdirs();
			}
			copyFileNIO(this.source.get(i), outFile);
			//progressBar.setValue(i);
		}
	}
	
	public void copyFileNIO(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
	
	public List<File> getDest() {
		return dest;
	}

	public void setDest(List<File> dest) {
		this.dest = dest;
	}

	public List<File> getSource() {
		return source;
	}

	public void setSource(List<File> source) {
		this.source = source;
	}

	

}
