//
// Created by 薛国良 on 2016/11/24.
//

#include "Json.h"

Json::Json() {
    _root = cJSON_CreateObject();
}

Json::~Json() {
    cJSON_Delete(_root);
}

void Json::insert(string key, string value) {
    cJSON_AddItemToObject(_root, key.c_str(), cJSON_CreateString(value.c_str()));
}

string Json::print() {
    char* p = cJSON_Print(_root);
    string ret(p);
    free(p);
    return ret;
}

void Json::parse(string json) {
    cJSON* root = cJSON_Parse(json.c_str());
    cJSON_Delete(_root);
    _root = root;
}

string Json::value(string key) {
    cJSON* obj = cJSON_GetObjectItem(_root, key.c_str());
    if(obj == NULL)
    {
        return string();
    }

    return string(obj->valuestring);
}
