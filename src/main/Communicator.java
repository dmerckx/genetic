package main;

import java.util.ArrayList;
import java.util.List;

import representations.Chromosome;
import factory.RepresentationFactory;

public class Communicator<R extends Chromosome> {

	private final Problem problem;
	private final CommunicationModel model;
	private final RepresentationFactory<R> factory;
	private List<R> received = new ArrayList<R>();
	
	
	public Communicator(Problem problem, CommunicationModel model, RepresentationFactory<R> factory) {
		this.model = model;
		this.problem = problem;
		this.factory = factory;
	}
	
	public List<R> getMessages(){
		model.await();
		List<R> result = received;
		received = new ArrayList<R>();
		return result;
	}
	
	public synchronized void receive(Chromosome chrom){
		R newChrom = factory.create(problem);
		newChrom.fromPath(chrom.toPath());
		received.add(newChrom);
	}
	
	public void send(R chrom){
		model.send(this, chrom);
	}
}
