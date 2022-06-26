package jp.ac.hcs.s3a300.gourmet;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分の店舗情報.
 */
@Data
@NoArgsConstructor
public class ShopData {

	/** id */
	private String id;

	/** 名前 */
	private String name;

	/** ロゴURL */
	private String logo_image;

	/** 名前（カナ） */
	private String name_kana;

	/** 住所 */
	private String address;

	/** 最寄り駅 */
	private String access;

	/** URL */
	private String url;
}
