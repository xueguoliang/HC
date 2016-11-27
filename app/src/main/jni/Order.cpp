//
// Created by 薛国良 on 2016/11/27.
//

#include "Order.h"
#include "Util.h"
#include "Def.h"
#include "Curl.h"
#include "Data.h"

Order::Order() {

}

Order* Order::instance() {
    static Order* This = new Order;
    return This;
}

/*
 *  {
 *      cmd: startorder,
 *      session: xxx-xxx-xxx-xxxxx,
 *      start:{
 *          lng:xxx,
 *          lat:xxx
 *      }
 *      end:{
 *          lng:xxx,
 *          lat:xxxx
 *      }
 *  }
 * */

bool Order::start(double lng1, double lat1, double lng2, double lat2) {

    Json jObj;

    Json pos;
    pos.insert(HC_LNG, Util::toString(lng1));
    pos.insert(HC_LAT, Util::toString(lat1));
    jObj.insert(HC_START, pos);

    pos.insert(HC_LNG, Util::toString(lng2));
    pos.insert(HC_LAT, Util::toString(lat2));
    jObj.insert(HC_END, pos);

    jObj.insert(HC_CMD, HC_START_ORDER);
    jObj.insert(HC_SESSION, Data::instacne()->_session);

    return execute(jObj);
}

bool Order::execute(Json& obj, Json* pResp) {
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