package a8;

public class ImageEditorModel implements ModelFrameObserver{

	private Frame original;
	private Frame current;
	private ModelFrame modelFrame = new ModelFrame();
	
	public ImageEditorModel(Frame f) {
		modelFrame.register(this);
		original = f;
		current = original.copy();
		modelFrame.setModelFrame(current);
	}

	public Frame getCurrent() {
		return current;
	}
	
	public void setCurrent(Frame f){
		current = f.copy();
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public void paintAt(int x, int y, Pixel brushColor, int brush_size) {
		current.suspendNotifications();
		modelFrame.getModelFrame().suspendNotifications();
		
		for (int xpos=x-brush_size+1; xpos <=x+brush_size-1; xpos++) {
			for (int ypos=y-brush_size+1; ypos <=y+brush_size-1; ypos++) {
				if (xpos >= 0 &&
					xpos < current.getWidth() &&
					ypos >= 0 &&
					ypos < current.getHeight()) {
					current.setPixel(xpos, ypos, brushColor);
					modelFrame.getModelFrame().setPixel(xpos, ypos, brushColor);
				}
			}
		}
		current.resumeNotifications();
		modelFrame.getModelFrame().resumeNotifications();
	}

	@Override
	public void update(Frame f) {
		setCurrent(f);
	}
}
