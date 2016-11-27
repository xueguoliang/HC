//
// Created by 薛国良 on 2016/11/24.
//

#include "Util.h"

string Util::toString(double d) {
    char buf[128];
    sprintf(buf, "%.9f", d);
    return string(buf);
}