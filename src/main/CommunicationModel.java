package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import representations.Chromosome;
import factory.RepresentationFactory;

public class CommunicationModel {

	private List<Communicator<?>> communicators = new ArrayList<Communicator<?>>();
	private CyclicBarrier barrier;
	private Problem problem;
	
	public CommunicationModel(Problem problem, int nrCommunicators) {
		this.problem = problem;
		barrier = new CyclicBarrier(nrCommunicators);
	}
	
	public synchronized <R extends Chromosome> Communicator<R> forge(RepresentationFactory<R> factory){
		Communicator<R> comm = new Communicator<R>(problem, this, factory);
		communicators.add(comm);
		return comm;
	}
	
	public void await(){
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void send(Communicator<?> comm, Chromosome chrom){
		for(Communicator<?> c:communicators){
			if(c != comm){
				c.receive(chrom);
			}
		}
	}
}
