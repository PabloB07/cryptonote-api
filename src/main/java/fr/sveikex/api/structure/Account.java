package fr.sveikex.api.structure;

public class Account { //TODO

	public final int blockHeight;
	public final String integratedAddress;
	public final String paymentId;
	public Balance balance;
	
	public Account(int blockHeight, String integratedAddress, String paymentId)
	{
		this.blockHeight = blockHeight;
		this.integratedAddress = integratedAddress;
		this.paymentId = paymentId;
	}
	
}
