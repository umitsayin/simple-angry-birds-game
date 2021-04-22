package angryBirds;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) {
		AngryBirds ab = new AngryBirds();
		ImageIcon img = new ImageIcon("D:\\java\\AngryBirds\\images.jpg");
		JLabel back = new JLabel("",img,JLabel.CENTER);
		back.setBounds(0, 0, 1000, 600);
		JFrame game = new JFrame("Angry Birds Clone-Project");
		game.add(ab);
		
		game.setSize(1000, 600);
		game.setLocationRelativeTo(null);
		game.setDefaultCloseOperation(game.EXIT_ON_CLOSE);
		game.setVisible(true);
	}

}
