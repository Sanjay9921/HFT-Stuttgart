package sample.app.layer3.application.ports.out;

import sample.app.layer1.domain.model.Something;

public interface AppRepository {

	Something create(Something thing);

	Something read(int id);

	boolean update(Something thing);

	boolean delete(Something thing);

	int count();

	void reset();

}