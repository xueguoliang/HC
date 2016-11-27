//
// Created by 薛国良 on 2016/11/27.
//

#ifndef HC_ORDER_H
#define HC_ORDER_H

#include "Json.h"

class Order {
private:
    Order();

public:
    static Order* instance();

    bool start(double lng1, double lat1, double lng2, double lat2);

    bool execute(Json& obj, Json* resp = NULL);
};


#endif //HC_ORDER_H
