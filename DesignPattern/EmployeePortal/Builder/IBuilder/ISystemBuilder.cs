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
        void SetRAM(RAM value);
        void SetHDD(HDD value);
        void SetMOUSE(MOUSE value);
        void SetKEYBOARD(KEYBOARD value);
        void SetTOUCHSCREEN(TOUCHSCREEN value);
        ComputerSystem GetSystem();
    }
}
