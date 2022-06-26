package jp.ac.hcs.s3a300.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 天気情報を操作する.
 * livedoorの提供するお天気Webサービスを活用する.
 * - http://weather.livedoor.com/weather_hacks/webservice
 */
@Transactional
@Service
public class WeatherService {

	@Autowired
	RestTemplate restTemplate;

	/** 天気予報API リクエストURL */
	private static final String URL = "https://weather.tsukumijima.net/api/forecast?city={citycode}";

	/**
	 * 指定した都市コードに紐づく天気情報を取得する.
	 * @param cityCode 都市コード
	 * @return WeatherEntity
	 */
	public WeatherEntity getWeather(String cityCode) {

		// 外部APIアクセス
		String json = restTemplate.getForObject(URL, String.class, cityCode);
		String cityName = null;
		
		switch(cityCode) {
			case "016010":
				cityName = "札幌";
				break;
			case "012010":
				cityName = "旭川";
				break;
			case "011000":
				cityName = "稚内";
				break;
			case "012020":
				cityName = "留萌";
				break;
			case "013010":
				cityName = "網走";
				break;
			case "013020":
				cityName = "北見";
				break;
			case "013030":
				cityName = "紋別";
				break;
			case "014010":
				cityName = "根室";
				break;
			case "014020":
				cityName = "釧路";
				break;
			case "014030":
				cityName = "帯広";
				break;
			case "016020":
				cityName = "岩見沢";
				break;
			case "016030":
				cityName = "倶知安";
				break;
			case "017010":
				cityName = "函館";
				break;
			case "017020":
				cityName = "江差";
				break;
			case "015010":
				cityName = "室蘭";
				break;
			case "015020":
				cityName = "浦河";
				break;
			default:
				cityName = "";
				break;
		}
			
		

		WeatherEntity weatherEntity = new WeatherEntity();

		// Mapping
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);

			weatherEntity.setCityCode(cityName);
			weatherEntity.setTitle(node.get("title").asText());
			weatherEntity.setDescription(node.get("description").get("text").asText());
			//weatherEntity.setErrorMessage(node.get("errorMessage").get("text").asText());

			for (JsonNode forecast : node.get("forecasts")) {

				weatherEntity.getForecasts()
						.add(new WeatherData(forecast.get("dateLabel").asText(), forecast.get("telop").asText(), forecast.get("image").get("url").asText()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return weatherEntity;
	}

}