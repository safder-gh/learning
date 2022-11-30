using System;

namespace BridgePattern
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Payment order = new CardPayment();
            order._IPaymentSystem = new CityPaymentSystems();
            order.MakePayment();
        }
    }
}
