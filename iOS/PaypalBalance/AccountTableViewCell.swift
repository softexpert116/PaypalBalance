//
//  AccountTableViewCell.swift
//  PaypalBalance
//
//  Created by ujs on 11/19/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import UIKit

class AccountTableViewCell: UITableViewCell {

    @IBOutlet weak var label_username: UILabel!
    @IBOutlet weak var label_balance: UILabel!
    var parentController:ViewController? = nil
    var sel_index:Int = 0
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    func init_cell (index:Int, parent:ViewController) {
        parentController = parent
        sel_index = index
    }
    @IBAction func remove_account(_ sender: Any) {
//        let account:AccountModel = array_list.object(at: sel_index) as! AccountModel;
        let alert = UIAlertController(title: "Warning", message: "Are you sure to remove this account?", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: {(alert: UIAlertAction!) in
            Global.array_account.removeObject(at: self.sel_index)
            Global.update_accounts_array()
            self.parentController?.tbl_list.reloadData()
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel, handler: nil))
        
        // show the alert
        parentController?.present(alert, animated: true, completion: nil)
        
//        parentController?.view.makeToast(account.username)
//        guard let cell = (sender as AnyObject).superview as? AccountTableViewCell else {
//            return // or fatalError() or whatever
//        }
//
//        let indexPath = self.indexPath(for: cell)
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
