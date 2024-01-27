package cn.nexura.nextapi.service.impl.inner;

import cn.nexura.common.model.entity.UserInterfaceInfo;
import cn.nexura.common.service.InnerUserInterfaceInfoService;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.mapper.UserInterfaceInfoMapper;
import cn.nexura.nextapi.service.UserInterfaceInfoService;
import cn.nexura.nextapi.service.UserService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author peiyp
*/
@DubboService
public class InnerUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;



    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}




