package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Window {
	private static final JFrame win = new JFrame();
	private static final Container mainPane = new Container();
	private static final JPanel slideColorsPane = new JPanel(),
			holdColorsPane = new JPanel(), txtColorsPane = new JPanel();
	private static final JSlider[] RGBslide = {
			new JSlider(JSlider.HORIZONTAL, 0, 255, 255),
			new JSlider(JSlider.HORIZONTAL, 0, 255, 255),
			new JSlider(JSlider.HORIZONTAL, 0, 255, 255)};
	private static final JLabel[] RGBtxt = {new JLabel(), new JLabel(),
			new JLabel()};
	private static JButton[] holdColorBtn = new JButton[16];
	private int holdColor() {
		int COLOR = 0;
		for (int i = 0; i < 3; i++)
			COLOR |= (RGBslide[2 - i].getValue() & 0xFF) << 8 * i;
		return COLOR;
	}
	private void holdColorClick(int btnIndex) {
		holdColorBtn[btnIndex].setBackground(new Color(holdColor()));
	}
	private void changeColor() {
		final int COLOR = holdColor();
		slideColorsPane.setBackground(new Color(COLOR));
		txtColorsPane.setBackground(new Color(COLOR));
		for (int i = 0; i < 3; i++) {
			RGBtxt[i].setText("" + RGBslide[i].getValue());
			RGBtxt[i].setForeground(new Color(0xFFFFFF & ~COLOR));
			RGBslide[i].setBackground(new Color(COLOR));
		}
		win.setTitle("RGB color: #%06X".formatted(COLOR));
	}
	private void drawWindow() {
		win.setTitle("Color Choice");
		win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		win.setIconImage(
				Toolkit.getDefaultToolkit().getImage("img/icon24.png"));
		win.setBounds(200, 200, 300, 200);
		win.setResizable(false);
		txtColorsPane.setPreferredSize(new Dimension(25, 0));
		holdColorsPane.setPreferredSize(new Dimension(0, 60));
		slideColorsPane.setLayout(new GridLayout(3, 1));
		txtColorsPane.setLayout(new GridLayout(3, 1));
		holdColorsPane.setLayout(new GridLayout(2, 8));
		changeColor();
		for (int i = 0; i < 3; i++) {
			RGBslide[i].addChangeListener(e -> changeColor());
			RGBtxt[i].setHorizontalAlignment(SwingConstants.RIGHT);
			slideColorsPane.add(RGBslide[i]);
			txtColorsPane.add(RGBtxt[i]);
		}
		for (int i = 0; i < 16; i++) {
			holdColorBtn[i] = new JButton();
			final int index = i;
			holdColorBtn[i].addActionListener(e -> holdColorClick(index));
			holdColorClick(i);
			holdColorsPane.add(holdColorBtn[i]);
		}
		mainPane.setLayout(new BorderLayout());
		mainPane.add(slideColorsPane, BorderLayout.CENTER);
		mainPane.add(txtColorsPane, BorderLayout.WEST);
		mainPane.add(holdColorsPane, BorderLayout.SOUTH);
		win.add(mainPane);
		win.setVisible(true);
	}
	public Window() {
		drawWindow();
	}
	public static void main(String[] args) {
		new Window();
	}
}