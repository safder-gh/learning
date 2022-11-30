using System;
using System.Collections.Generic;
using System.Text;

namespace BridgePattern
{
    public interface IPaymentSystem
    {
        void ProcessPayment(string paymentSystem);
    }
}
