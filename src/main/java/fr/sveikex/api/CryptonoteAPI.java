package fr.sveikex.api;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.sveikex.api.structure.Tx;
import fr.sveikex.api.wallet.DeroWallet;
import fr.sveikex.api.wallet.IWallet;

public class CryptonoteAPI {

	 private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	//TODO fix Async functions
	
	public static void main(String[] args)
	{
		IWallet dero = new DeroWallet("localhost", 30309, "", "");

		scheduler.scheduleAtFixedRate(new ScheduledTask(dero), 0, 5, TimeUnit.SECONDS);
		
		System.out.println("total balance: " + dero.getTotalBalance().balanceUnlocked);
		System.out.println("balance: " + dero.getBalance("d5ec52ddb3c11838ab345145e4e63a562e0d918664eafa93c1990e5af689eb4b").totalBalance); //d5ec52ddb3c11838ab345145e4e63a562e0d918664eafa93c1990e5af689eb4b
		Future<List<Tx>> future = dero.getBulkPaymentAsync("d5ec52ddb3c11838ab345145e4e63a562e0d918664eafa93c1990e5af689eb4b");//(true, true, 0, 0);
		
		while(!future.isDone()) {}
		
		System.out.println("Done!");
		try {
			for(Tx tx : future.get())
				System.out.println(tx.blockHeight + " | " + tx.txHash + " | " + tx.amount + " DERO" + " | " + tx.type);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		//IWallet.shutdownExecutor();
		//scheduler.shutdown();
	}
}