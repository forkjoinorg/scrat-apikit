import TestForm from '../core/TestForm'
import TestArrayForm from './TestArrayForm'
import TestWrapperForm from './TestWrapperForm'


/**
 * 
*/
interface ObjectForm {

    test?:TestForm;

    testArray?:TestArrayForm;

    testWrapper?:TestWrapperForm;
}

export default ObjectForm;