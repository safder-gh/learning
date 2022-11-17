using EmployeePortal.Builder.IBuilder;
using EmployeePortal.Builder.Product;
using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.ConcreteBuilder
{
    public class DesktopBuilder : ISystemBuilder
    {
        ComputerSystem DesktopSystem = new ComputerSystem();

        public ComputerSystem GetSystem()
        {
            throw new NotImplementedException();
        }

        public void SetHDD(HDD value)
        {
            DesktopSystem.HDD = value;
        }

        public void SetKEYBOARD(KEYBOARD value)
        {
            DesktopSystem.KEYBOARD = value;
        }

        public void SetMOUSE(MOUSE value)
        {
            DesktopSystem.MOUSE = value;
        }

        public void SetRAM(RAM value)
        {
            DesktopSystem.RAM = value;
        }

        public void SetTOUCHSCREEN(TOUCHSCREEN value)
        {
            return;
        }
    }
}
