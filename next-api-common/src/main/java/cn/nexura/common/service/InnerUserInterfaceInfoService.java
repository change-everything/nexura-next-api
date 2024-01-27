package cn.nexura.common.service;

import cn.nexura.common.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 内部用户接口服务
 * @author peiYP
 */
public interface InnerUserInterfaceInfoService extends IService<UserInterfaceInfo> {


    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
