package cn.nexura.nextapi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.common.model.entity.UserInterfaceInfo;
import cn.nexura.nextapi.annotation.AuthCheck;
import cn.nexura.nextapi.common.BaseResponse;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.common.ResultUtils;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.model.vo.InterfaceInfoVO;
import cn.nexura.nextapi.service.InterfaceInfoService;
import cn.nexura.nextapi.service.UserInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author PeiYP
 * @since 2024年01月28日 10:54
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private InterfaceInfoService interfaceInfoService;


    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoService.listTopInvokeInterfaceInfo(3);

        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfos.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        LambdaQueryWrapper<InterfaceInfo> wrapper = Wrappers.lambdaQuery(InterfaceInfo.class);
        wrapper.in(InterfaceInfo::getId, interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtil.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(interfaceInfoVOList);
    }

}
