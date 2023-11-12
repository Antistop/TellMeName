package com.hanhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanhan.entity.CipherText;

public interface CipherTextMapper extends BaseMapper<CipherText> {
    int deleteCipherTextByCreatTime();
}
