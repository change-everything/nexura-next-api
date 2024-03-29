package cn.nexura.nextapi.service.impl;

import cn.nexura.common.model.entity.UserInterfaceInfo;
import cn.nexura.common.service.InnerUserInterfaceInfoService;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.mapper.InterfaceInfoMapper;
import cn.nexura.nextapi.mapper.UserInterfaceInfoMapper;
import cn.nexura.nextapi.service.UserInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author peiyp
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于 0");
        }
    }
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> wrapper = Wrappers.lambdaQuery(UserInterfaceInfo.class);
        wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        wrapper.eq(UserInterfaceInfo::getUserId, userId);
        UserInterfaceInfo interfaceInfo = this.getOne(wrapper);
        if (interfaceInfo == null) {
            UserInterfaceInfo newUserInterfaceInfo = new UserInterfaceInfo();
            newUserInterfaceInfo.setUserId(userId);
            newUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            boolean save = this.save(newUserInterfaceInfo);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口数据更新失败");
            }
        }

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interface_info_id", interfaceInfoId);
        updateWrapper.eq("user_id", userId);
        updateWrapper.gt("left_num", 0);
        updateWrapper.setSql("left_num = left_num - 1, total_num = total_num + 1");
        return this.update(updateWrapper);
    }

    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return baseMapper.listTopInvokeInterfaceInfo(limit);
    }
}




