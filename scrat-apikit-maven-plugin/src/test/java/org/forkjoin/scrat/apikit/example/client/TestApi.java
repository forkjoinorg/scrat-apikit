package org.forkjoin.scrat.apikit.example.client;

import reactor.core.publisher.Mono;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.forkjoin.scrat.apikit.client.*;

import org.forkjoin.scrat.apikit.example.client.core.TestModel;
import org.forkjoin.scrat.apikit.example.client.form.GenericForm;
import org.forkjoin.scrat.apikit.example.client.form.ObjectForm;
import org.forkjoin.scrat.apikit.example.client.form.ValidForm;
import org.forkjoin.scrat.apikit.example.client.model.GenericModel;
import org.forkjoin.scrat.apikit.example.client.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.client.model.ValidModel;

/**
 * 
 */
public class TestApi {
	private ReactiveHttpClientAdapter httpClientAdapter;

	public TestApi() {
	}

	public TestApi(ReactiveHttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/long</b>
	* <ul>
	* <li><b>Model:</b> long</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see long
	
	   */
	public Mono<Long> longValue() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/long", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _0Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/generic</b>
	* <ul>
	* <li><b>Form:</b>GenericForm&lt;ValidForm&gt;generic</li>
	* <li><b>Model:</b> GenericModel&lt;ValidModel&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see GenericModel&lt;ValidModel&gt;
	* @see GenericForm&lt;ValidForm&gt;
	
	   */
	public Mono<GenericModel<ValidModel>> generic(GenericForm<ValidForm> form) {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/generic", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _1Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/valid</b>
	* <ul>
	* <li><b>Form:</b>ValidFormvalid</li>
	* <li><b>Model:</b> ValidModel</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see ValidModel
	* @see ValidForm
	
	   */
	public Mono<ValidModel> valid(ValidForm form) {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/valid", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _2Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/instantArray</b>
	* <ul>
	* <li><b>Model:</b> java.util.ArrayList&lt;Date&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see java.util.ArrayList&lt;Date&gt;
	
	   */
	public Mono<java.util.ArrayList<Date>> instantsArray() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/instantArray", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _3Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/testModel</b>
	* <ul>
	* <li><b>Model:</b> TestModel&lt;Date&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see TestModel&lt;Date&gt;
	
	   */
	public Mono<TestModel<Date>> testModel() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/testModel", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _4Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/testObject</b>
	* <ul>
	* <li><b>Model:</b> ObjectModel</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see ObjectModel
	
	   */
	public Mono<ObjectModel> testObject() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/testObject", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _5Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/instants</b>
	* <ul>
	* <li><b>Model:</b> List&lt;Date&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see List&lt;Date&gt;
	
	   */
	public Mono<List<Date>> instants() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/instants", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _6Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/instant</b>
	* <ul>
	* <li><b>Model:</b> Date</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see Date
	
	   */
	public Mono<Date> instant() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/instant", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _7Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/object</b>
	* <ul>
	* <li><b>Form:</b>ObjectFormobject</li>
	* <li><b>Model:</b> ObjectModel</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see ObjectModel
	* @see ObjectForm
	
	   */
	public Mono<ObjectModel> object(ObjectForm form) {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/object", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _8Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/longs</b>
	* <ul>
	* <li><b>Model:</b> List&lt;Long&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see List&lt;Long&gt;
	
	   */
	public Mono<List<Long>> longs() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/test/longs", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _9Type, true);
	}

	public void setHttpClientAdapter(ReactiveHttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	public ReactiveHttpClientAdapter getHttpClientAdapter() {
		return httpClientAdapter;
	}

	private static final ApiType _0Type = ApiUtils.type(Long.class);
	private static final ApiType _1Type = ApiUtils.type(GenericModel.class, ApiUtils.type(ValidModel.class));
	private static final ApiType _2Type = ApiUtils.type(ValidModel.class);
	private static final ApiType _3Type = ApiUtils.type(java.util.ArrayList.class, Date.class);
	private static final ApiType _4Type = ApiUtils.type(TestModel.class, ApiUtils.type(Date.class));
	private static final ApiType _5Type = ApiUtils.type(ObjectModel.class);
	private static final ApiType _6Type = ApiUtils.type(List.class, ApiUtils.type(Date.class));
	private static final ApiType _7Type = ApiUtils.type(Date.class);
	private static final ApiType _8Type = ApiUtils.type(ObjectModel.class);
	private static final ApiType _9Type = ApiUtils.type(List.class, ApiUtils.type(Long.class));
}