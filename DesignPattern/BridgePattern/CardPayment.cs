using System;
using System.Collections.Generic;
using System.Text;

namespace BridgePattern
{
    public class CardPayment : Payment
    {
        public override void MakePayment()
        {
            _IPaymentSystem.ProcessPayment("Card Payment");
        }
    }
}
