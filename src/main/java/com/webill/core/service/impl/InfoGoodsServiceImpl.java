package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.InfoGoods;
import com.webill.core.service.IInfoGoodsService;
import com.webill.core.service.mapper.InfoGoodsMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * InfoGoods 服务层接口实现类
 *
 */
@Service
public class InfoGoodsServiceImpl extends SuperServiceImpl<InfoGoodsMapper, InfoGoods> implements IInfoGoodsService {


}