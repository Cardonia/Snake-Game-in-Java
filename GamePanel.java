import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	int snakeLength = 2;
	int snakeX=10,snakeY=10;
	int snakeBodyX = 0;
	int snakeBodyY = 0;
	int moveX=0,moveY=0;
	final int TileSize = 32;
	final int WTile = 20;
	final int LTile = 20;
	final int ScreenW = WTile * TileSize; //640
	final int ScreenL = LTile * TileSize; //640
	boolean snakeDeath = false;
	boolean gameStart = false;
	KeyHandler keyH = new KeyHandler();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(ScreenW , ScreenL));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	Thread GameThread;      
	
	
	
	
	
	
	public void GameStartThread() {
		GameThread =new Thread(this);
		GameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/5;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(GameThread != null) {
			
			if(snakeDeath) {break;}
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			keyUpdate();
			
			if(delta >= 1) {
			update();
			if(snakeDeath) {break;}
			repaint();
			delta --;
			
			}
			
			
			
		}
	}
	
	
	
	
	int [][] map = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	
	
	
	
	public void keyUpdate() {
		
		if(keyH.wKey == true) {moveY=-1; moveX=0;gameStart =true;}
		if(keyH.sKey == true) {moveY=1; moveX=0;gameStart =true;}
		if(keyH.aKey == true) {moveY=0; moveX=-1;gameStart =true;}
		if(keyH.dKey == true) {moveY=0; moveX=1;gameStart =true;}
	}

	public void update() {
		
		
		snakeUpdate();
	}
	
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
	
		
		
		
		
		for(int y = 0;y<20;y++) {
			for(int x = 0;x<20;x++) {
			
			if(map[y][x]>0) {
				g2.setColor(Color.white);
				g2.fillRect(x*32,y*32,32,32);
				map[y][x]-=1;
			}
			else if (map[y][x]==-1) {
				g2.setColor(Color.red);
				g2.fillRect(x*32,y*32,32,32);
			}
			
			}
			
		}
		
		g2.dispose();
		
		//System.out.println(PX + " " + PY);
	}
	
	
	 
	
	public void snakeUpdate() {
		
		snakeY+=moveY;
		snakeX+=moveX;
		if(snakeX < 0 || snakeX > 19 || snakeY < 0 || snakeY > 19 ) {
			
			snakeDeath = true;
			return;
		}
		
		if(gameStart) {
			if(map[snakeY][snakeX]>0) {
			
			snakeDeath = true;
			return;
			}
		}
		
		
		if(map[snakeY][snakeX] == -1) {
			map[snakeY][snakeX] = 0;
			snakeLength++;
			for(int y = 0;y<20;y++) {
				for(int x = 0;x<20;x++) {
				
				if(map[y][x]>0)map[y][x]+=1;
				
				}
			}
			while (true) {
				int randomX = (int)(Math.random() * 20); 
				int randomY = (int)(Math.random() * 20); 
				if(map[randomY][randomX] == 0) {
					map[randomY][randomX] = -1;
					break;
				}
				
				
			}
		}
		
		
		map[snakeY][snakeX]=snakeLength;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
