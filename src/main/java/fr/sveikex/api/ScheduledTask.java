package fr.sveikex.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import fr.sveikex.api.structure.Tx;
import fr.sveikex.api.wallet.IWallet;

public class ScheduledTask extends TimerTask {

	private final IWallet wallet;
	private int lastHeight;
	private List<String> paymentIds;
	
	public ScheduledTask(IWallet wallet, String... paymentIds)
	{
		this.wallet = wallet;
		this.lastHeight = 0;
		this.paymentIds = Arrays.asList(paymentIds);
	}
	
	public void addPaymentId(String paymentId)
	{
		paymentIds.add(paymentId);
	}
	
	@Override
	public void run() 
	{
		System.out.println("Searching at min height " + lastHeight);
		Map<String, List<Tx>> tx = wallet.getBulkPayments(lastHeight, paymentIds.toArray(new String[paymentIds.size()]));
		/*
		for(Entry<String, List<Tx>> e : tx.entrySet())
		{
			System.out.println("paymentId:" + e.getKey());
			for (Tx t : e.getValue())
				System.out.println(t.blockHeight + " | " + t.txHash + " | " + t.amount + " DERO" + " | " + t.type);
		}*/
		
		System.out.println("setting new lastHeight");
		
		lastHeight = wallet.getHeight();
	}
}
