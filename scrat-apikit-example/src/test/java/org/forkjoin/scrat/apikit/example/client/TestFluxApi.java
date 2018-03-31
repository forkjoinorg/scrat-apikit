package org.forkjoin.scrat.apikit.example.client;

import reactor.core.publisher.Mono;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.forkjoin.scrat.apikit.client.*;

import org.forkjoin.scrat.apikit.example.client.form.GenericForm;
import org.forkjoin.scrat.apikit.example.client.form.ObjectForm;
import org.forkjoin.scrat.apikit.example.client.form.ValidForm;
import org.forkjoin.scrat.apikit.example.client.model.GenericModel;
import org.forkjoin.scrat.apikit.example.client.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.client.model.ValidModel;

/**
 * 
 */
public class TestFluxApi {
	private ReactiveHttpClientAdapter httpClientAdapter;

	public TestFluxApi() {
	}

	public TestFluxApi(ReactiveHttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/generic</b>
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
		String _url = ApiUtils.expandUriComponent("/testFlux/generic", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _0Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/valid</b>
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
		String _url = ApiUtils.expandUriComponent("/testFlux/valid", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _1Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/testObject</b>
	* <ul>
	* <li><b>Model:</b> ObjectModel</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see ObjectModel
	
	   */
	public Mono<ObjectModel> testObject() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/testFlux/testObject", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _2Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/valid/{id}-{id2}</b>
	* <ul>
	* <li><b>Form:</b>ValidFormvalidById</li>
	* <li><b>PathVariable:</b> String id2</li>
	* <li><b>PathVariable:</b> String id</li>
	* <li><b>Model:</b> ValidModel</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see ValidModel
	* @see ValidForm
	* @see String
	* @see String
	
	   */
	public Mono<ValidModel> validById(ValidForm form, String id2, String id) {
		Map<String, Object> _uriVariables = new HashMap<>();
		_uriVariables.put("id2", id2);
		_uriVariables.put("id", id);
		String _url = ApiUtils.expandUriComponent("/testFlux/valid/{id}-{id2}", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _3Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/instants</b>
	* <ul>
	* <li><b>Model:</b> java.util.ArrayList&lt;Date&gt;</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see java.util.ArrayList&lt;Date&gt;
	
	   */
	public Mono<java.util.ArrayList<Date>> instants() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/testFlux/instants", _uriVariables);

		return httpClientAdapter.request("POST", _url, null, _4Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/instant</b>
	* <ul>
	* <li><b>Model:</b> Date</li>
	* <li>需要登录</li>
	* </ul>
	* </div>
	* @see Date
	
	   */
	public Mono<Date> instant() {
		Map<String, Object> _uriVariables = new HashMap<>();
		String _url = ApiUtils.expandUriComponent("/testFlux/instant", _uriVariables);

		return httpClientAdapter.request("GET", _url, null, _5Type, true);
	}

	/**
	   * 
	   *
	   * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/object</b>
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
		String _url = ApiUtils.expandUriComponent("/testFlux/object", _uriVariables);

		List<Entry<String, Object>> _form = form.encode("", new ArrayList<Entry<String, Object>>());
		return httpClientAdapter.request("POST", _url, _form, _6Type, true);
	}

	public void setHttpClientAdapter(ReactiveHttpClientAdapter httpClientAdapter) {
		this.httpClientAdapter = httpClientAdapter;
	}

	public ReactiveHttpClientAdapter getHttpClientAdapter() {
		return httpClientAdapter;
	}

	private static final ApiType _0Type = ApiUtils.type(GenericModel.class, ApiUtils.type(ValidModel.class));
	private static final ApiType _1Type = ApiUtils.type(ValidModel.class);
	private static final ApiType _2Type = ApiUtils.type(ObjectModel.class);
	private static final ApiType _3Type = ApiUtils.type(ValidModel.class);
	private static final ApiType _4Type = ApiUtils.type(java.util.ArrayList.class, Date.class);
	private static final ApiType _5Type = ApiUtils.type(Date.class);
	private static final ApiType _6Type = ApiUtils.type(ObjectModel.class);
}