package cn.bravedawn.exception;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/22 15:33
 */
public interface CommonError {

    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
