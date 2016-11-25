//
// Created by 薛国良 on 2016/11/24.
//

#include "User.h"
#include "Json.h"
#include "Util.h"
#include "Curl.h"
#include "Def.h"

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

    return execute(jObj);
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

bool User::execute(Json& obj) {
    string jsonBuf = obj.print();

    HC_LOG("%s\n", jsonBuf.c_str());
    // send to server ...

    Curl curl(HC_URL);
    bool bOK = curl.execute(jsonBuf);
    if(bOK)
    {
        string respData = curl.responseData();

        Json respJson;
        respJson.parse(respData);

        string result = respJson.value(HC_RESULT);
        if(result == HC_OK)
        {
            return true;
        }
        else
        {
            string reason = respJson.value(HC_REASON);
            HC_LOG("error: %s\n", reason.c_str());
        }
    }
    return false;
}