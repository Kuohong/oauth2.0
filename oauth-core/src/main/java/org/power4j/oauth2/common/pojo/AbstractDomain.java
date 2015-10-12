package org.power4j.oauth2.common.pojo;


import org.power4j.oauth2.common.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kuo Hong
 */
public abstract class AbstractDomain implements Serializable {


    /**
     * The domain create time.
     */
    protected Date createTime = DateUtils.now();

    public AbstractDomain() {
    }


    public Date createTime() {
        return createTime;
    }

    @SuppressWarnings("unchecked") public <T extends AbstractDomain> T createTime(Date createTime) {
        this.createTime = createTime;
        return (T) this;
    }

}
