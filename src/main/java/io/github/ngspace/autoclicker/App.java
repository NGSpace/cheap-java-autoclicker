package io.github.ngspace.autoclicker;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

/**
 * JPackage command for windows:
 * jpackage --vendor ngspace --app-version "1.0" --copyright ngspace --description "Cheap Java Autoclicker" --name "CheapJavaAutoclicker" --type msi -i autoclicker --main-jar autoclicker.jar --win-dir-chooser --win-menu --win-shortcut-prompt
 */
public class App {
	public static Robot robot;
	public static boolean started;
	static Random rng = new Random();
    
    public static void main(String[] args) throws AWTException, ClassNotFoundException, InstantiationException,
    	IllegalAccessException, UnsupportedLookAndFeelException {
    	robot = new Robot();
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	
    	JFrame frame = new JFrame();
    	frame.setLayout(null);

    	JLabel label = new JLabel("Press any key to stop", SwingConstants.CENTER);
    	label.setFont(new Font("arial", Font.BOLD, 14));
    	label.setBounds(310, 0, 270, 50);
    	frame.add(label);
    	
    	JLabel After = new JLabel("After starting move your mouse to", SwingConstants.CENTER);
    	After.setFont(new Font("arial", Font.BOLD, 14));
    	After.setBounds(310, 50, 270, 50);
    	frame.add(After);
    	
    	JLabel After2 = new JLabel("where you wish to be auto clicked", SwingConstants.CENTER);
    	After2.setFont(new Font("arial", Font.BOLD, 14));
    	After2.setBounds(310, 65, 270, 50);
    	frame.add(After2);

    	JLabel min_int = new JLabel("Minimum intreval:");
    	min_int.setFont(new Font("arial", Font.BOLD, 14));
    	min_int.setBounds(0, 0, 140, 100);
    	frame.add(min_int);
    	
    	DoubleSpinner min = new DoubleSpinner();
    	min.setBounds(140, 0, 110, 75);
    	min.setValue(4);
    	frame.add(min);
    	
    	JLabel max_int = new JLabel("Maximum intreval:");
    	max_int.setFont(new Font("arial", Font.BOLD, 14));
    	max_int.setBounds(0, 100, 140, 100);
    	frame.add(max_int);
    	
    	DoubleSpinner max = new DoubleSpinner();
    	max.setBounds(140, 100, 110, 75);
    	max.setValue(5);
    	frame.add(max);
    	
    	JButton button = new JButton("Start");
    	button.setFont(new Font("arial", Font.BOLD, 14));
    	button.setBounds(310, 100, 270, 100);
    	frame.add(button);
    	
		// Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input


		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			
			@Override public void keyReleased(GlobalKeyEvent event) {
				if (started) System.exit(0);
			}
		});
		
    	button.addMouseListener(new MouseAdapter() {
    		@Override public void mouseClicked(MouseEvent e) {
				if (started) System.exit(0);
    			started = true;
    			Thread s = new Thread(()->{
        			button.setText("5 Seconds until start.");
    				for (int i = 5;i!=0;i--) {
	    				try {
							Thread.sleep(1000);
		        			button.setText((i-1)+" Seconds until start.");
						} catch (Exception e1) {
							throw new RuntimeException(e1);//This shit can be dangerous so even the smallest error needs to automatically shutdown to prevent damage.
						}
    				}
	    			button.setText("Press any key to stop");
    				try {
    	    			while (true) {
    						Thread.sleep((long) (rng.nextDouble(min.getDouble(), max.getDouble())*1000));
    						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    	    			}
					} catch (Exception e1) {
						throw new RuntimeException(e1);//Again, no risks.
					}
    			});
    			s.setUncaughtExceptionHandler((t,ex)->{ex.printStackTrace();System.exit(0);});
    			s.start();
    		}
		});

        frame.setVisible(true);
        frame.setSize(600, 250);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
