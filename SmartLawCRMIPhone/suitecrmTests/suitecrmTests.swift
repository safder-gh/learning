//
//  suitecrmTests.swift
//  suitecrmTests
//
//  Created by s on 14/03/2019.
//  Copyright © 2019 Knowledge Management. All rights reserved.
//

import XCTest
@testable import suitecrm

class suitecrmTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    func testNP(){
        let url = URL(string:"https://github.com/user/repo/master/db.json")!
        let np = NetworkProcessor(url: url)
        np.downloadJSONFromURL { (jsonDictionary) in
            print(jsonDictionary!)
        }
    }
    
  }
