package org.aooshi.j.numberserver.client;

import org.aooshi.j.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * NumberServer.ApiUrl=http://192.168.199.85:8021/
 * NumberServer.ApiUser=1
 * NumberServer.ApiPass=4pzPwZ45ajMMbhQpmwPJsZZc757SHHpG
 */
@Component
public class NumberServerClient {

    /**
     * config : NumberServer.ApiUrl
     */
    @Value("${NumberServer.ApiUrl}")
    private String apiUrl;
    /**
     * config : NumberServer.ApiUser
     */
    @Value("${NumberServer.ApiUser}")
    private String apiUser ;

    /**
     * config : NumberServer.ApiPass
     */
    @Value("${NumberServer.ApiPass}")
    private String apiPass;


    private String lastErrorMessage = "";

    /**
     * config : NumberServer.ApiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * config : NumberServer.ApiUrl
     */
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    /**
     * config : NumberServer.ApiUser
     */
    public String getApiUser() {
        return apiUser;
    }

    /**
     * config : NumberServer.ApiUser
     */
    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    /**
     * config : NumberServer.ApiPass
     */
    public String getApiPass() {
        return apiPass;
    }

    /**
     * config : NumberServer.ApiPass
     */
    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

    /**
     * get last error message
     * @return
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }


    /**
     * POST: URL: /add DATA: id=00000&value=1
     * <p>
     * Not Find: HTTP 404
     * <p>
     * 添加一个标识，如果已存在则将失败
     *
     * @param id
     * @param value
     * @return
     * @throws IOException
     */
    public Boolean add(long id, long value) throws IOException {
        String auth = this.getAuth();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("value", value);
        String doPost = HttpClientUtils.doPost(auth, this.apiUrl + "/add", paramMap);
        if (doPost.equals("ok")) {
            return true;
        } else {
            this.lastErrorMessage = doPost;
            return false;
        }
    }

    /**
     * POST:
     * <p>
     * URL: /update DATA: id=00000&value=00000
     * <p>
     * Not Find: HTTP 404
     * <p>
     * 将一个标识值置换成为新值
     *
     * @param id
     * @param value
     * @return
     */
    public Boolean update(long id, long value) {
        String auth = this.getAuth();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("value", value);
        String doPost = HttpClientUtils.doPost(auth, this.apiUrl + "/update", paramMap);
        if (doPost.equals("ok")) {
            return true;
        } else {
            this.lastErrorMessage = doPost;
            return false;
        }
    }

    /**
     * POST:
     * <p>
     * URL: /delete DATA: id=00000
     * <p>
     * Not Find: HTTP 404
     * <p>
     * 删除一个标识
     *
     * @param id
     * @return
     */
    public Boolean delete(long id) {
        String auth = this.getAuth();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        String doPost = HttpClientUtils.doPost(auth, this.apiUrl + "/delete", paramMap);
        if (doPost.equals("ok")) {
            return true;
        } else {
            this.lastErrorMessage = doPost;
            return false;
        }
    }

    /**
     * GET: URL: /get?id=0000
     * <p>
     * Not Find: HTTP 404
     * <p>
     * 获取一个标识（不会引发标识的值变化）
     *
     * @param id
     * @return
     */
    public Long get(long id) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/get?id=" + id);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET: URL: /increment?id=0000&step=1
     * <p>
     * <p>
     * Not Find: HTTP 404
     *
     * @param id
     * @param step
     * @return
     */
    public Long increment(long id, int step) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/increment?id=" + id + "&step=" + step);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET: URL: /decrement?id=0000&step=1
     * <p>
     * Not Find: HTTP 404
     *
     * @param id
     * @param step
     * @return
     */
    public Long decrement(long id, int step) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/decrement?id=" + id
                + "&step=" + step);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET: URL: /getOrAdd?id=0000&default=1
     * <p>
     * 获取一个标识（不会引发标识的值变化）
     *
     * @param id
     * @param defaultValue
     * @return
     */
    public Long getOrAdd(long id, long defaultValue) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/getOrAdd?id=" + id
                + "&default=" + defaultValue);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET: URL: /incrementOrAdd?id=0000&step=1&default=1
     *
     * @param id
     * @param defaultValue
     * @param step
     * @return
     */
    public Long incrementOrAdd(long id, int step, long defaultValue) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/incrementOrAdd?id=" + id
                + "&step=" + step + "&default=" + defaultValue);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET: URL: /decrementOrAdd?id=0000&step=1&default=1
     *
     * @param id
     * @param defaultValue
     * @param step
     * @return
     */
    public Long decrementOrAdd(long id, int step, long defaultValue) {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/decrementOrAdd?id=" + id
                + "&step=" + step + "&default=" + defaultValue);
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * GET:
     *  URL: /snowflake
     *
     * @return
     */
    public Long snowflake() {
        String auth = this.getAuth();
        String doGet = HttpClientUtils.doGet(auth, this.apiUrl + "/snowflake");
        Long result = Long.valueOf(doGet);
        return result;
    }

    /**
     * get a auth from user and pass
     * @return
     */
    public String getAuth()
    {
        String auth = this.apiUser + ":" + this.apiPass;
        return auth;
    }

}
