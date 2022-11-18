using EmployeePortal.Builder.Product;
using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.IBuilder
{
    public interface ISystemBuilder
    {
        void SetRAM(string RAM);
        void SetHDD(string HDD);
        void SetMOUSE(string MOUSE);
        void SetKEYBOARD(string KEYBOARD);
        void SetTOUCHSCREEN(string TOUCHSCREEN);
        ComputerSystem GetSystem();
    }
}
