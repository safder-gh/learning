//
//  JsonProcessor.swift
//  suitecrm
//
//  Created by s on 19/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import Foundation
public class JsonProcessor {

    let url:URL
    var json: [Dictionary<String, String>] = []
    

    public init(url:URL) {
        self.url = url
        json.append(["method" : "login"])
        json.append(["input_type" : "JSON"])
        json.append(["response_type" : "JSON"])
    }
    public func login(user:User) -> User {
        var userJson: [Dictionary<String, String>] = []
        userJson.append(["user_name":user.userName])
        userJson.append(["password":user.getPassword()])

        var restJson: [Dictionary<String, String>] = []
        restJson.append(["user_auth": Common.getArrayToJson(params:userJson)])
        restJson.append(["application_name": "IPhone"])
        restJson.append(["name_value_list": "[]"])

        json.append(["rest_data" : Common.getArrayToJson(params:restJson)])
        
        let np:NetworkProcessor = NetworkProcessor(url: self.url)
        np.downloadJSONFromURL(parameters: json) { (response) in
            print(response!)
        }
        return User(userName:"",password:"")
    }
}
