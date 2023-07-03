package util;

import java.util.Random;

public class RandomID {
	public static String getID() {
		String ID = "";
		Random rand = new Random();
		for (int i = 0; i < 16; i++) {
			ID = ID + rand.nextInt(10);
		}
		return ID;
	}
}
