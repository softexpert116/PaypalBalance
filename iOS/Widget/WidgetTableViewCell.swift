//
//  WidgetTableViewCell.swift
//  Widget
//
//  Created by ujs on 11/22/18.
//  Copyright © 2018 ujs. All rights reserved.
//

import UIKit

class WidgetTableViewCell: UITableViewCell {
    @IBOutlet weak var label_username: UILabel!
    
    @IBOutlet weak var label_balance: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
