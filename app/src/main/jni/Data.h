//
// Created by 薛国良 on 2016/11/27.
//

#ifndef HC_DATA_H
#define HC_DATA_H

#include <string>
using namespace std;

class Data {
private:
    Data();

public:
    static Data* instacne();
    string _session;
};


#endif //HC_DATA_H
