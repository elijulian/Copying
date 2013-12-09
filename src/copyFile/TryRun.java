package copyFile;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TryRun {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Create a file chooser
		JFrame frame = new JFrame();
		JFileChooser fileChooser = new JFileChooser("C:\\");
		frame.add(fileChooser);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		JOptionPane.showMessageDialog(null, "Choose the file/directory to copy from");
		int returnVal = fileChooser.showOpenDialog(frame);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File sourceChosen = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(null, "Choose the directory to copy to");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			returnVal = fileChooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File destChosen = fileChooser.getSelectedFile();
				
				new Copy(sourceChosen.getAbsolutePath(),destChosen.getAbsolutePath());
				
				
			}
		}
		//new Copy("C:\\Program Files", "c:\\Samples-IO");
		frame.dispose();
	}

}
