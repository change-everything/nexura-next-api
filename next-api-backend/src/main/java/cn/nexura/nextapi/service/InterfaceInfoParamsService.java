package cn.nexura.nextapi.service;


import cn.nexura.nextapi.model.InterfaceInfoParams;
import cn.nexura.nextapi.model.InterfaceParams;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86188
* @description 针对表【interface_info_params(接口参数信息)】的数据库操作Service
* @createDate 2024-01-31 17:44:48
*/
public interface InterfaceInfoParamsService extends IService<InterfaceInfoParams> {

    List<InterfaceParams> selectParamsByInterfaceId(long id);
}
