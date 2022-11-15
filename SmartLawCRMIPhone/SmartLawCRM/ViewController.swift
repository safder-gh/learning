//
//  ViewController.swift
//  SmartLawCRM
//
//  Created by s on 14/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import UIKit
import suitecrm
class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let url = URL(string:"https://test.smartlawcrm.com/service/v4_1/rest.php")!
        //let url = URL(string:"http://httpbin.org/post")!
        let np = NetworkProcessor(url: url)
        np.downloadJSONFromURL { (jsonDictionary) in
            //print(jsonDictionary!)
        }
    }


}

