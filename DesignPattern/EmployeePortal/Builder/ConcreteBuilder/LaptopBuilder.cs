using EmployeePortal.Builder.IBuilder;
using EmployeePortal.Builder.Product;
using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.ConcreteBuilder
{
    public class LaptopBuilder : ISystemBuilder
    {
        ComputerSystem Desktop = new ComputerSystem();

        public ComputerSystem GetSystem()
        {
            throw new NotImplementedException();
        }

        public void SetHDD(string HDD)
        {
            Desktop.HDD = HDD;
        }

        public void SetKEYBOARD(string KEYBOARD)
        {
            return;
        }

        public void SetMOUSE(string MOUSE)
        {
            return;
        }

        public void SetRAM(string RAM)
        {
            Desktop.RAM = RAM;
        }

        public void SetTOUCHSCREEN(string TOUCHSCREEN)
        {
            Desktop.TOUCHSCREEN = TOUCHSCREEN;
        }
    }
}
