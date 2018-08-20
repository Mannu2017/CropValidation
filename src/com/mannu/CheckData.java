package com.mannu;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

public class CheckData {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new CheckData();
			}
		});
	}

	public CheckData() {
		Timer timer=new Timer();
		timer.schedule(new DataProcess(), 0, 1000 * 60 * 60);
	}
	
	
}
