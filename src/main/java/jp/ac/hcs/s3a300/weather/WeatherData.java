package jp.ac.hcs.s3a300.weather;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 1件分の天気情報.
 * 各項目のデータ仕様については、APIの仕様を参照とする.
 * - http://weather.livedoor.com/weather_hacks/webservice
 */
@Data
@AllArgsConstructor
public class WeatherData {

	/** 日付 */
	private String dateLabel;

	/** 予報 */
	private String telop;
	
	/** 天気アイコン */
	private String image;

}