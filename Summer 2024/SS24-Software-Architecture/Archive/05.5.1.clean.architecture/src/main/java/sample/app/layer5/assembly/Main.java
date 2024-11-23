package sample.app.layer5.assembly;

import java.util.Random;

import sample.app.layer1.domain.model.Something;
import sample.app.layer2.domain.functions.DomainFunction;
import sample.app.layer2.domain.ports.in.DomainService;
import sample.app.layer3.application.ports.in.BusinessManager;
import sample.app.layer3.application.ports.out.AppRepository;
import sample.app.layer3.application.usecases.BusinessUseCases;
import sample.app.layer4.infrastructure.db.SomeSQLDatabase;
import sample.app.layer4.infrastructure.ui.UserInteraction;

public class Main {
	
	private static void initRepository(AppRepository repository) {
		if(repository.count() == 0) {
			Random r = new Random();
			for(int i = 1; i <= 99; i++) {
				Something thing = new Something(0, r.nextInt(100));
				repository.create(thing);
			}
		}
	}

	public static void main(String[] args) {
		AppRepository repository = new SomeSQLDatabase("jdbc:sqlite:sample.db");
		initRepository(repository);
		DomainService service = new DomainFunction();
		BusinessManager manager = new BusinessUseCases(repository, service);
		UserInteraction ui = new UserInteraction(manager);
		ui.run();
	}
}
