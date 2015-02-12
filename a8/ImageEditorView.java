package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImageEditorView extends JPanel implements ActionListener{

	private JFrame main_frame;
	private FrameView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JPanel buttonPanel;
	private JButton openButton;
	private JPanel framePanel;
	private ModelFrame modelFrame = new ModelFrame();
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		
		setLayout(new BorderLayout());
		
		frame_view = new FrameView(modelFrame.getModelFrame());
		framePanel = new JPanel();
		framePanel.add(frame_view);
		framePanel.setAlignmentX(CENTER_ALIGNMENT);
		framePanel.setAlignmentY(TOP_ALIGNMENT);
		
		add(framePanel, BorderLayout.NORTH);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		add(tool_ui_panel, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		openButton = new JButton("Open");
		openButton.addActionListener(this);
		
		buttonPanel.add(openButton, BorderLayout.NORTH);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		tool_ui = null;
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String url = JOptionPane.showInputDialog("Please enter a URL");
		try{
			ColorFrame cf = ColorFrame.readFromURL(url);
			modelFrame.setModelFrame(cf);
			frame_view.setFrame(cf);
			framePanel.setPreferredSize(new Dimension(cf.getWidth(), cf.getHeight()));
			main_frame.pack();
			
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(main_frame, "This URL is not valid, please try again");
		}
	}
	

}
