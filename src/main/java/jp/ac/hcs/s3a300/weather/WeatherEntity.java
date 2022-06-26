package jp.ac.hcs.s3a300.weather;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 天気予報検索結果の天気情報.
 * 各項目のデータ仕様については、APIの仕様を参照とする.
 * - http://weather.livedoor.com/weather_hacks/webservice
 */
@Data
public class WeatherEntity {
	
	/** 都市名 */
	private String cityCode;

	/** タイトル */
	private String title;

	/** 説明文 */
	private String description;

	/** 天気情報のリスト */
	private List<WeatherData> forecasts = new ArrayList<WeatherData>();
	
	/** エラーメッセージ（表示用） */
	private String errorMessage;

}