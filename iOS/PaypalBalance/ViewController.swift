//
//  ViewController.swift
//  PaypalBalance
//
//  Created by ujs on 11/19/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import UIKit
import Toast_Swift

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tbl_list: UITableView!
    
    @IBOutlet weak var view_dialog: UIView!
    @IBOutlet weak var btn_add: UIButton!
    @IBOutlet weak var btn_start: UIButton!
    @IBOutlet weak var txt_username: UITextField!
    @IBOutlet weak var txt_password: UITextField!
    @IBOutlet weak var txt_signature: UITextField!
    @IBOutlet weak var btn_dialog_add: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        tbl_list.reloadData()
        tbl_list.tableFooterView = UIView()
        if Global.read_flag(key: "FLAG") {
            btn_start.setTitle("Stop Widget", for: .normal)
            btn_start.backgroundColor = UIColor.red
        } else {
            btn_start.setTitle("Start Widget", for: .normal)
            btn_start.backgroundColor = UIColor.blue
        }
    }
    
    @IBAction func clickStart(_ sender: Any) {
        if Global.read_flag(key: "FLAG") {
            btn_start.setTitle("Start Widget", for: .normal)
            btn_start.backgroundColor = UIColor.blue
            Global.set_flag(value: false, key: "FLAG")
        } else {
            btn_start.setTitle("Stop Widget", for: .normal)
            btn_start.backgroundColor = UIColor.red
            Global.set_flag(value: true, key: "FLAG")
        }
    }
    @IBAction func clickAddAccount(_ sender: Any) {
        view_dialog.isHidden = false
    }
    @IBAction func dialogAddAccount(_ sender: Any) {
        let username = txt_username.text?.trimmingCharacters(in: .whitespacesAndNewlines)
        let password = txt_password.text
        let signature = txt_signature.text?.trimmingCharacters(in: .whitespacesAndNewlines)
        if username!.count * password!.count * signature!.count == 0 {
            Utils.showAlert(title: "Warning", message: "Please fill in all fields!")
            return
        }
        for model in Global.array_account {
            if username == (model as! AccountModel).username {
                Utils.showAlert(title: "Warning", message: "This account already exists!")
                return
            }
        }
        txt_username.text = ""
        txt_password.text = ""
        txt_signature.text = ""
        let model:AccountModel = AccountModel(username: username!, password: password!, signature: signature!, balance: "", currency: "", date: "")
        Global.array_account.add(model)
        tbl_list.reloadData()
        Global.update_accounts_array()
        view.endEditing(true)
        view_dialog.isHidden = true
    }
    @IBAction func dialog_CloseAccount(_ sender: Any) {
        view_dialog.isHidden = true
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if Global.array_account.count == 0 {
            self.tbl_list.setEmptyMessage("No Account")
        } else {
            self.tbl_list.restore()
        }
        return Global.array_account.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 60
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell_account", for: indexPath) as! AccountTableViewCell
        let model:AccountModel = Global.array_account.object(at: indexPath.row) as! AccountModel
        cell.init_cell(index: indexPath.row, parent: self)
        cell.label_username.text = model.username
        cell.label_balance.text = model.balance
        cell.accessoryType = UITableViewCellAccessoryType.none;
        return cell
    }

}
extension UITableView {
    
    func setEmptyMessage(_ message: String) {
        let messageLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.bounds.size.width, height: self.bounds.size.height))
        messageLabel.text = message
        messageLabel.textColor = .black
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
