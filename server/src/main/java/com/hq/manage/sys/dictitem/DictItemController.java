package com.hq.manage.sys.dictitem;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.dict.vo.DictVO;

/**
 * @author 高振中
 * @类说明 【字典项】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("dictItem")
public class DictItemController {

	@Autowired
	private DictItemService dictItemService; // 注入【字典项】业务逻辑层

	@PostMapping("save")
	@AutoLog("【字典项】新增")
	@CheckAdmin
	public Result<Long> save(@RequestBody @Validated DictItem dictItem) {
		if (dictItemService.exists(DictItemCond.builder().itemValue(dictItem.getItemValue()).dictId(dictItem.getDictId()).build())) {
			return error("【字典项】编码不能重复！");
		}
		if (dictItemService.exists(DictItemCond.builder().textEq(dictItem.getText()).dictId(dictItem.getDictId()).build())) {
			return error("【字典项】文本不能重复！");
		}
		return success(dictItemService.save(dictItem));
	}

	@PostMapping("delete")
	@AutoLog("【字典项】删除")
	@CheckAdmin
	public Result<Integer> delete(@RequestBody Long[] ids) {
		return success(dictItemService.delete(ids));
	}

	@AutoLog("【字典项】修改")
	@PostMapping("update")
	@CheckAdmin
	public Result<Integer> update(@RequestBody @Validated DictItem dictItem) {
		if (dictItemService.exists(DictItemCond.builder().itemValue(dictItem.getItemValue()).dictId(dictItem.getDictId()).idNe(dictItem.getId()).build())) {
			return error("【字典项】编码不能重复！");
		}
		if (dictItemService.exists(DictItemCond.builder().textEq(dictItem.getText()).dictId(dictItem.getDictId()).idNe(dictItem.getId()).build())) {
			return error("【字典项】文本不能重复！");
		}
		return success(dictItemService.update(dictItem));
	}

	@AutoLog("【字典项】列表")
	@PostMapping("list")
	public Result<List<DictItem>> list(@RequestBody DictItemCond cond) {
		return success(dictItemService.list(cond));
	}

	@AutoLog("【字典项】列表(手机)")
	@PostMapping({ "listItem", "out/listItem" })
	public Result<List<DictVO>> listItem(@RequestBody DictItemCond cond) {
		return success(dictItemService.listItem(cond));
	}

	@AutoLog("【字典项】全部数据")
	@PostMapping("all")
	public Result<Map<String, List<DictVO>>> all() {
		return success(dictItemService.dict());
	}
}
