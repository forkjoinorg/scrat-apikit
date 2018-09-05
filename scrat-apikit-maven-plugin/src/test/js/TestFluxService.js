
import {AbstractApi} from 'apikit-core'

import {requestGroupImpi} from 'apikit-core'


class TestFluxService extends AbstractApi {

    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/generic</b>
    * <ul>
    * <li><b>Form:</b>GenericFormgeneric</li>
    * <li><b>Model:</b> GenericModel</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see GenericModel
    * @see GenericForm

     */
    generic = (form) => {
        let _path = null;
        return super._request("test", "testFluxService", "POST", "/testFlux/generic", _path, form);
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
    valid = (form) => {
        let _path = null;
        return super._request("test", "testFluxService", "POST", "/testFlux/valid", _path, form);
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
    testObject = () => {
        let _path = null;
        return super._request("test", "testFluxService", "POST", "/testFlux/testObject", _path, null);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/valid/{id}-{id2}</b>
    * <ul>
    * <li><b>Form:</b>ValidFormvalidById</li>
    * <li><b>PathVariable:</b> string id2</li>
    * <li><b>PathVariable:</b> string id</li>
    * <li><b>Model:</b> ValidModel</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see ValidModel
    * @see ValidForm
    * @see string
    * @see string

     */
    validById = (form, id2, id) => {
        let _path = {};
        _path["id2"] = id2;
        _path["id"] = id;
        return super._request("test", "testFluxService", "POST", "/testFlux/valid/{id}-{id2}", _path, form);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/testFlux/instants</b>
    * <ul>
    * <li><b>Model:</b> Date[]</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see Date[]

     */
    instants = () => {
        let _path = null;
        return super._request("test", "testFluxService", "POST", "/testFlux/instants", _path, null);
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
    instant = () => {
        let _path = null;
        return super._request("test", "testFluxService", "GET", "/testFlux/instant", _path, null);
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
    object = (form) => {
        let _path = null;
        return super._request("test", "testFluxService", "POST", "/testFlux/object", _path, form);
    }

}

export { TestFluxService };
const testFluxService = new TestFluxService();
testFluxService._init(requestGroupImpi);
export default testFluxService;

