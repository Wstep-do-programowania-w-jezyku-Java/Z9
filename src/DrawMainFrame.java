import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;

public class DrawMainFrame extends JFrame implements Runnable {
	private ControlPanel controlPanel;
	private DrawPanel drawPanel;
	private Color currentColor;
	private Point startPoint=new Point(0,300),endPoint=new Point(0,400);
	public static void main(String[] args) {
		EventQueue.invokeLater(new DrawMainFrame("Okno główne"));
	}

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public DrawMainFrame(String title) {
		super(title);
		setSize(600,400);
		controlPanel=new ControlPanel(this);
		drawPanel=new DrawPanel(this);
		add(controlPanel,BorderLayout.SOUTH);
		add(drawPanel,BorderLayout.CENTER);
		controlPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		pack();
	}
	
	class ControlPanel extends JPanel{
		private DrawMainFrame parentFrame;
		private JButton closeButton,colorButton,animateButton,loadButton;
		private Timer t;
		public ControlPanel(DrawMainFrame parentFrame) {
			this.parentFrame=parentFrame;
			setPreferredSize(new Dimension(parentFrame.getWidth(),parentFrame.getHeight()/10));
			closeButton=new JButton("Zamknij");
			add(closeButton);
			closeButton.addActionListener(new  ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
			colorButton=new JButton("Kolor");
			add(colorButton);
			colorButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentColor=JColorChooser.showDialog(parentFrame, "Wybierz kolor", Color.BLACK);
					drawPanel.repaint();
				}
			});
			animateButton=new JButton("Animuj");
			add(animateButton);
			animateButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					t=new Timer(1000,new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(startPoint.x<drawPanel.getWidth()) {
							startPoint.translate(10, 0);
							endPoint.translate(10, 0);
							drawPanel.repaint();}
							else {
								t.stop();
								startPoint=new Point(0,300);
								endPoint=new Point(0,400);
							}
						}
					});
					t.start();
				}
			});
			loadButton=new JButton("Ładuj obrazek");
			add(loadButton);
			loadButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc=new JFileChooser(new File("."));
					fc.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public boolean accept(File f) {
							return f.getName().contains("jpg") || f.getName().contains("png");
						}
					});
					int result=fc.showOpenDialog(parentFrame);
//					if result==JFileChooser.APPROVE_OPTION
					
				}
			});
		}
	}
	
	class DrawPanel extends JPanel{
		private DrawMainFrame parentFrame;
		public DrawPanel(DrawMainFrame parentFrame) {
			this.parentFrame=parentFrame;
			setPreferredSize(new Dimension(800,600));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
			Graphics2D g2=(Graphics2D)g;
			
			g2.setPaint(currentColor);
			
			Rectangle2D rec=new Rectangle2D.Double(50,50,300,200);
			g2.draw(rec);
			
			Line2D.Double line=new Line2D.Double(50,50,350,250);
			g2.draw(line);
			
			Ellipse2D el=new Ellipse2D.Double(50,50,300,200);
			g2.draw(el);
			
			Line2D animatedLine=new Line2D.Double(startPoint,endPoint);
			g2.draw(animatedLine);
		}
	}
}
