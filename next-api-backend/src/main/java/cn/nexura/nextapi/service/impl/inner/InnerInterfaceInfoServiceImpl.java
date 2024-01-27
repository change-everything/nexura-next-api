package cn.nexura.nextapi.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.common.model.entity.User;
import cn.nexura.common.service.InnerInterfaceInfoService;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.model.enums.InterfaceStatusEnum;
import cn.nexura.nextapi.service.InterfaceInfoService;
import cn.nexura.nextapi.mapper.InterfaceInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author peiyp
 */
@DubboService
public class InnerInterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InnerInterfaceInfoService {

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if(StrUtil.isAllBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<InterfaceInfo> wrapper = Wrappers.lambdaQuery(InterfaceInfo.class);
        wrapper.eq(InterfaceInfo::getUrl, path).eq(InterfaceInfo::getMethod, method).eq(InterfaceInfo::getStatus, InterfaceStatusEnum.ONLINE.getValue());
        return baseMapper.selectOne(wrapper);
    }
}




