import TestModel from './core/TestModel'
import GenericForm from './form/GenericForm'
import ObjectForm from './form/ObjectForm'
import ValidForm from './form/ValidForm'
import GenericModel from './model/GenericModel'
import ObjectModel from './model/ObjectModel'
import ValidModel from './model/ValidModel'

import {AbstractApi} from 'apikit-core'

import {requestGroupImpi} from 'apikit-core'


/**
 * 
*/
declare class TestService extends AbstractApi {



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
    longValue():Promise<number>;



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
    generic(form:GenericForm):Promise<GenericModel>;



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
    valid(form:ValidForm):Promise<ValidModel>;



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
    instantsArray():Promise<Date[]>;



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
    testModel():Promise<TestModel>;



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
    testObject():Promise<ObjectModel>;



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
    instants():Promise<Date[]>;



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
    instant():Promise<Date>;



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
    object(form:ObjectForm):Promise<ObjectModel>;



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
    longs():Promise<number[]>;

}
export { TestService };
declare const testService: TestService;
export default testService;