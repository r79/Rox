package gameEngine;


import gameEngine.View;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

/**
 *
 * @author Sebastian Galli
 */
public class Main{
 
    public static void main(String[] args) throws IOException {
    
    	JFrame frame = new JFrame("Rox");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new BorderLayout());
    	
    	View view = new View();
    	frame.add(view, BorderLayout.CENTER);
    	
    	frame.setSize(457, 479);
    	frame.setVisible(true);
        frame.setResizable(false);
    	
    }
}