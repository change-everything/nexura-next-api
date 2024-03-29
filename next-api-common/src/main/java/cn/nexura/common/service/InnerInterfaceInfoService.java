package cn.nexura.common.service;


import cn.nexura.common.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 内部接口服务
 * @author peiyp
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2024-01-18 17:09:22
 */
public interface InnerInterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

    /**
     * 检测接口是否存活
     */
    InterfaceInfo testInterfaceAlive(String path, String method);

}
