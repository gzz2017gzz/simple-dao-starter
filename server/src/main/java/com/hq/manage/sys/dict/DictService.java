package com.hq.manage.sys.dict;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hq.manage.sys.dict.vo.DictVO;
import com.hq.manage.sys.dictitem.DictItemDao;
import com.simple.common.base.Page;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;

/**
 * @author 高振中
 * @类说明 【枚举字典】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class DictService {

	private static Map<String, Map<Long, String>> dictCacheMap;
	@Autowired
	private DictDao dictDao; // 注入【字典】数据访问层
	@Resource
	private DictItemDao dictItemDao;

	/**
	 * 初始化字典缓存
	 */
	public void initDictMap() {
		if (!CollectionUtils.isEmpty(dictCacheMap)) {
			return;
		}
		refresh();
	}

	/**
	 * 刷新字典
	 */
	public void refresh() {
		List<DictVO> dictVOS = dictItemDao.listDict();
		dictCacheMap = dictVOS.stream().collect(Collectors.groupingBy(DictVO::getCode, Collectors.toMap(DictVO::getItemValue, DictVO::getText, (v1, v2) -> v2)));
	}

	/**
	 * 解析字典值
	 *
	 * @param code      字典编码
	 * @param itemValue 字典值
	 * @return String 字典值内容
	 */
	@SneakyThrows
	public String parseDict(String code, Long itemValue) {
		if (Objects.isNull(itemValue) || Strings.isBlank(code)) {
			return Strings.EMPTY;
		}

		this.initDictMap();
		Map<Long, String> dictMap = dictCacheMap.get(code);
		if (Objects.isNull(dictMap)) {
			return itemValue.toString();
		}

		return dictMap.getOrDefault(itemValue, itemValue.toString());
	}

	/**
	 * @方法说明 新增【枚举字典】
	 */
	public Long save(Dict dict) {
		return dictDao.save(dict).getId();
	}

	/**
	 * @方法说明 删除【枚举字典】
	 */
	public int delete(Object[] ids) {
		return dictDao.delete(ids);
	}

	/**
	 * @方法说明 更新【枚举字典】
	 */
	public int update(Dict dict) {
		return dictDao.update(dict);
	}

	/**
	 * @方法说明 【枚举字典】分页列表
	 */
	public Page<Dict> page(DictCond cond) {
		cond.setOrders("id DESC");
		return dictDao.page(cond);
	}

	/**
	 * @方法说明 【枚举字典】记录是否存在
	 */
	public boolean exists(DictCond cond) {
		return dictDao.exists(cond);
	}
}
