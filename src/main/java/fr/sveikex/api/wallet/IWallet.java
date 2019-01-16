package fr.sveikex.api.wallet;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

import org.json.JSONObject;

import fr.sveikex.api.structure.Balance;
import fr.sveikex.api.structure.Tx;

public interface IWallet {
	
	JSONObject request(JSONObject json);
	
	public Balance getTotalBalance();
	
	/**
	 * 
	 * @param paymentId
	 */
	public Balance getBalance(String paymentId);
	
	public Future<Balance> getBalanceAsync(String paymentId);
	
	public List<Tx> getBulkPayments(int minBlockHeight, String... paymentIds);
	
	public Future<List<Tx>> getBulkPaymentsAsync(int minBlockHeight, String... paymentIds);
	
	public List<Tx> getBulkPayments(String... paymentIds);
	
	public Future<List<Tx>> getBulkPaymentsAsync(String... paymentIds);
	
	public int getHeight();
	
	public Future<Integer> getHeightAsync();
	
	public String getAddress();

	public Future<String> getAddressAsync();
	
	public String makeIntegratedAddress(String paymentId);
	
	public Future<String> makeIntegratedAddressAsync(String paymentId);
	
	/**
	 * 
	 * @param in
	 * @param out
	 * @param minHeight
	 * @param maxHeight set 0 for current block
	 * @return List<Tx>
	 */
	public List<Tx> getTransactionHistory(boolean in, boolean out, int minHeight, int maxHeight);
	
	public Future<List<Tx>> getTransactionHistoryAsync(boolean in, boolean out, int minHeight, int maxHeight);
	
	public static String randomPaymentId(int size)
	{
		StringBuilder builder = new StringBuilder(size);
		Random rnd = new Random();

		while (builder.length() < size)
			builder.append(Integer.toHexString(rnd.nextInt()));

		return builder.substring(0, size);
	}
	
	static JSONObject json(String method)
	{
		return json(method, null);
	}
	
	static JSONObject json(String method, Map<String, Object> params)
	{
		JSONObject json = new JSONObject().put("jsonrpc", "2.0").put("id", "1").put("method", method);
		
		if (params != null) json.put("params", params);
		
		return json;
	}
}
