package cn.nexura.nextapi.service.impl;

import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.exception.BusinessException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.nexura.nextapi.model.entity.InterfaceInfo;
import cn.nexura.nextapi.service.InterfaceInfoService;
import cn.nexura.nextapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author peiyp
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-01-18 17:09:22
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

}




