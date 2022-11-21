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
        ISystemBuilder SetRAM(string RAM);
        ISystemBuilder SetHDD(string HDD);
        ISystemBuilder SetMOUSE(string MOUSE);
        ISystemBuilder SetKEYBOARD(string KEYBOARD);
        ISystemBuilder SetTOUCHSCREEN(string TOUCHSCREEN);
        ComputerSystem GetSystem();
    }
}
