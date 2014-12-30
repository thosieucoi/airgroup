package com.airgroup.controller.customer_email

import com.airgroup.domain.CustomerEmail;

class CustomerEmailController {

    def listBackEnd = {
        [listEmail: CustomerEmail.list(params)]
    }

}
