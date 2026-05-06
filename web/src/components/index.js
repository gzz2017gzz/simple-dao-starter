/* 注册自定义组件 */
import Vue from 'vue';
import vPage from './vPage';
import vDeptTree from './vDeptTree';
import vSelect from './vSelect';
import vButton from './vButton';
import vUpload from './vUpload';
import vTable from './vTable';
import vUserPage from './vUserPage';
import vUserSelect from './vUserSelect';
import vColumns from './vColumns';
import vFooter from './vFooter';
import tSelect from './tSelect';
import vSelectTable from './vSelectTable';

Vue.component(vPage.name, vPage);
Vue.component(vDeptTree.name, vDeptTree);
Vue.component(vSelect.name, vSelect);
Vue.component(vButton.name, vButton);
Vue.component(vUpload.name, vUpload);
Vue.component(vTable.name, vTable);
Vue.component(vUserPage.name, vUserPage);
Vue.component(vUserSelect.name, vUserSelect);
Vue.component(vColumns.name, vColumns);
Vue.component(vFooter.name, vFooter);
Vue.component(tSelect.name, tSelect);
Vue.component(vSelectTable.name, vSelectTable);