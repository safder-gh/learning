//
//  User.swift
//  suitecrm
//
//  Created by s on 19/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import Foundation
public class User {
    var userName:String
    private var password:String
    var sessionId:String
    var userId:String
    var isAdmin:Bool
    var isActive:Bool
    public init(userName:String,password:String) {
        self.userName = userName
        self.password = Common.MD5(string:password)
        self.isAdmin = false
        self.sessionId = ""
        self.userId = ""
        self.isActive = false
    }
    public func setPassword(password:String)->Void{
        self.password = Common.MD5(string:password)
    }
    public func getPassword()->String{
        return self.password
    }
}
