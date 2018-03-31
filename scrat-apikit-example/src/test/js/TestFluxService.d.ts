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
declare class TestFluxService extends AbstractApi {



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
    generic(form:GenericForm):Promise<GenericModel>;



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
    valid(form:ValidForm):Promise<ValidModel>;



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
    testObject():Promise<ObjectModel>;



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
    validById(form:ValidForm, id2:string, id:string):Promise<ValidModel>;



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
    instants():Promise<Date[]>;



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
    instant():Promise<Date>;



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
    object(form:ObjectForm):Promise<ObjectModel>;

}
export { TestFluxService };
declare const testFluxService: TestFluxService;
export default testFluxService;