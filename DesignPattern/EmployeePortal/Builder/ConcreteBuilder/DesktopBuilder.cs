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
          return DesktopSystem;
        }

        public ISystemBuilder SetHDD(string HDD)
        {
            DesktopSystem.HDD = EnumHelper<HDD>.GetDisplayValue(EnumHelper<HDD>.Parse(HDD));
            return this;
        }

        public ISystemBuilder SetKEYBOARD(string KEYBOARD)
        {
            DesktopSystem.KEYBOARD = EnumHelper<KEYBOARD>.GetDisplayValue(EnumHelper<KEYBOARD>.Parse(KEYBOARD));
            return this;
        }

        public ISystemBuilder SetMOUSE(string MOUSE)
        {
            DesktopSystem.MOUSE = EnumHelper<MOUSE>.GetDisplayValue(EnumHelper<MOUSE>.Parse(MOUSE));
            return this;
        }

        public ISystemBuilder SetRAM(string RAM)
        {
            DesktopSystem.RAM = EnumHelper<RAM>.GetDisplayValue(EnumHelper<RAM>.Parse(RAM));
            return this;
        }

        public ISystemBuilder SetTOUCHSCREEN(string TOUCHSCREEN)
        {
            return this;
        }
    }
}
