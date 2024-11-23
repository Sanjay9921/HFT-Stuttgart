package shop.model.mobile;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class MobilePhone extends JFrame{
	
	private JTextArea text;
	
	public MobilePhone(String number){
		super("Phone #" + number);
		
		text = new JTextArea();
		
		DefaultCaret caret = (DefaultCaret) text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		this.add(new JScrollPane(text));
		
		text.setText("All messages on your mobile phone...\n");
		this.setPreferredSize(new Dimension(300, 400));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLocationByPlatform(true);
		this.pack();
	}
}
