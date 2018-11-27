//
//  TodayViewController.swift
//  Widget
//
//  Created by ujs on 11/22/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import UIKit
import NotificationCenter
import Alamofire

class TodayViewController: UIViewController, NCWidgetProviding, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var label_title: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    let serverUrl = "http://test116.net.preview.oxito.com/index.php"
    //    let serverUrl = "http://192.168.0.107/paypal/index.php"
    var timer: Timer? = nil
    var index: Int = -1
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if Global.array_account.count == 0 {
            self.tableView.setEmptyMessage("No Account")
        } else {
            self.tableView.restore()
        }
        return Global.array_account.count
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 40
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell_widget", for: indexPath) as! WidgetTableViewCell
        let account = Global.array_account.object(at: indexPath.row) as! AccountModel
        cell.label_username.text = account.username
        cell.label_balance.text = account.balance
        cell.accessoryType = UITableViewCell.AccessoryType.none;
        return cell
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        Global.load_accounts_array()
        if Global.read_flag(key: "FLAG") {
            timer = Timer.scheduledTimer(timeInterval: 10, target: self, selector: #selector(self.send_request), userInfo: nil, repeats: true)
        }
        self.tableView.reloadData()
//        NotificationCenter.default.addObserver(self, selector: #selector(userDefaultsDidChange), name: UserDefaults.didChangeNotification, object: nil)

        self.preferredContentSize = CGSize(width:self.view.frame.size.width, height:110)
        if #available(iOSApplicationExtension 10.0, *) {
            self.extensionContext?.widgetLargestAvailableDisplayMode = .expanded
        }
    }
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        if timer != nil {
            timer!.invalidate()
        }
    }
    @objc func send_request() {
        if Global.array_account.count == 0 {
            return
        }
        index += 1
        if index >= Global.array_account.count {
            index = 0
        }
        let model = Global.array_account.object(at: index) as! AccountModel
        Alamofire.request(serverUrl, method: .post, parameters: ["type": "GetBalance", "username": model.username, "password": model.password, "signature": model.signature],encoding: URLEncoding.httpBody)
            .validate(statusCode: 200..<300)
            .responseJSON { response in
                switch response.result {
                case .success(let JSON):
                    let response = JSON as! NSDictionary
                    //example if there is an id
                    let code = response.object(forKey: "status") as! NSNumber
                    if code == 200 {
                        let data = response.object(forKey: "data") as! NSDictionary
                        model.username = data.object(forKey: "username") as! String
                        model.balance = data.object(forKey: "balance") as! String
                        model.currency = data.object(forKey: "currency") as! String
                        model.date = data.object(forKey: "date") as! String
                    } else {
                        model.balance = "Error"
                    }
//                    self.tableView.reloadData()
                    break
                case .failure(let error):
                    //                    self.view.makeToast(error as? String)
                    model.balance = "Error"
//                    self.tableView.reloadData()
                    break
                }
                self.tableView.reloadData()
        }
    }
    @objc func userDefaultsDidChange(notfication:NSNotification) {
        
//        Global.load_accounts_array()
//        self.tableView.reloadData()
    }
    @available(iOS 10.0, *)
    @available(iOSApplicationExtension 10.0, *)
    func widgetActiveDisplayModeDidChange(_ activeDisplayMode: NCWidgetDisplayMode, withMaximumSize maxSize: CGSize) {
        let height = CGFloat(40*Global.array_account.count+50)
        if activeDisplayMode == .expanded {
            self.preferredContentSize = CGSize(width: self.view.frame.size.width, height: height)
        }else if activeDisplayMode == .compact{
            self.preferredContentSize = CGSize(width: maxSize.width, height: 110)
        }
    }
    
    @IBAction func clickSetting(_ sender: Any) {
        let url: URL? = URL(string: "open://1")!
        if let appurl = url { self.extensionContext!.open(appurl, completionHandler: nil) }
    }
    
    func widgetPerformUpdate(completionHandler: (@escaping (NCUpdateResult) -> Void)) {
        // Perform any setup necessary in order to update the view.
        
        // If an error is encountered, use NCUpdateResult.Failed
        // If there's no update required, use NCUpdateResult.NoData
        // If there's an update, use NCUpdateResult.NewData
        
        completionHandler(NCUpdateResult.newData)
    }
    
}
extension UITableView {
    
    func setEmptyMessage(_ message: String) {
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height))
        messageLabel.text = message
        messageLabel.textColor = .white
        messageLabel.numberOfLines = 0;
        messageLabel.textAlignment = .center;
        messageLabel.font = UIFont(name: "TrebuchetMS", size: 15)
        messageLabel.sizeToFit()
        
        self.backgroundView = messageLabel;
        self.separatorStyle = .none;
    }
    
    func restore() {
        self.backgroundView = nil
        self.separatorStyle = .singleLine
    }
}
