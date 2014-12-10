package com.lclass.actor;

public class Level {

	int enabled;
	String word;
	
	public Level(String word, int enabled)
	{
		this.word = word;
		this.enabled = enabled;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
