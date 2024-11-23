package sample.app.layer5.main;

import java.util.Random;

import sample.app.common.domain.model.Something;
import sample.app.layer1.infrastructure.db.SomeSQLDatabase;
import sample.app.layer4.infrastructure.ui.UserInteraction;

public class Main {
	
	private static void initRepository() {
		SomeSQLDatabase repository = new SomeSQLDatabase();
		if(repository.count() == 0) {
			Random r = new Random();
			for(int i = 1; i <= 99; i++) {
				Something thing = new Something(0, r.nextInt(100));
				repository.create(thing);
			}
		}
	}

	public static void main(String[] args) {
		initRepository();
		UserInteraction ui = new UserInteraction();
		ui.run();
	}
}
