/*
 * screw-server - 简洁好用的数据库表结构文档生成工具
 * Copyright © 2020 SanLi (qinggang.zuo@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dqv5.screw.server.pojo;

import lombok.Data;

/**
 * @author duq
 * @date 2024/6/7
 */
@Data
public class CommonResponse<T> {
    private int    code;
    private String msg;
    private T      data;

    public static <T> CommonResponse<T> build(boolean success, String msg) {
        return build(success, msg, null);
    }

    public static <T> CommonResponse<T> build(boolean success, String msg, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(success ? 200 : 500);
        commonResponse.setMsg(msg);
        commonResponse.setData(data);
        return commonResponse;
    }
}
