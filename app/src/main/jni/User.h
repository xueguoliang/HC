//
// Created by 薛国良 on 2016/11/24.
//

#ifndef HC_USER_H
#define HC_USER_H

#include <string>
using namespace std;

class User {

private:
    User();

public:
    static User* instance();
    bool Login(string username, string password);
};


#endif //HC_USER_H
