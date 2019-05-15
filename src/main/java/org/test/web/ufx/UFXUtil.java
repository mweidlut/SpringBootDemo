package org.test.web.ufx;

import java.util.logging.Logger;

import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;

public class UFXUtil {

	public static String OPERATOR_NO = "814";
	public static String PASSWD = "6+6+";
	public static String ACCOUNT_CODE = "6016";
	public static String COMBI_NO = "6016000";
	public static String MARKET_NO = "1";
	public static String STOCK_CODE = "801354";
	public static String ENTRUST_PRICE = "14";
	public static String ENTRUST_AMOUNT = "1000";

	private static Logger logger = Logger.getLogger("UFXUtil");

	public static void PrintMsg(IDatasets result) {
		StringBuilder sb = new StringBuilder();
		int datasetCount = result.getDatasetCount();
		// 遍历所有的结果集
		for (int i = 0; i < datasetCount; i++) {
			sb.append("===============================================\n");
			// 开始读取单个结果集的信息
			IDataset ds = result.getDataset(i);
			int columnCount = ds.getColumnCount();
			// 遍历单个结果集列信息
			for (int j = 1; j <= columnCount; j++) {
				sb.append(String.format("%20s", ds.getColumnName(j)));
				sb.append("|");
			}
			sb.append("\n");
			// 遍历单个结果集记录，遍历前首先将指针置到开始
			ds.beforeFirst();
			while (ds.hasNext()) {
				sb.append("\n");
				ds.next();
				for (int j = 1; j <= columnCount; j++) {
					sb.append(String.format("%20s", ds.getString(j)));
					sb.append("|");
				}
			}
		}
		sb.append("\n");
		logger.info(sb.toString());
	}

	/**
	 * 登陆消息(10001)
	 * 
	 * @return
	 */
	public static IDataset GetLoginPack() {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("operator_no");
		dataset.addColumn("password");
		dataset.addColumn("mac_address");
		dataset.addColumn("op_station");
		dataset.addColumn("ip_address");
		dataset.addColumn("authorization_id");
		dataset.addColumn("login_time");
		dataset.addColumn("verification_code");
		dataset.appendRow();
		dataset.updateString("operator_no", OPERATOR_NO);
		dataset.updateString("password", PASSWD);
		dataset.updateString("mac_address", "00-FF-52-92-F4-2E");
		dataset.updateString("op_station", "123");
		dataset.updateString("ip_address", "10.0.7.112");
		dataset.updateString("authorization_id", "187540A387373696B3EA7B0BDC34C723");
		dataset.updateString("login_time", "");
		dataset.updateString("verification_code", "");
		return dataset;
	}

	/**
	 * 普通买卖委托消息 (91001)
	 * 
	 * @return
	 */
	public static IDataset GetEntrustPack(String session) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("user_token");
		dataset.addColumn("batch_no");
		dataset.addColumn("account_code");
		dataset.addColumn("asset_no");
		dataset.addColumn("combi_no");
		dataset.addColumn("instance_no");
		dataset.addColumn("stockholder_id");
		dataset.addColumn("report_seat");
		dataset.addColumn("market_no");
		dataset.addColumn("stock_code");
		dataset.addColumn("entrust_direction");
		dataset.addColumn("price_type");
		dataset.addColumn("entrust_price");
		dataset.addColumn("entrust_amount");
		dataset.addColumn("invest_type");
		dataset.addColumn("extsystem_id");
		dataset.addColumn("third_reff");

		dataset.appendRow();
		dataset.updateString("user_token", session);
		dataset.updateString("batch_no", "");
		dataset.updateString("account_code", ACCOUNT_CODE);
		dataset.updateString("asset_no", "");
		dataset.updateString("combi_no", COMBI_NO);
		dataset.updateString("instance_no", "");
		dataset.updateString("stockholder_id", "");
		dataset.updateString("report_seat", "");
		dataset.updateString("market_no", MARKET_NO);
		dataset.updateString("stock_code", STOCK_CODE);
		dataset.updateString("entrust_direction", "3");
		dataset.updateString("price_type", "0");
		dataset.updateString("entrust_price", ENTRUST_PRICE);
		dataset.updateString("entrust_amount", ENTRUST_AMOUNT);
		dataset.updateString("invest_type", "");
		dataset.updateString("extsystem_id", "hundsun");
		dataset.updateString("third_reff", "demo");
		return dataset;
	}

	/**
	 * 委托查询(32001)
	 * 
	 * @return
	 */
	public static IDataset GetEntrustQryPack(String session, String enrtustno) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("user_token");
		dataset.addColumn("account_code");
		dataset.addColumn("combi_no");
		dataset.addColumn("entrust_no");

		dataset.appendRow();
		dataset.updateString("user_token", session);
		dataset.updateString("account_code", ACCOUNT_CODE);
		dataset.updateString("combi_no", COMBI_NO);
		dataset.updateString("entrust_no", enrtustno);

		return dataset;
	}

	/**
	 * 成交查询(33001)
	 * 
	 * @return
	 */
	public static IDataset GetDealQryPack(String session, String enrtustno) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("user_token");
		dataset.addColumn("account_code");
		dataset.addColumn("combi_no");
		dataset.addColumn("entrust_no");

		dataset.appendRow();
		dataset.updateString("user_token", session);
		dataset.updateString("account_code", ACCOUNT_CODE);
		dataset.updateString("combi_no", COMBI_NO);
		dataset.updateString("entrust_no", enrtustno);

		return dataset;
	}

	/**
	 * 撤单(33001)
	 * 
	 * @return
	 */
	public static IDataset GetWithdrawPack(String session, String enrtustno) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("user_token");
		dataset.addColumn("entrust_no");

		dataset.appendRow();
		dataset.updateString("user_token", session);
		dataset.updateString("entrust_no", enrtustno);

		return dataset;
	}

}
