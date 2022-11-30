using System;
using System.Collections.Generic;
using System.Text;

namespace BridgePattern
{
    public class NetBankingPayment : Payment
    {
        public override void MakePayment()
        {
            _IPaymentSystem.ProcessPayment("Net Banking Payment");
        }
    }
}
