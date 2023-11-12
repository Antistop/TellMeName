package com.hanhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanhan.entity.PKey;

public interface KeyMapper extends BaseMapper<PKey> {
    int deletePKeyByCreatTime();
}
