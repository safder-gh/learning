using System;
using System.Collections.Generic;
using System.Text;

namespace BridgePattern
{
    public class CityPaymentSystems : IPaymentSystem
    {
        public void ProcessPayment(string paymentSystem)
        {
            Console.WriteLine("Using CityBank getway for "+paymentSystem);
        }
    }
    public class IDBIPaymentSystems : IPaymentSystem
    {
        public void ProcessPayment(string paymentSystem)
        {
            Console.WriteLine("Using IDBIBank getway for " + paymentSystem);
        }
    }
}
