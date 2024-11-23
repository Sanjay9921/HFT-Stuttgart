package sample.app.layer3.application.usecases;

import sample.app.common.domain.model.Something;
import sample.app.layer1.infrastructure.db.SomeSQLDatabase;
import sample.app.layer2.domain.functions.DomainFunction;

public class BusinessUseCases{
	
	private DomainFunction service = new DomainFunction();
	private SomeSQLDatabase repository = new SomeSQLDatabase();

	public void process(int id) {
		Something thing = repository.read(id);
		if(thing != null) {
			Something updated = service.update(thing);
			repository.update(updated);
		}
	}
	
	public void print(int id) {
		Something thing = repository.read(id);
		System.out.println(thing);
	}

}