//
//  Global.swift
//  PaypalBalance
//
//  Created by ujs on 11/22/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import Foundation

class Global: NSObject {
    static let KEY = "MY ACCOUNTS"
    static let company = "group.clevery.TodayExtensionSharingDefaults"
    static var array_account = NSMutableArray.init()
    override init() {
        
    }
    
    static func update_accounts_array () {
        let array = NSMutableArray.init()
        if array_account.count > 0 {
            for i in 0...array_account.count-1 {
                let account = array_account.object(at: i) as! AccountModel
                let dict: [String:String] = ["username":account.username, "password":account.password, "signature":account.signature, "balance":account.balance, "currency":account.currency, "date":account.date]
                array.add(dict)
            }
        }
        let userDefaults = UserDefaults(suiteName: company)
        let encodedData: Data = NSKeyedArchiver.archivedData(withRootObject: array)
        userDefaults!.set(encodedData, forKey: KEY as String)
        userDefaults!.synchronize()
    }
    static func load_accounts_array () {
        array_account = NSMutableArray.init()
        let userDefaults = UserDefaults(suiteName: company)
        if userDefaults!.object(forKey: KEY as String) == nil {
            return
        }
        let decoded  = userDefaults!.object(forKey: KEY as String) as! Data
        let array = NSKeyedUnarchiver.unarchiveObject(with: decoded) as! NSArray
        if array.count > 0 {
            for i in 0...array.count-1 {
                let dict = array.object(at: i) as! NSDictionary
                let account = AccountModel(username: dict.value(forKey: "username") as! String, password: dict.value(forKey: "password") as! String, signature: dict.value(forKey: "signature") as! String, balance: dict.value(forKey: "balance") as! String, currency: dict.value(forKey: "currency") as! String, date: dict.value(forKey: "date") as! String)
                array_account.add(account)
            }
        }
    }
    static func set_flag(value: Bool, key: String) {
        let userDefaults = UserDefaults(suiteName: company)
        userDefaults?.set(value, forKey: key)
        userDefaults?.synchronize()
    }
    static func read_flag(key: String)->Bool {
        let userDefaults = UserDefaults(suiteName: company)
        userDefaults?.synchronize()
        let value = userDefaults?.bool(forKey: key)
        return value!
    }
}
