package fr.sveikex.api.structure;

import java.math.BigDecimal;

public class Tx {

	public final int blockHeight;
	public final String txHash;
	public final BigDecimal amount;
	public final String address;
	public final byte lockedTime; //in blocks
	public final Type type;
	
	public Tx(int blockHeight, String txId, BigDecimal amount, String address, int lockedTime, Type type)
	{
		this.blockHeight = blockHeight;
		this.txHash = txId;
		this.amount = amount;
		this.address = address;
		this.lockedTime = (byte) lockedTime;
		this.type = type;
	}
	
	public enum Type {
		IN,
		OUT;
	}
}
