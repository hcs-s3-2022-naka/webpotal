package jp.ac.hcs.s3a300.gourmet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * グルメ情報を操作する.
 * リクルートの提供するグルメサーチAPIを活用する.
 * - http://webservice.recruit.co.jp/hotpepper/reference.html
 */
@Transactional
@Service
public class GourmetService {

	@Autowired
	RestTemplate restTemplate;

	/** リクルート APIキー ※取扱注意 */
	private static final String API_KEY = "c936fc549d3d07d1";

	/** グルメサーチAPI リクエストURL */
	private static final String URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key={apikey}&keyword={shopname}&large_service_area={large_service_area}&start={start}&format=json";

	/**
	 * 指定した店舗名と大サービスエリアコードに紐づく店舗情報を取得する.
	 * @param shopname 店舗名
	 * @param large_service_area 大サービスエリアコード
	 * @return ShopEntity
	 */
	public ShopEntity getShops(String shopname, String large_service_area, String start) {

		// 外部APIアクセス
		String json = restTemplate.getForObject(URL, String.class, API_KEY, shopname, large_service_area, start);

		ShopEntity shopEntity = new ShopEntity();

		// Mapping
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);

			shopEntity.setResults_available(node.get("results").get("results_available").asText());
			shopEntity.setResults_returned(node.get("results").get("results_returned").asText());
			shopEntity.setResults_start(node.get("results").get("results_start").asText());

			for (JsonNode shop : node.get("results").get("shop")) {

				ShopData shopData = new ShopData();
				shopData.setId(shop.get("id").asText());
				shopData.setName(shop.get("name").asText());
				shopData.setLogo_image(shop.get("logo_image").asText());
				shopData.setName_kana(shop.get("name_kana").asText());
				shopData.setAddress(shop.get("address").asText());
				shopData.setAccess(shop.get("access").asText());
				shopData.setUrl(shop.get("urls").get("pc").asText());

				shopEntity.getShops().add(shopData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return shopEntity;
	}

}