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

        public void SetHDD(string HDD)
        {
            DesktopSystem.HDD = HDD;
        }

        public void SetKEYBOARD(string KEYBOARD)
        {
            DesktopSystem.KEYBOARD = KEYBOARD;
        }

        public void SetMOUSE(string MOUSE)
        {
            DesktopSystem.MOUSE = MOUSE;
        }

        public void SetRAM(string RAM)
        {
            DesktopSystem.RAM = RAM;
        }

        public void SetTOUCHSCREEN(string TOUCHSCREEN)
        {
            return;
        }
    }
}
