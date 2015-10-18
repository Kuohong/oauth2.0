package com.power4j.oauth2.mybatis.handler;


import com.power4j.oauth2.common.pojo.CommonStatus;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

/**
 * Created by cute on 2015/10/13.
 */
public class CommonStatusHandler extends EnumOrdinalTypeHandler<CommonStatus> {
    public CommonStatusHandler(Class<CommonStatus> type) {
        super(type);
    }
}
