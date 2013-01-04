package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import representations.Chromosome;
import factory.RepresentationFactory;

public class Communicator<R extends Chromosome> {

	private final Problem problem;
	private final CommunicationModel model;
	private final RepresentationFactory<R> factory;
	private List<R> received = new ArrayList<R>();
	
	public int id;
	
	public Communicator(Problem problem, CommunicationModel model, RepresentationFactory<R> factory, int id) {
		this.model = model;
		this.problem = problem;
		this.factory = factory;
		this.id = id;
	}
	
	public List<R> getMessages(){
		model.await();
		List<R> result = received;
		received = new ArrayList<R>();
		model.readyToReceive(this);
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
