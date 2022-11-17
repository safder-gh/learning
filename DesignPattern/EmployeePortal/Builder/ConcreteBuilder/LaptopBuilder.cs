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

        public void SetHDD(HDD value)
        {
            Desktop.HDD = value;
        }

        public void SetKEYBOARD(KEYBOARD value)
        {
            return;
        }

        public void SetMOUSE(MOUSE value)
        {
            return;
        }

        public void SetRAM(RAM value)
        {
            Desktop.RAM = value;
        }

        public void SetTOUCHSCREEN(TOUCHSCREEN value)
        {
            Desktop.TOUCHSCREEN = value;
        }
    }
}
