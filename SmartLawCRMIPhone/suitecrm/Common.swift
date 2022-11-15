//
//  Common.swift
//  suitecrm
//
//  Created by s on 15/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import Foundation
import CommonCrypto
class Common {
    static func dictionaryToJson(dictionary:[String:Any]) -> String {
    let jsonData = try? JSONSerialization.data(withJSONObject: dictionary, options: [])
    return String(data: jsonData!, encoding: .utf8)!
    }
    static func MD5(string: String) -> String {
        let messageData = string.data(using:.utf8)!
        var digestData = Data(count: Int(CC_MD5_DIGEST_LENGTH))
        
        _ = digestData.withUnsafeMutableBytes {digestBytes in
            messageData.withUnsafeBytes {messageBytes in
                CC_MD5(messageBytes, CC_LONG(messageData.count), digestBytes)
            }
        }
        
        return digestData.map { String(format: "%02hhx", $0) }.joined()
    }
    static func getPostString(params:[Dictionary<String, String>]) -> String
    {
        var data = [String]()
        params.forEach { item in
            item.forEach({ dic in
              data.append(dic.key + "=\(dic.value)")
            })
        }
       return data.map { String($0) }.joined(separator: "&")
    }
    
    static func getArrayToJson(params:[Dictionary<String, String>])-> String {
        var str:String = ""
        str.append("{")
        params.forEach { item in
            item.forEach({ dic in
                str.append("\"")
                str.append(dic.key)
                str.append("\"")
                str.append(":")
                if(dic.value.first == "{" && dic.value.last == "}" ){
                str.append(dic.value)
                }else {
                    str.append("\"")
                    str.append(dic.value)
                    str.append("\"")
                }
                str.append(",")
           })
        }
        str = String(str.dropLast())
        str.append("}")
        return str
    }
}
