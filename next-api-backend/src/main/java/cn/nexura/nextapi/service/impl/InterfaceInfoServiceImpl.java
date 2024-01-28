package cn.nexura.nextapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.common.model.entity.User;
import cn.nexura.common.service.InnerInterfaceInfoService;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.model.vo.InterfaceInfoVO;
import cn.nexura.nextapi.service.InterfaceInfoService;
import cn.nexura.nextapi.mapper.InterfaceInfoMapper;
import cn.nexura.nextapi.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author peiyp
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-01-18 17:09:22
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Resource
    private UserService userService;

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

    @Override
    public List<InterfaceInfoVO> listInterface(QueryWrapper<InterfaceInfo> queryWrapper) {
        List<InterfaceInfo> interfaceInfoList = this.list(queryWrapper);
        List<Long> userIds = interfaceInfoList.stream()
                .map(InterfaceInfo::getUserId)
                .collect(Collectors.toList());

        List<User> users = userService.listByIds(userIds);
        Map<Long, List<User>> userIdObjMap = users.stream().collect(Collectors.groupingBy(User::getId));

        return interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtil.copyProperties(interfaceInfo, interfaceInfoVO);
            interfaceInfoVO.setUserName(userIdObjMap.get(interfaceInfo.getUserId()).get(0).getUserName());
            return interfaceInfoVO;
        }).collect(Collectors.toList());

    }

}




