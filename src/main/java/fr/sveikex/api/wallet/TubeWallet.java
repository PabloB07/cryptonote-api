package fr.sveikex.api.wallet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.json.JSONObject;

import fr.sveikex.api.structure.Balance;
import fr.sveikex.api.structure.Tx;

public class TubeWallet implements IWallet {

	@Override
	public JSONObject request(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance getTotalBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance getBalance(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Balance> getBalanceAsync(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Tx>> getBulkPayments(int minBlockHeight, String... paymentIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Map<String, List<Tx>>> getBulkPaymentsAsync(int minBlockHeight, String... paymentIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Tx>> getBulkPayments(String... paymentIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Map<String, List<Tx>>> getBulkPaymentsAsync(String... paymentIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tx> getBulkPayment(int minBlockHeight, String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<Tx>> getBulkPaymentAsync(int minBlockHeight, String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tx> getBulkPayment(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<Tx>> getBulkPaymentAsync(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Future<Integer> getHeightAsync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> getAddressAsync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeIntegratedAddress(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> makeIntegratedAddressAsync(String paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tx> getTransactionHistory(boolean in, boolean out, int minHeight, int maxHeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<List<Tx>> getTransactionHistoryAsync(boolean in, boolean out, int minHeight, int maxHeight) {
		// TODO Auto-generated method stub
		return null;
	}

}
