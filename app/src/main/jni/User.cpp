//
// Created by 薛国良 on 2016/11/24.
//

#include "User.h"
#include "Json.h"
#include "Util.h"
#include "Curl.h"
#include "Def.h"
#include "Data.h"

User::User() { }

User* User::instance() {
    static User* obj = new User;
    return obj;
}

bool User::Login(string username, string password, string type) {
    Json jObj;

    jObj.insert(HC_CMD, HC_LOGIN);
    jObj.insert(HC_USERNAME, username);
    jObj.insert(HC_PASSWORD, password);
    jObj.insert(HC_LOGINTYPE, type);

    Json resp;

    bool ret = execute(jObj, &resp);
    if(ret)
    {
        // 保存服务器返回的session信息
        Data::instacne()->_session = resp.value(HC_SESSION);
        HC_LOG("save session %s\n", resp.value(HC_SESSION).c_str());
    }

    return ret;
}

bool User::Reg(string username, string password, string mobile, string email, string id) {

    Json jObj;

    jObj.insert(HC_CMD, HC_REG);
    jObj.insert(HC_USERNAME, username);
    jObj.insert(HC_PASSWORD, password);
    jObj.insert(HC_MOBILE, mobile);
    jObj.insert(HC_EMAIL, email);
    jObj.insert(HC_ID, id);

    return execute(jObj);
}

bool User::execute(Json& obj, Json* pResp) {
    string jsonBuf = obj.print();

    HC_LOG("%s\n", jsonBuf.c_str());
    // send to server ...

    Json tmpResp;
    Json* resp = &tmpResp;
    if(pResp) resp = pResp;

    Curl curl(HC_URL);
    bool bOK = curl.execute(jsonBuf);
    if(bOK)
    {
        string respData = curl.responseData();


        resp->parse(respData);

        string result = resp->value(HC_RESULT);
        if(result == HC_OK)
        {
            return true;
        }
        else
        {
            string reason = resp->value(HC_REASON);
            HC_LOG("error: %s\n", reason.c_str());
        }
    }
    return false;
}

void User::LocationChange(double lng, double lat) {
    Json jObj;
    jObj.insert(HC_CMD, HC_LOCATION_CHANGE);
    jObj.insert(HC_LNG, Util::toString(lng));
    jObj.insert(HC_LAT, Util::toString(lat));
    jObj.insert(HC_SESSION, Data::instacne()->_session);

    execute(jObj);
}