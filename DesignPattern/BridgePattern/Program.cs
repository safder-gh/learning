using System;

namespace BridgePattern
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Payment _CardPayment = new CardPayment();
            _CardPayment._IPaymentSystem = new CityPaymentSystems();
            _CardPayment.MakePayment();

            Payment _NetPayment = new CardPayment();
            _NetPayment._IPaymentSystem = new CityPaymentSystems();
            _NetPayment.MakePayment();
        }
    }
}
