package jp.ac.hcs.s3a300.gourmet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * グルメ情報検索結果の店舗情報.
 * 各項目のデータ仕様については、APIの仕様を参照とする.
 * - http://webservice.recruit.co.jp/hotpepper/reference.html
 */
@Data
public class ShopEntity {

	/** 全件数 */
	private String results_available;

	/** 検索件数 */
	private String results_returned;

	/** 開始位置 */
	private String results_start;

	/** 店舗情報のリスト */
	private List<ShopData> shops = new ArrayList<ShopData>();

}