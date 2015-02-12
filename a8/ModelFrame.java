package a8;

import java.util.ArrayList;

public class ModelFrame {
	private static Frame modelFrame;
	private static ArrayList<ModelFrameObserver> observers = new ArrayList<ModelFrameObserver>();
	
	public Frame getModelFrame(){
		return modelFrame;
	}
	
	public void setModelFrame(Frame f){
		modelFrame = f;
		notifyObservers();
	}
	
	public void register(ModelFrameObserver o){
		observers.add(o);
	}
	
	public void remove(ModelFrameObserver o){
		observers.remove(o);
	}
	
	public void notifyObservers(){
		for(ModelFrameObserver o : observers){
			o.update(modelFrame);
		}
	}
}
