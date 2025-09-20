import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
public static void main(String[] args) throws IOException {
	
	
		JFrame Window = new JFrame("window");
		
		//close the whole program if the window is closed. without this the program will keep running even after closing the window.
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
	
		Window.setResizable(false);
		//show the window in the center of the screen 
		GamePanel gamePanel = new GamePanel();
		
		
		
		Window.add(gamePanel);
		gamePanel.GameStartThread();
		
		
		
		//Size the frame.
		Window.pack();
		Window.setLocationRelativeTo(null);
		 
		
		
		
		
		
		
		
		//Show the window.
		Window.setVisible(true);
	}
}
