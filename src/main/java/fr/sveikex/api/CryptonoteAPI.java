package fr.sveikex.api;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.sveikex.api.structure.Tx;
import fr.sveikex.api.wallet.DeroWallet;
import fr.sveikex.api.wallet.IWallet;

public class CryptonoteAPI {

	//TODO fix Async functions
	
	public static void main(String[] args) throws InterruptedException
	{
		IWallet dero = new DeroWallet("localhost", 30309, "", "");
		//String p = c.random64PaymentID();
		System.out.println("total balance: " + dero.getTotalBalance().balanceUnlocked);
		System.out.println("balance: " + dero.getBalance("d5ec52ddb3c11838ab345145e4e63a562e0d918664eafa93c1990e5af689eb4b").totalBalance); //d5ec52ddb3c11838ab345145e4e63a562e0d918664eafa93c1990e5af689eb4b*/
		Future<List<Tx>> future = dero.getTransactionHistoryAsync(true, true, 0, 0);
		
		while(!future.isDone()) {}
		
		System.out.println("Done!");
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}