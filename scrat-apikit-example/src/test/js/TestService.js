
import {AbstractApi} from 'apikit-core'

import {requestGroupImpi} from 'apikit-core'


class TestService extends AbstractApi {

    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/long</b>
    * <ul>
    * <li><b>Model:</b> number</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see number

     */
    longValue = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/long", _path, null);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/generic</b>
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
        return super._request("test", "testService", "POST", "/test/generic", _path, form);
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
    valid = (form) => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/valid", _path, form);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/instantArray</b>
    * <ul>
    * <li><b>Model:</b> Date[]</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see Date[]

     */
    instantsArray = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/instantArray", _path, null);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/testModel</b>
    * <ul>
    * <li><b>Model:</b> TestModel</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see TestModel

     */
    testModel = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/testModel", _path, null);
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
    testObject = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/testObject", _path, null);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/instants</b>
    * <ul>
    * <li><b>Model:</b> Date[]</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see Date[]

     */
    instants = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/instants", _path, null);
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
    instant = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/instant", _path, null);
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
    object = (form) => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/object", _path, form);
    }


    /**
        * 
     *
        * <div class='http-info'>http 说明：<b>Api Url:</b> <b>/test/longs</b>
    * <ul>
    * <li><b>Model:</b> number[]</li>
    * <li>需要登录</li>
    * </ul>
    * </div>
    * @see number[]

     */
    longs = () => {
        let _path = null;
        return super._request("test", "testService", "POST", "/test/longs", _path, null);
    }

}

export { TestService };
const testService = new TestService();
testService._init(requestGroupImpi);
export default testService;

