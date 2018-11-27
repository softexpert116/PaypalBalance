//
//  AccountModel.swift
//  PaypalBalance
//
//  Created by ujs on 11/22/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import Foundation

class AccountModel: NSObject, NSCoding {
    
    var username:String = ""
    var password:String = ""
    var signature:String = ""
    var balance:String = ""
    var currency:String = ""
    var date:String = ""
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(username, forKey: "username")
        aCoder.encode(password, forKey: "password")
        aCoder.encode(signature, forKey: "signature")
        aCoder.encode(balance, forKey: "balance")
        aCoder.encode(currency, forKey: "currency")
        aCoder.encode(date, forKey: "date")
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        let username = aDecoder.decodeObject(forKey: "username") as! String
        let password = aDecoder.decodeObject(forKey: "password") as! String
        let signature = aDecoder.decodeObject(forKey: "signature") as! String
        let balance = aDecoder.decodeObject(forKey: "balance") as! String
        let currency = aDecoder.decodeObject(forKey: "currency") as! String
        let date = aDecoder.decodeObject(forKey: "date") as! String
        self.init(username: username, password: password, signature: signature, balance: balance, currency: currency, date: date)
    }
    
    
    init(username:String, password:String, signature:String, balance:String, currency:String, date:String) {
        super.init()
        self.username = username
        self.password = password
        self.signature = signature
        self.balance = balance
        self.currency = currency
        self.date = date
    }
}
