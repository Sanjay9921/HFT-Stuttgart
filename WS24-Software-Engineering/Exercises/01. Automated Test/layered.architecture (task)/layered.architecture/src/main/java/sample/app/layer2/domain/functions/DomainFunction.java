package sample.app.layer2.domain.functions;

import java.util.List;

import sample.app.common.domain.model.Something;

public class DomainFunction {
	
	public List<Something> merge(Something thing1, Something thing2) {
		int sum = thing1.data() + thing2.data();
		Something merged1 = new Something(thing1.id(), sum);
		Something merged2 = new Something(thing2.id(), sum);
		List<Something> result = List.of(merged1, merged2);
		return result;
	}
	
	public boolean validate(Something thing) {
		return thing.data() >= 0;
	}
	
	public Something update(Something thing) {
		int data = thing.data();
		return new Something(thing.id(), data + 1);
	}
}
