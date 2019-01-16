package fr.sveikex.api.wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.sveikex.api.Helper;
import fr.sveikex.api.structure.Balance;
import fr.sveikex.api.structure.Tx;
import fr.sveikex.api.structure.Tx.Type;

public class DeroWallet implements IWallet {

	private static final int scale = 12;
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	private final String host;
	private final String username;
	private final String password;
	
	public DeroWallet(String host, int port, String username, String password)
	{
		this.host = String.format("http://%s:%d/json_rpc", host, port);
		this.username = username;
		this.password = password;
	}
	
	@Override
	public JSONObject request(JSONObject json) 
	{
		try {
			HttpResponse<JsonNode> j = Unirest.post(host).basicAuth(username, password).header("Content-Type", "application/json").body(json).asJson();
			return j.getBody().getObject().getJSONObject("result"); //yeah it's dirty
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Balance getTotalBalance()
	{
		JSONObject json = request(IWallet.json("getbalance"));
		return new Balance(json.getLong("balance"), json.getLong("unlocked_balance"), scale);
	}

	@Override
	public Balance getBalance(String paymentId) 
	{
		BigDecimal balanceUnlocked = BigDecimal.ZERO;
		BigDecimal balanceTotal = BigDecimal.ZERO;
		
		List<Tx> tx = getBulkPayments(paymentId);
		
		for (Tx t : tx)
		{
			if (t.lockedTime == 0)
				balanceUnlocked = balanceUnlocked.add(t.amount);
			balanceTotal = balanceTotal.add(t.amount);
		}
		
		return new Balance(balanceTotal, balanceUnlocked, scale);
	}

	@Override
	public List<Tx> getBulkPayments(String... paymentIds)
	{		
		return getBulkPayments(200, paymentIds);
	}
	
	@Override
	public List<Tx> getBulkPayments(int minBlockHeight, String... paymentIds)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("payment_ids", paymentIds);
		map.put("min_block_height", minBlockHeight);
		
		JSONArray a = request(IWallet.json("get_bulk_payments", map)).getJSONArray("payments");
		
		List<Tx> tx = new ArrayList<Tx>();
		
		Iterator<Object> it = a.iterator();
		
		while (it.hasNext())
		{
			JSONObject j = (JSONObject) it.next();
			
			String address = j.isNull("destinations") ? null : j.getJSONArray("destinations").getString(0);
			BigDecimal amount = Helper.decimal(j.getLong("amount"), scale);
			int lockedTime = j.getInt("unlock_time");
			tx.add(new Tx(j.getString("tx_hash"), amount, address, lockedTime, address == null ? Type.IN : Type.OUT));
		}
		
		return tx;
	}
	
	@Override
	public int getHeight()
	{
		return request(IWallet.json("getheight")).getInt("height");
	}

	@Override
	public String getAddress()
	{
		return request(IWallet.json("getaddress")).getString("address");
	}
	
	@Override
	public String makeIntegratedAddress(String paymentId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payment_id", paymentId);
		
		return request(IWallet.json("make_integrated_address", map)).getString("integrated_address");
	}

	@Override
	public List<Tx> getTransactionHistory(boolean in, boolean out, int minHeight, int maxHeight) 
	{
		List<Tx> tx = new ArrayList<Tx>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("in", in);
		params.put("out", out);
		params.put("min_height", 0);
		params.put("max_height", 0);
		
		JSONObject json = request(IWallet.json("get_transfers", params));
		
		if (in)
		{
			JSONArray ai = json.getJSONArray("in");
			
			Iterator<Object> it = ai.iterator();
			
			while (it.hasNext())
			{
				JSONObject o = (JSONObject) it.next();
				BigDecimal amount = Helper.decimal(json.getLong("amount"), scale);
				
				tx.add(new Tx(o.getString("tx_hash"), amount, null, o.getInt("unlock_time"), Type.IN));
			}
		}
		
		if (out)
		{
			JSONArray ao = json.getJSONArray("out");
			
			Iterator<Object> it = ao.iterator();
			
			while (it.hasNext())
			{
				JSONObject o = (JSONObject) it.next();
				BigDecimal amount = Helper.decimal(json.getLong("amount"), scale);
				String address = null; //Captain need to return "destinations" addresses.
				tx.add(new Tx(o.getString("tx_hash"), amount, address, o.getInt("unlock_time"), Type.OUT));
			}
		}
		
		return tx;
	}

	@Override
	public Future<Balance> getBalanceAsync(String paymentId) {
		
		return executor.submit(() -> {
			return getBalance(paymentId);
		});
	}

	@Override
	public Future<List<Tx>> getBulkPaymentsAsync(int minBlockHeight, String... paymentIds) {
		return  executor.submit(() -> {
			return getBulkPayments(minBlockHeight, paymentIds);
		});
	}

	@Override
	public Future<List<Tx>> getBulkPaymentsAsync(String... paymentIds) {
		return executor.submit(() -> {
			return getBulkPayments(paymentIds);
		});
	}

	@Override
	public Future<Integer> getHeightAsync() {
		return executor.submit(() -> {
			return getHeight();
		});
	}

	@Override
	public Future<String> getAddressAsync() {
		return executor.submit(() -> {
			return getAddress();
		});
	}

	@Override
	public Future<String> makeIntegratedAddressAsync(String paymentId) {
		return executor.submit(() -> {
			return makeIntegratedAddress(paymentId);
		});
	}

	@Override
	public Future<List<Tx>> getTransactionHistoryAsync(boolean in, boolean out, int minHeight, int maxHeight) {
		return executor.submit(() -> {
			return getTransactionHistory(in, out, minHeight, maxHeight);
		});
	}
}
