import BaseObj from '../core/BaseObj'
import TestModel from '../core/TestModel'
import TestArrayModel from './TestArrayModel'
import TestWrapperModel from './TestWrapperModel'


/**
 * 
*/
interface ObjectModel {

    test?:TestModel;

    testArray?:TestArrayModel;

    testWrapper?:TestWrapperModel;

    baseName?:Object;
}

export default ObjectModel;