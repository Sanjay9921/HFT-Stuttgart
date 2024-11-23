package sample.app.layer2.domain.ports.in;

import java.util.List;

import sample.app.layer1.domain.model.Something;

public interface DomainService {

	List<Something> merge(Something thing1, Something thing2);

	boolean validate(Something thing);

	Something update(Something thing);

}