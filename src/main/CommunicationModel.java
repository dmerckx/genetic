package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import representations.Chromosome;
import factory.RepresentationFactory;

public class CommunicationModel {

	public static boolean RANDOM_TARGET = false;
	
	private List<Communicator<?>> communicators = new ArrayList<Communicator<?>>();
	private CyclicBarrier barrier;
	private CyclicBarrier receiveBarrier;
	private Problem problem;
	
	public static final Random rand = new Random(157);
	
	private int nrComms;
	
	public CommunicationModel(Problem problem, int nrCommunicators) {
		this.problem = problem;
		this.nrComms = nrCommunicators;
		barrier = new CyclicBarrier(nrCommunicators);
		receiveBarrier = new CyclicBarrier(nrCommunicators);
	}
	
	public synchronized <R extends Chromosome> Communicator<R> forge(RepresentationFactory<R> factory, int id){
		Communicator<R> comm = new Communicator<R>(problem, this, factory, id);
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
		if(communicators.size() != nrComms)
			throw new IllegalStateException();
		
		if(RANDOM_TARGET){
			communicators.get(rand.nextInt(nrComms)).receive(chrom);
		}
		else{
			communicators.get((comm.id +1) % nrComms).receive(chrom);
		}
	}

	public void readyToReceive(Communicator<?> comm) {
		try {
			receiveBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
