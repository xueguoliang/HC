//
// Created by 薛国良 on 2016/11/24.
//

#ifndef HC_JSON_H
#define HC_JSON_H

#include <string>
using namespace std;
#include "cJSON.h"

class Json {
private:
    cJSON* _root;
    Json(const Json&);
    Json &operator=(const Json&);

public:
    Json();
    ~Json();

    void insert(string key, string value);
    void insert(string key, Json& value);
    string print();

    void parse(string json);
    string value(string key);
};


#endif //HC_JSON_H
