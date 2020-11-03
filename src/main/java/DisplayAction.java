import com.birlasoft.gui.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.*;
import java.io.*;
import java.util.prefs.*;
import java.util.Random;
public class DisplayAction implements ActionListener{

	public DisplayAction()
			{
				Preferences root = Preferences.userRoot();
				Preferences node = root.node("/com/birlasoft/gui");
				showTime = node.getInt("ShowTime", 5);
				fashion = node.get("Fashion","Orderly");
				pos = 0;
				generator = new Random();
				userDir = System.getProperty("user.home");
				File fileDir = new File(userDir,".Rapstor");
				if(!fileDir.exists()) fileDir.mkdir();
				dataFile = new File(fileDir, "MesData.rap");
				if(!dataFile.exists()) { try{dataFile.createNewFile();} catch(IOException e) {e.printStackTrace();}}
				displayFrame = new ScreenDisplayFrame();
				displayFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				/*try
							{
							displayFrame.readData(file);
							} catch(IOException ex) {ex.printStackTrace();}
				displayFrame.setVisible(true);*/
			}
	public void actionPerformed(ActionEvent ev)
			{
				
					try
					{
				file = new RandomAccessFile(dataFile /*new File(userDir + "/.Rapstor/MesData.rap")*/, "r");
					}
				catch(FileNotFoundException ex)
					{
					
					ex.printStackTrace();	
					}
							try
							{
							if(pos == file.length()) {pos = 0;}
							if(file.length()== 0) {displayFrame.readData("No Messages Stored", "To add a note click on tray icon and choose \"Open UI\"");}
							if(fashion.equals("Random"))
								{
									//System.out.println((long)((generator.nextInt((int)((file.length()/RECORD_SIZE)+1))-1)* RECORD_SIZE));
									//file.seek((int)((generator.nextInt(((int)(file.length()/RECORD_SIZE))+1)-1)* RECORD_SIZE));
									//System.out.println(generator.nextInt((int)file.length()/RECORD_SIZE)+1);
									//file.seek(Math.random()*(file.length()/RECORD_SIZE)-1 * RECORD_SIZE);
									file.seek((long)(Math.random()*((file.length()/RECORD_SIZE)+1) - 1) *RECORD_SIZE);
								}
							else {file.seek(pos);}
							displayFrame.readData(file);
							pos = file.getFilePointer();
							} catch(EOFException ex){ex.printStackTrace();} catch(IOException ex) {ex.printStackTrace();}
							//displayFrame.repaint();
							displayFrame.setVisible(true);
							Timer offTimer = new Timer(1000 * showTime, new ActionListener()
									{
								     public void actionPerformed(ActionEvent ev)
										{
											displayFrame.setVisible(false);
										}});/* DisplayAction.this);*/
									offTimer.setRepeats(false);
									offTimer.start();
					try
					{
				file.close();
					}
				catch(IOException ex)
					{
					ex.printStackTrace();	
					}

				
			}
	private ScreenDisplayFrame displayFrame;
	private RandomAccessFile file;
	private File dataFile;
	private String userDir;
	private String fashion;
	private int showTime;
	private Random generator;
	private int nRecords;
	private static final int SHORT_MESSAGE_LENGTH = 30;
	private static final int LONG_MESSAGE_LENGTH = 250;
	private static final int RECORD_SIZE = (SHORT_MESSAGE_LENGTH * 2) + (LONG_MESSAGE_LENGTH * 2); 
	private long pos;
}
