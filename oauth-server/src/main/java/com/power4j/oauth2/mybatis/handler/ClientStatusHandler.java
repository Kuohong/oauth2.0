package com.power4j.oauth2.mybatis.handler;


import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import com.power4j.oauth2.common.pojo.ClientStatus;

/**
 * Created by cute on 2015/10/13.
 */
public class ClientStatusHandler  extends EnumOrdinalTypeHandler<ClientStatus> {
    public ClientStatusHandler(Class<ClientStatus> type) {
        super(type);
    }
}
