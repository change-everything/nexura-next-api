package cn.nexura.nextapi.service.impl;

import cn.nexura.nextapi.mapper.InterfaceInfoParamsMapper;
import cn.nexura.nextapi.model.InterfaceInfoParams;
import cn.nexura.nextapi.model.InterfaceParams;
import cn.nexura.nextapi.service.InterfaceInfoParamsService;
import cn.nexura.nextapi.service.InterfaceParamsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 86188
* @description 针对表【interface_info_params(接口参数信息)】的数据库操作Service实现
* @createDate 2024-01-31 17:44:48
*/
@Service
public class InterfaceInfoParamsServiceImpl extends ServiceImpl<InterfaceInfoParamsMapper, InterfaceInfoParams>
    implements InterfaceInfoParamsService {

    @Resource
    private InterfaceParamsService paramsService;

    @Override
    public List<InterfaceParams> selectParamsByInterfaceId(long id) {

        List<InterfaceInfoParams> interfaceInfoParamsList = list(Wrappers.lambdaQuery(InterfaceInfoParams.class)
                .eq(InterfaceInfoParams::getInterfaceInfoId, id));

        Set<Long> interfaceInfoParamIds = interfaceInfoParamsList.stream().map(InterfaceInfoParams::getInterfaceParamId)
                .collect(Collectors.toSet());


        return paramsService.listByIds(interfaceInfoParamIds);
    }
}




