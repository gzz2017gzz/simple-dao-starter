package com.hq.manage.sys.dictitem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hq.manage.sys.dict.vo.DictVO;

/**
 * @author 高振中
 * @类说明 【字典项】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class DictItemService {

	@Autowired
	private DictItemDao dictItemDao; // 注入【字典项】数据访问层

	/**
	 * @方法说明 【字典项】新增
	 */
	public Long save(DictItem dictItem) {
		return dictItemDao.save(dictItem).getDictId();
	}

	/**
	 * @方法说明 【字典项】删除
	 */
	public int delete(Object[] ids) {
		return dictItemDao.delete(ids);
	}

	/**
	 * @方法说明 【字典项】修改新
	 */
	public int update(DictItem dictItem) {
		return dictItemDao.update(dictItem);
	}

	/**
	 * @方法说明 【字典项】全部
	 */
	public Map<String, List<DictVO>> dict() {
		return dictItemDao.listDict().stream().collect(Collectors.groupingBy(DictVO::getCode));
	}

	/**
	 * @方法说明 【字典项】列表
	 */
	public List<DictItem> list(DictItemCond cond) {
		cond.setOrders("order_num");
		return dictItemDao.list(cond);
	}

	/**
	 * @方法说明 【字典项】列表
	 */
	public List<DictVO> listItem(DictItemCond cond) {
		cond.setOrders("order_num");
		return dictItemDao.listItem(cond);
	}

	/**
	 * @方法说明 【字典项】记录是否存在
	 */
	public boolean exists(DictItemCond cond) {
		return dictItemDao.exists(cond);
	}
}
