package cn.com.taiji.dto;


import lombok.Data;

/**
 * 返回值对象
 */
@Data
public class ResultDTO {
    private boolean success;
    private Object obj;
    private String msg;
}
