package com.webill.core.service.impl;

import org.springframework.stereotype.Service;

import com.webill.core.model.MemberGoods;
import com.webill.core.service.IMemberGoodsService;
import com.webill.core.service.mapper.MemberGoodsMapper;
import com.webill.framework.service.impl.SuperServiceImpl;

/**
 *
 * MemberGoods 服务层接口实现类
 *
 */
@Service
public class MemberGoodsServiceImpl extends SuperServiceImpl<MemberGoodsMapper, MemberGoods> implements IMemberGoodsService {


}