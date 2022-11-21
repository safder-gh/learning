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
        ComputerSystem Laptop = new ComputerSystem();

        public ComputerSystem GetSystem()
        {
            return Laptop;
        }

        public void SetHDD(string HDD)
        {
            Laptop.HDD = HDD;
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
            Laptop.RAM = RAM;
        }

        public void SetTOUCHSCREEN(string TOUCHSCREEN)
        {
            Laptop.TOUCHSCREEN = TOUCHSCREEN;
        }
    }
}
