package com.hq.common.http;

import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.hq.common.tools.JsonTools;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTP请求工具
 */
@Slf4j
public final class HTTPTools {

	private HTTPTools() {
	}

	public static RestTemplate restTemplate;

	/**
	 * POST 请求
	 *
	 * @param url        地址
	 * @param data       数据
	 * @param returnType 返回类型
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T post(String url, Object data, Class<T> returnType) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			String s = null;
			if (Objects.nonNull(data)) {
				s = data.getClass().equals(String.class) ? data.toString() : JsonTools.toString(data);
			}

			HttpEntity<String> formEntity = new HttpEntity<>(s, headers);
			String response = restTemplate.postForObject(url, formEntity, String.class);

			if (String.class.equals(returnType)) {
				return (T) response;
			}

			return Objects.nonNull(response) ? (JsonTools.toObject(response, returnType)) : null;
		} catch (Exception e) {
			throw new RuntimeException("HTTP-POST请求异常:" + url + e.getMessage());
		}
	}

	/**
	 * http get请求
	 *
	 * @param url        地址
	 * @param returnType 响应类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String url, Class<T> returnType) {
		try {
			String response = restTemplate.getForObject(url, String.class);
			if (String.class.equals(returnType)) {
				return (T) response;
			}
			return Objects.nonNull(response) ? JsonTools.toObject(response, returnType) : null;
		} catch (Exception e) {
			throw new RuntimeException("HTTP-GET请求异常:" + url + e.getMessage());
		}
	}

}
