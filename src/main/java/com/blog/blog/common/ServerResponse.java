package com.blog.blog.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/*
 * @author JavisChen
 * @desc 服务端响应结果封装
 * @date 2018/4/17 下午10:24
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = -5409913864886373072L;
    private int code;
    private String msg;
    private T data;

//    @JSONField(serialize = false)
//    public boolean isOK() {
//        return this.code == ResponseEnums.OK.getCode();
//    }

    public ServerResponse(ResponseEnums responseEnums, T data) {
        this(responseEnums.getCode(), responseEnums.getMsg(), data);
    }

    public ServerResponse(ResponseEnums responseEnums) {
        this(responseEnums.getCode(), responseEnums.getMsg(), null);
    }

    public ServerResponse(int code, String msg) {
        this(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public ServerResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    private ServerResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ServerResponse setData(T data) {
        this.data = data;
        return this;
    }


    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseEntity<ServerResponse> ok() {
        return ResponseEntity.ok((new ServerResponse<>().setCode(ResponseEnums.OK.getCode()).setMsg(ResponseEnums.OK.getMsg())));
    }

    public static <T> ResponseEntity<ServerResponse> ok(T data) {
        return ResponseEntity.ok(new ServerResponse<>().setCode(ResponseEnums.OK.getCode()).setMsg(ResponseEnums.OK.getMsg()).setData(data));
    }

    public static ResponseEntity<ServerResponse> error() {
        return new ResponseEntity<>(new ServerResponse<>().setCode(ResponseEnums.ERROR.getCode()).setMsg(ResponseEnums.ERROR.getMsg()), HttpStatus.OK);
    }

    public static ResponseEntity<ServerResponse> error(String msg) {
        return new ResponseEntity<>(new ServerResponse<>().setCode(ResponseEnums.ERROR.getCode()).setMsg(msg), HttpStatus.OK);
    }

    public static ResponseEntity<ServerResponse> clientError() {
        return new ResponseEntity<>(new ServerResponse<>().setCode(ResponseEnums.PARAM_.getCode()).setMsg(ResponseEnums.PARAM_.getMsg()), HttpStatus.OK);
    }

    public static ResponseEntity<ServerResponse> clientError(String msg) {
        return new ResponseEntity<>(new ServerResponse<>().setCode(ResponseEnums.PARAM_.getCode()).setMsg(msg), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ServerResponse> clientError(int code, String msg) {
        return new ResponseEntity<>(new ServerResponse<>().setCode(code).setMsg(msg), HttpStatus.BAD_REQUEST);
    }
}
