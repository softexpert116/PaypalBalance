//
//  Utils.swift
//  PaypalBalance
//
//  Created by ujs on 11/19/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import Foundation
import UIKit

class Utils: NSObject {
//    static let KEY = "MY ACCOUNTS"
////    static var array_account = NSMutableArray.init()
//    override init() {
//        
//    }
//    static func load_accounts_array() {
//        array_account = get_accounts_array(key: KEY)
//    }
//    static func update_accounts_array() {
//        set_accounts_array(accounts: array_account, key: KEY)
//    }
//    static func set_accounts_array (accounts:NSMutableArray, key:String) {
//        let userDefaults = UserDefaults.standard
//        let encodedData: Data = NSKeyedArchiver.archivedData(withRootObject: accounts)
//        userDefaults.set(encodedData, forKey: key as String)
//        userDefaults.synchronize()
//    }
//    static func get_accounts_array (key:String)->NSMutableArray  {
//        let userDefaults = UserDefaults.standard
//        if userDefaults.object(forKey: key as String) == nil {
//            return NSMutableArray.init()
//        }
//        let decoded  = userDefaults.object(forKey: key as String) as! Data
//        let decodedTeams = NSKeyedUnarchiver.unarchiveObject(with: decoded) as! NSMutableArray
//        return decodedTeams
//    }
    static func showAlert(title:String, message:String) {
         let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
//        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel, handler: nil))
        
        // show the alert
        getTopViewController()?.present(alert, animated: true, completion: nil)
    }
    static func getTopViewController() -> UIViewController? {
        var topController: UIViewController? = UIApplication.shared.keyWindow?.rootViewController
        while topController?.presentedViewController != nil {
            topController = topController?.presentedViewController
        }
        return topController
    }
    
}
