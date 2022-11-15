//
//  NetworkProcessor.swift
//  suitecrm
//
//  Created by s on 14/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import Foundation


internal class NetworkProcessor{
    
    lazy var configuration : URLSessionConfiguration = URLSessionConfiguration.default
    lazy var session : URLSession = URLSession(configuration:self.configuration)
    let url:URL
    
    init(url:URL) {
        self.url = url
    }
      
    internal func downloadJSONFromURL(parameters:[Dictionary<String, String>],_ completion : @escaping ((String?) -> Void)    ){
        var request = URLRequest(url:self.url)
        request.httpMethod = "POST"
        request.httpBody = Common.getPostString(params: parameters).data(using: .utf8)
        
        let dataTask = session.dataTask(with: request) { (data, response, error) in
            if error == nil {
                if let httpResponse = response as? HTTPURLResponse{
                    switch httpResponse.statusCode {
                    case 200:
                        if let data = data{
                                //print(String(decoding: data, as: UTF8.self))
                                completion(String(decoding: data, as: UTF8.self) )
                        }
                   default:
                        print("HTTP Response Code:\(httpResponse.statusCode)")
                    }
                }
            } else {
                print("Error : \(String(describing: error?.localizedDescription))")
            }
        }
        dataTask.resume()
        
    }
    
    

}

