package cn.nexura.nextapi.service;

import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.nextapi.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author peiyp
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-01-18 17:09:22
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {


    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);


    List<InterfaceInfoVO> listInterface(List<InterfaceInfo> interfaceInfoList);
}
