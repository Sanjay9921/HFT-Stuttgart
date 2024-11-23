package sample.app.layer3.application.usecases;

import sample.app.layer1.domain.model.Something;
import sample.app.layer2.domain.ports.in.DomainService;
import sample.app.layer3.application.ports.in.BusinessManager;
import sample.app.layer3.application.ports.out.AppRepository;

public class BusinessUseCases implements BusinessManager {
	
	private final AppRepository repository;
	private final DomainService service;

	public BusinessUseCases(AppRepository database, DomainService service) {
		this.repository = database;
		this.service = service;
	}

	@Override
	public void process(int id) {
		Something thing = repository.read(id);
		if(thing != null) {
			Something updated = service.update(thing);
			repository.update(updated);
		}
	}
	
	@Override
	public void print(int id) {
		Something thing = repository.read(id);
		if(thing != null)
			System.out.println(thing);
	}

}