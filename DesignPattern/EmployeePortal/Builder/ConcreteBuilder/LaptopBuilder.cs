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

        public ISystemBuilder SetHDD(string HDD)
        {
            Laptop.HDD = EnumHelper<HDD>.GetDisplayValue( EnumHelper<HDD>.Parse(HDD));
            return this;
        }

        public ISystemBuilder SetKEYBOARD(string KEYBOARD)
        {
            return this;
        }

        public ISystemBuilder SetMOUSE(string MOUSE)
        {
            return this;
        }

        public ISystemBuilder SetRAM(string RAM)
        {
            Laptop.RAM = EnumHelper<RAM>.GetDisplayValue(EnumHelper<RAM>.Parse(RAM));
            return this;
        }

        public ISystemBuilder SetTOUCHSCREEN(string TOUCHSCREEN)
        {
            Laptop.TOUCHSCREEN = EnumHelper<TOUCHSCREEN>.GetDisplayValue(EnumHelper<TOUCHSCREEN>.Parse(TOUCHSCREEN));
            return this;
        }
    }
}
