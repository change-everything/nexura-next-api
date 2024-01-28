package cn.nexura.nextapi.service;

import cn.nexura.common.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author peiYP
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {


    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 接口调用次数
     * @author PeiYP
     * @since 2024/1/28 11:17
     * @param limit
     * @return java.util.List<cn.nexura.common.model.entity.UserInterfaceInfo>
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}
