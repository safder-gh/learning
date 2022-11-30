using System;
using System.Collections.Generic;
using System.Text;

namespace BridgePattern
{
    public abstract class Payment
    {
        public IPaymentSystem _IPaymentSystem;
        public abstract void MakePayment();
    }
}
