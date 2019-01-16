package fr.sveikex.api.structure;

import java.math.BigDecimal;

import fr.sveikex.api.Helper;

public class Balance {

	public final BigDecimal totalBalance;
	public final BigDecimal balanceUnlocked;
	public final BigDecimal balanceLocked;
	
	public Balance(long balance, long balanceUnlocked, int scale)
	{		
		this(Helper.decimal(balance, scale), Helper.decimal(balanceUnlocked, scale), scale);
	}
	
	public Balance(BigDecimal totalBalance, BigDecimal balanceUnlocked, int scale)
	{
		this.totalBalance = totalBalance;
		this.balanceUnlocked = balanceUnlocked;
		this.balanceLocked = this.totalBalance.subtract(this.balanceUnlocked);
	}
}
