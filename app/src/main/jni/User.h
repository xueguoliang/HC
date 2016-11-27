//
// Created by 薛国良 on 2016/11/24.
//

#ifndef HC_USER_H
#define HC_USER_H

#include <string>
#include "Json.h"

using namespace std;

class User {

private:
    User();

public:
    static User* instance();
    bool Login(string username, string password, string type);
    bool Reg(string username, string password, string mobile, string email, string id);
    void LocationChange(double lng, double lat);
    bool execute(Json& obj, Json* resp = NULL);
};


#endif //HC_USER_H
