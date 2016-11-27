//
// Created by 薛国良 on 2016/11/27.
//

#include "Data.h"

Data::Data() {

}


Data* Data::instacne() {
    static Data* data = new Data;
    return data;
}