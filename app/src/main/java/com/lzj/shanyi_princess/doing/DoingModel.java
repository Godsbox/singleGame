/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyi_princess.doing;

import com.lzj.arch.core.Model;

/**
 * 正在做表现模型。
 *
 * @author 吴吉林
 */
public class DoingModel extends Model {

    /**
     * 提示消息。
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
